/*     */ package com.enation.app.shop.core.service.impl.batchimport;
/*     */ 
/*     */ import com.enation.app.shop.core.model.Cat;
/*     */ import com.enation.app.shop.core.model.ImportDataSource;
/*     */ import com.enation.app.shop.core.model.support.GoodsTypeDTO;
/*     */ import com.enation.app.shop.core.plugin.goodsimp.GoodsImportPluginBundle;
/*     */ import com.enation.app.shop.core.service.IBrandManager;
/*     */ import com.enation.app.shop.core.service.IGoodsCatManager;
/*     */ import com.enation.app.shop.core.service.IGoodsTypeManager;
/*     */ import com.enation.app.shop.core.service.batchimport.IGoodsDataBatchManager;
/*     */ import com.enation.app.shop.core.service.batchimport.IGoodsDataImporter;
/*     */ import com.enation.framework.context.spring.SpringContextHolder;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.util.ExcelUtil;
/*     */ import com.enation.framework.util.XMLUtil;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*     */ import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/*     */ import org.apache.poi.ss.usermodel.Cell;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.Sheet;
/*     */ import org.apache.poi.ss.usermodel.Workbook;
/*     */ import org.springframework.transaction.annotation.Propagation;
/*     */ import org.springframework.transaction.annotation.Transactional;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public class GoodsDataBatchManager
/*     */   implements IGoodsDataBatchManager
/*     */ {
/*  48 */   protected final Logger logger = Logger.getLogger(getClass());
/*     */   private IBrandManager brandManager;
/*     */   private IGoodsTypeManager goodsTypeManager;
/*     */   private IGoodsCatManager goodsCatManager;
/*     */   private GoodsImportPluginBundle goodsImportPluginBundle;
/*     */   private IDaoSupport daoSupport;
/*     */   private IDaoSupport baseDaoSupport;
/*     */ 
/*     */   @Transactional(propagation=Propagation.REQUIRED)
/*     */   public void batchImport(String path, int imptype, int impcatid, Integer startNum, Integer endNum)
/*     */   {
/*  61 */     if (this.logger.isDebugEnabled()) {
/*  62 */       this.logger.debug("开始导入商品数据...");
/*     */     }
/*     */ 
/*  67 */     Document configDoc = load(path);
/*     */ 
/*  69 */     this.goodsImportPluginBundle.onBeforeImport(configDoc);
/*     */ 
/*  71 */     Element configEl = XMLUtil.getChildByTagName(configDoc, "config");
/*     */ 
/*  74 */     String datafolder = configEl.getAttribute("datafolder");
/*     */ 
/*  77 */     String excel = configEl.getAttribute("excel");
/*  78 */     Workbook goodsWb = openExcel(excel);
/*     */ 
/*  83 */     NodeList catNodeList = configEl.getElementsByTagName("cat");
/*  84 */     int i = 0; for (int len = catNodeList.getLength(); i < len; i++) {
/*  85 */       Node catNode = catNodeList.item(i);
/*     */ 
/*  87 */       if (imptype == 2) {
/*  88 */         int catid = Integer.valueOf(XMLUtil.getChildByTagName(catNode, "id").getTextContent()).intValue();
/*  89 */         if (catid != impcatid) {
/*     */           continue;
/*     */         }
/*     */       }
/*  93 */       processSheet(datafolder, goodsWb, catNode, startNum, endNum);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processSheet(String datafolder, Workbook goodsWb, Node catNode, Integer startNum, Integer endNum)
/*     */   {
/* 102 */     Element beforeSheetNode = XMLUtil.getChildByTagName(catNode, "beforesheet");
/*     */ 
/* 105 */     int sheetIndex = Integer.valueOf(XMLUtil.getChildByTagName(catNode, "sheet_index").getTextContent()).intValue();
/*     */ 
/* 108 */     int rowStartNum = 0;
/* 109 */     if (startNum != null)
/* 110 */       rowStartNum = startNum.intValue();
/*     */     else {
/* 112 */       rowStartNum = Integer.valueOf(XMLUtil.getChildByTagName(catNode, "start_rouwnum").getTextContent()).intValue();
/*     */     }
/*     */ 
/* 116 */     int catid = Integer.valueOf(XMLUtil.getChildByTagName(catNode, "id").getTextContent()).intValue();
/*     */ 
/* 119 */     int goodsIdCluNum = Integer.valueOf(XMLUtil.getChildByTagName(catNode, "goodsid_column").getTextContent()).intValue();
/*     */ 
/* 127 */     ImportDataSource importDs = new ImportDataSource();
/*     */ 
/* 129 */     Cat cat = this.goodsCatManager.getById(catid);
/*     */ 
/* 133 */     GoodsTypeDTO typeDTO = this.goodsTypeManager.get(cat.getType_id());
/* 134 */     importDs.setBrandList(this.brandManager.list());
/* 135 */     importDs.setPropList(typeDTO.getPropList());
/*     */ 
/* 139 */     Sheet sheet = goodsWb.getSheetAt(sheetIndex);
/* 140 */     int lastRowNum = 0;
/* 141 */     if (endNum != null)
/* 142 */       lastRowNum = endNum.intValue();
/*     */     else {
/* 144 */       lastRowNum = sheet.getLastRowNum();
/*     */     }
/*     */ 
/* 149 */     NodeList rowList = beforeSheetNode.getElementsByTagName("column");
/* 150 */     Element processorNode = XMLUtil.getChildByTagName(catNode, "processors");
/* 151 */     NodeList importerNodeList = processorNode.getElementsByTagName("importer");
/*     */ 
/* 154 */     if (this.logger.isDebugEnabled()) {
/* 155 */       this.logger.debug("开始导入类别[" + cat.getName() + "]rowStartNum[" + rowStartNum + "]lastRowNum[" + lastRowNum + "]...");
/*     */     }
/*     */ 
/* 160 */     Map goods = null;
/*     */ 
/* 162 */     for (int i = rowStartNum; i < lastRowNum + 1; i++)
/*     */     {
/* 164 */       Row row = sheet.getRow(i);
/* 165 */       importDs.setRowData(row);
/*     */ 
/* 171 */       int goodsNum = 0;
/* 172 */       if (goodsIdCluNum == -1)
/* 173 */         goodsNum = row.getRowNum() + 1;
/*     */       else {
/* 175 */         goodsNum = Double.valueOf(row.getCell(goodsIdCluNum).getNumericCellValue()).intValue();
/*     */       }
/*     */ 
/* 178 */       if (this.logger.isDebugEnabled()) {
/* 179 */         this.logger.debug("开始行号[" + goodsNum + "]...");
/*     */       }
/*     */ 
/* 183 */       if (goodsNum != 0)
/*     */       {
/* 185 */         importDs.setDatafolder(datafolder + "/" + XMLUtil.getChildByTagName(catNode, "name").getTextContent() + "/" + goodsNum);
/* 186 */         importDs.setNewGoods(true);
/* 187 */         importDs.setGoodsNum(goodsNum);
/* 188 */         goods = new HashMap();
/* 189 */         goods.put("market_enable", Integer.valueOf(1));
/* 190 */         goods.put("cat_id", Integer.valueOf(catid));
/* 191 */         goods.put("type_id", cat.getType_id());
/* 192 */         goods.put("have_spec", Integer.valueOf(0));
/* 193 */         goods.put("cost", Integer.valueOf(0));
/* 194 */         goods.put("store", Integer.valueOf(0));
/* 195 */         goods.put("weight", Integer.valueOf(0));
/* 196 */         goods.put("disabled", Integer.valueOf(0));
/* 197 */         goods.put("create_time", Long.valueOf(System.currentTimeMillis()));
/* 198 */         goods.put("view_count", Integer.valueOf(0));
/* 199 */         goods.put("buy_count", Integer.valueOf(0));
/* 200 */         goods.put("last_modify", Long.valueOf(System.currentTimeMillis()));
/*     */       }
/*     */       else
/*     */       {
/* 205 */         importDs.setNewGoods(false);
/*     */       }
/*     */ 
/* 214 */       for (int j = 0; j < rowList.getLength(); j++) {
/* 215 */         Element rowNode = (Element)rowList.item(j);
/* 216 */         String index = rowNode.getAttribute("index");
/* 217 */         String importer = rowNode.getAttribute("importer");
/*     */ 
/* 219 */         Object value = ExcelUtil.getCellValue(row.getCell(Integer.valueOf(index).intValue()));
/* 220 */         IGoodsDataImporter goodsDataImporter = (IGoodsDataImporter)SpringContextHolder.getBean(importer);
/* 221 */         goodsDataImporter.imported(value, rowNode, importDs, goods);
/*     */       }
/*     */ 
/* 225 */       if (goodsNum != 0) {
/* 226 */         this.baseDaoSupport.insert("goods", goods);
/* 227 */         int goodsid = this.baseDaoSupport.getLastId("goods");
/* 228 */         goods.put("goods_id", Integer.valueOf(goodsid));
/*     */       }
/*     */ 
/* 236 */       Element afterSheetNode = XMLUtil.getChildByTagName(catNode, "aftersheet");
/*     */ 
/* 238 */       if (afterSheetNode != null)
/*     */       {
/* 242 */         NodeList afterRowList = afterSheetNode.getElementsByTagName("column");
/*     */ 
/* 248 */         for (int j = 0; j < afterRowList.getLength(); j++) {
/* 249 */           Element rowNode = (Element)afterRowList.item(j);
/* 250 */           String index = rowNode.getAttribute("index");
/* 251 */           String importer = rowNode.getAttribute("importer");
/*     */ 
/* 253 */           Object value = ExcelUtil.getCellValue(row.getCell(Integer.valueOf(index).intValue()));
/* 254 */           IGoodsDataImporter goodsDataImporter = (IGoodsDataImporter)SpringContextHolder.getBean(importer);
/* 255 */           goodsDataImporter.imported(value, rowNode, importDs, goods);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 265 */       for (int j = 0; j < importerNodeList.getLength(); j++) {
/* 266 */         Element node = (Element)importerNodeList.item(j);
/* 267 */         String importer = node.getTextContent();
/* 268 */         IGoodsDataImporter goodsDataImporter = (IGoodsDataImporter)SpringContextHolder.getBean(importer);
/*     */ 
/* 270 */         goodsDataImporter.imported(null, node, importDs, goods);
/*     */       }
/*     */ 
/* 273 */       if (this.logger.isDebugEnabled()) {
/* 274 */         this.logger.debug("行号[" + goodsNum + "]导入完成");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 279 */     if (this.logger.isDebugEnabled())
/* 280 */       this.logger.debug("导入类别[" + cat.getName() + "]完成...");
/*     */   }
/*     */ 
/*     */   private Workbook openExcel(String excelPath)
/*     */   {
/*     */     try
/*     */     {
/* 294 */       POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(excelPath));
/* 295 */       Workbook wb = new HSSFWorkbook(fs);
/* 296 */       return wb;
/*     */     } catch (IOException e) {
/* 298 */       e.printStackTrace();
/* 299 */     }return null;
/*     */   }
/*     */ 
/*     */   private Document load(String path)
/*     */   {
/*     */     try
/*     */     {
/* 312 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/*     */ 
/* 314 */       DocumentBuilder builder = factory.newDocumentBuilder();
/* 315 */       Document document = builder.parse(path);
/* 316 */       return document;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 320 */       e.printStackTrace();
/* 321 */     }throw new RuntimeException("load [" + path + "]    error");
/*     */   }
/*     */ 
/*     */   public IDaoSupport getDaoSupport()
/*     */   {
/* 331 */     return this.daoSupport;
/*     */   }
/*     */ 
/*     */   public void setDaoSupport(IDaoSupport daoSupport)
/*     */   {
/* 336 */     this.daoSupport = daoSupport;
/*     */   }
/*     */ 
/*     */   public IDaoSupport getBaseDaoSupport()
/*     */   {
/* 341 */     return this.baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public void setBaseDaoSupport(IDaoSupport baseDaoSupport)
/*     */   {
/* 346 */     this.baseDaoSupport = baseDaoSupport;
/*     */   }
/*     */ 
/*     */   public IGoodsTypeManager getGoodsTypeManager()
/*     */   {
/* 351 */     return this.goodsTypeManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsTypeManager(IGoodsTypeManager goodsTypeManager)
/*     */   {
/* 356 */     this.goodsTypeManager = goodsTypeManager;
/*     */   }
/*     */ 
/*     */   public IGoodsCatManager getGoodsCatManager()
/*     */   {
/* 361 */     return this.goodsCatManager;
/*     */   }
/*     */ 
/*     */   public void setGoodsCatManager(IGoodsCatManager goodsCatManager)
/*     */   {
/* 366 */     this.goodsCatManager = goodsCatManager;
/*     */   }
/*     */ 
/*     */   public IBrandManager getBrandManager()
/*     */   {
/* 371 */     return this.brandManager;
/*     */   }
/*     */ 
/*     */   public void setBrandManager(IBrandManager brandManager)
/*     */   {
/* 376 */     this.brandManager = brandManager;
/*     */   }
/*     */ 
/*     */   public GoodsImportPluginBundle getGoodsImportPluginBundle()
/*     */   {
/* 381 */     return this.goodsImportPluginBundle;
/*     */   }
/*     */ 
/*     */   public void setGoodsImportPluginBundle(GoodsImportPluginBundle goodsImportPluginBundle)
/*     */   {
/* 387 */     this.goodsImportPluginBundle = goodsImportPluginBundle;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.GoodsDataBatchManager
 * JD-Core Version:    0.6.0
 */