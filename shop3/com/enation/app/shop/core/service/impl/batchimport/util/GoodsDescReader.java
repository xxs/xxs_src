/*    */ package com.enation.app.shop.core.service.impl.batchimport.util;
/*    */ 
/*    */ import com.enation.eop.sdk.context.EopSetting;
/*    */ import com.enation.framework.util.FileUtil;
/*    */ import java.io.File;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.jsoup.Jsoup;
/*    */ import org.jsoup.nodes.Document;
/*    */ import org.jsoup.nodes.Element;
/*    */ import org.jsoup.select.Elements;
/*    */ 
/*    */ public class GoodsDescReader
/*    */ {
/* 19 */   protected final Logger logger = Logger.getLogger(getClass());
/*    */ 
/*    */   public String read(String folder, String goodsid)
/*    */   {
/* 23 */     String descFilePath = folder + "/desc.htm";
/*    */ 
/* 25 */     if (!new File(descFilePath).exists()) {
/* 26 */       descFilePath = folder + "/desc.html";
/* 27 */       if ((!new File(descFilePath).exists()) && 
/* 28 */         (this.logger.isDebugEnabled())) {
/* 29 */         this.logger.debug("描述[" + descFilePath + "]文件不存,跳过");
/*    */       }
/*    */ 
/* 33 */       return null;
/*    */     }
/*    */ 
/* 38 */     String bodyHtml = null;
/*    */ 
/* 44 */     Document doc = Jsoup.parse(FileUtil.read(descFilePath, "GBK"));
/* 45 */     Elements bodys = doc.select("body");
/* 46 */     if ((bodys != null) && (!bodys.isEmpty())) {
/* 47 */       Element bodyEl = bodys.get(0);
/* 48 */       bodyHtml = bodyEl.html();
/* 49 */       bodyHtml = bodyHtml.replaceAll("src=\"desc.files/", "src=\"fs:/attachment/ckeditor/" + goodsid + "/");
/* 50 */       bodyHtml = bodyHtml.replaceAll("src=\"desc_files/", "src=\"fs:/attachment/ckeditor/" + goodsid + "/");
/*    */ 
/* 53 */       if (this.logger.isDebugEnabled()) {
/* 54 */         this.logger.debug("read商品[" + goodsid + "]描述信息完成");
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 66 */     String folderPath = folder + "/desc.files";
/* 67 */     if (new File(folderPath).exists()) {
/* 68 */       FileUtil.copyFolder(folderPath, EopSetting.IMG_SERVER_PATH + "/attachment/ckeditor/" + goodsid);
/* 69 */       if (this.logger.isDebugEnabled())
/* 70 */         this.logger.debug("copy商品[" + goodsid + "]描述图片完成");
/*    */     }
/*    */     else {
/* 73 */       folderPath = folder + "/desc_files";
/*    */ 
/* 75 */       if (new File(folderPath).exists()) {
/* 76 */         FileUtil.copyFolder(folderPath, EopSetting.IMG_SERVER_PATH + "/attachment/ckeditor/" + goodsid);
/* 77 */         if (this.logger.isDebugEnabled()) {
/* 78 */           this.logger.debug("copy商品[" + goodsid + "]描述图片完成");
/*    */         }
/*    */       }
/* 81 */       else if (this.logger.isDebugEnabled()) {
/* 82 */         this.logger.debug("商品[" + goodsid + "]描述图片不存在，跳过导入描述图片");
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 87 */     if (this.logger.isDebugEnabled()) {
/* 88 */       this.logger.debug("导入商品[" + goodsid + "]描述 完成");
/*    */     }
/*    */ 
/* 91 */     return bodyHtml;
/*    */   }
/*    */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.batchimport.util.GoodsDescReader
 * JD-Core Version:    0.6.0
 */