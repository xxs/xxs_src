/*     */ package com.enation.framework.util;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/*     */ import org.apache.poi.poifs.filesystem.POIFSFileSystem;
/*     */ import org.apache.poi.ss.usermodel.Cell;
/*     */ import org.apache.poi.ss.usermodel.CellStyle;
/*     */ import org.apache.poi.ss.usermodel.DataFormat;
/*     */ import org.apache.poi.ss.usermodel.Row;
/*     */ import org.apache.poi.ss.usermodel.Sheet;
/*     */ import org.apache.poi.ss.usermodel.Workbook;
/*     */ 
/*     */ public class ExcelUtil
/*     */ {
/*     */   private Workbook wb;
/*  32 */   private POIFSFileSystem fs = null;
/*     */ 
/*     */   public ExcelUtil() {
/*  35 */     this.wb = new HSSFWorkbook();
/*     */   }
/*     */ 
/*     */   public void openModal(InputStream in) {
/*     */     try {
/*  40 */       this.fs = new POIFSFileSystem(in);
/*  41 */       this.wb = new HSSFWorkbook(this.fs);
/*     */     } catch (IOException e) {
/*  43 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void openModal(String modlePath)
/*     */   {
/*  49 */     InputStream In = null;
/*  50 */     FileInputStream File_in = null;
/*     */     try
/*     */     {
/*  53 */       this.fs = new POIFSFileSystem(new FileInputStream(modlePath));
/*  54 */       this.wb = new HSSFWorkbook(this.fs);
/*     */     } catch (IOException e) {
/*  56 */       System.out.println("------read ::" + modlePath + "-----ERROR!---");
/*  57 */       e.printStackTrace(System.err);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeToFile(String targetFile) {
/*  62 */     FileOutputStream fileOut = null;
/*     */     try {
/*  64 */       fileOut = new FileOutputStream(targetFile);
/*     */     } catch (FileNotFoundException e) {
/*  66 */       e.printStackTrace();
/*     */     }
/*     */     try {
/*  69 */       this.wb.write(fileOut);
/*     */     } catch (IOException e2) {
/*  71 */       e1.printStackTrace();
/*     */     } finally {
/*  73 */       this.wb = null;
/*     */       try {
/*  75 */         fileOut.flush();
/*  76 */         fileOut.close();
/*     */       } catch (IOException e2) {
/*  78 */         e2.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setCellMoney(int numRow, int numCol)
/*     */   {
/*     */     try
/*     */     {
/*  87 */       if (this.wb.getSheetAt(0) != null) {
/*  88 */         Sheet aSheet = this.wb.getSheetAt(0);
/*  89 */         DataFormat format = this.wb.createDataFormat();
/*  90 */         Row row = aSheet.getRow((short)numRow);
/*  91 */         Cell csCell = row.getCell((short)numCol);
/*  92 */         CellStyle style = this.wb.createCellStyle();
/*  93 */         style.setDataFormat(format.getFormat("0.00"));
/*  94 */         style.setBorderBottom(1);
/*  95 */         style.setBottomBorderColor(8);
/*  96 */         style.setBorderLeft(1);
/*  97 */         style.setLeftBorderColor(8);
/*  98 */         style.setBorderRight(1);
/*  99 */         style.setRightBorderColor(8);
/* 100 */         style.setBorderTop(1);
/* 101 */         style.setTopBorderColor(8);
/*     */ 
/* 103 */         csCell.setCellStyle(style);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 107 */       System.out.println("insertDataToExcel" + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeStringToCell(int numRow, int numCol, String strval) {
/*     */     try {
/* 113 */       strval = StringUtil.isEmpty(strval) ? "" : strval;
/* 114 */       if (this.wb.getSheetAt(0) != null) {
/* 115 */         Sheet aSheet = this.wb.getSheetAt(0);
/* 116 */         Row row = aSheet.getRow((short)numRow);
/* 117 */         if (row == null) {
/* 118 */           row = aSheet.createRow(numRow);
/*     */         }
/* 120 */         Cell csCell = row.getCell((short)numCol);
/* 121 */         if (csCell == null) {
/* 122 */           csCell = row.createCell(numCol);
/*     */         }
/* 124 */         csCell.setCellValue(strval);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 128 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insertStringToCell(int numRow, int numCol, String strval)
/*     */   {
/*     */     try
/*     */     {
/* 136 */       if (this.wb.getSheetAt(0) != null) {
/* 137 */         Sheet aSheet = this.wb.getSheetAt(0);
/* 138 */         Row row = aSheet.createRow((short)numRow);
/* 139 */         Cell csCell = row.createCell((short)numCol);
/*     */ 
/* 141 */         csCell.setCellValue(strval);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 146 */       System.out.println("insertDataToExcel" + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeFormula(int numRow, int numCol, String formula)
/*     */   {
/*     */     try {
/* 153 */       if (this.wb.getSheetAt(0) != null) {
/* 154 */         Sheet aSheet = this.wb.getSheetAt(0);
/* 155 */         Row row = aSheet.getRow((short)numRow);
/* 156 */         Cell csCell = row.getCell((short)numCol);
/* 157 */         csCell.setCellFormula(formula);
/*     */       }
/*     */     } catch (Exception e) {
/* 160 */       System.out.println("insertDataToExcel" + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insertFormula(int numRow, int numCol, String formula) {
/*     */     try {
/* 166 */       if (this.wb.getSheetAt(0) != null) {
/* 167 */         Sheet aSheet = this.wb.getSheetAt(0);
/* 168 */         Row row = aSheet.createRow((short)numRow);
/* 169 */         Cell csCell = row.createCell((short)numCol);
/* 170 */         csCell.setCellFormula(formula);
/*     */       }
/*     */     } catch (Exception e) {
/* 173 */       System.out.println("insertDataToExcel" + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeNumToCell(int numRow, int numCol, Double num) {
/*     */     try {
/* 179 */       if (this.wb.getSheetAt(0) != null) {
/* 180 */         Sheet aSheet = this.wb.getSheetAt(0);
/* 181 */         Row row = aSheet.getRow((short)numRow);
/* 182 */         Cell csCell = row.getCell((short)numCol);
/* 183 */         csCell.setCellValue(num.doubleValue());
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 187 */       System.out.println("insertDataToExcel" + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insertNumToCell(int numRow, int numCol, Double num)
/*     */   {
/*     */     try {
/* 194 */       if (this.wb.getSheetAt(0) != null) {
/* 195 */         Sheet aSheet = this.wb.getSheetAt(0);
/* 196 */         Row row = aSheet.createRow((short)numRow);
/* 197 */         Cell csCell = row.createCell((short)numCol);
/* 198 */         csCell.setCellValue(num.doubleValue());
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 202 */       System.out.println("insertDataToExcel" + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void replaceDataToCell(int numRow, int numCol, String temstr, String strval)
/*     */   {
/*     */     try
/*     */     {
/* 210 */       if (this.wb.getSheetAt(0) != null) {
/* 211 */         Sheet aSheet = this.wb.getSheetAt(0);
/* 212 */         Row row = aSheet.getRow((short)numRow);
/* 213 */         Cell csCell = row.getCell((short)numCol);
/* 214 */         String temp = "";
/* 215 */         temp = csCell.getStringCellValue();
/* 216 */         temp = temp.replaceAll(temstr, strval);
/* 217 */         csCell.setCellValue(temp);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 221 */       System.out.println("insertDataToExcel" + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insertDataToExcel(int numRow, Object[] object)
/*     */   {
/*     */     try
/*     */     {
/* 231 */       if (null != this.wb.getSheetAt(0)) {
/* 232 */         Sheet aSheet = this.wb.getSheetAt(0);
/* 233 */         Row row = aSheet.getRow((short)numRow);
/*     */ 
/* 236 */         if (row == null) {
/* 237 */           row = aSheet.createRow((short)numRow);
/*     */         }
/*     */ 
/* 240 */         for (int i = 0; i < object.length; i++) {
/* 241 */           Cell csCell = row.createCell((short)i);
/*     */ 
/* 243 */           CellStyle style = this.wb.createCellStyle();
/* 244 */           style.setBorderBottom(1);
/* 245 */           style.setBottomBorderColor(8);
/* 246 */           style.setBorderLeft(1);
/* 247 */           style.setLeftBorderColor(8);
/* 248 */           style.setBorderRight(1);
/* 249 */           style.setRightBorderColor(8);
/* 250 */           style.setBorderTop(1);
/* 251 */           style.setTopBorderColor(8);
/*     */ 
/* 253 */           csCell.setCellStyle(style);
/*     */ 
/* 255 */           if (object[i] != null)
/* 256 */             csCell.setCellValue(object[i].toString());
/*     */           else {
/* 258 */             csCell.setCellValue("0");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 265 */       System.out.println("insertDataToExcel" + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getExcelLastCellNum()
/*     */   {
/* 275 */     int count = 0;
/* 276 */     if (null != this.wb.getSheetAt(0)) {
/* 277 */       Sheet aSheet = this.wb.getSheetAt(0);
/* 278 */       Row aRow = aSheet.getRow(0);
/* 279 */       count = aRow.getLastCellNum();
/*     */     }
/* 281 */     return count;
/*     */   }
/*     */ 
/*     */   public String getOutputPath(String str) {
/* 285 */     String temp = "";
/* 286 */     if (str.lastIndexOf("/") != -1)
/*     */     {
/* 288 */       temp = str.substring(0, str.lastIndexOf("/"));
/* 289 */     } else if (str.lastIndexOf("\\") != -1)
/*     */     {
/* 291 */       temp = str.substring(0, str.lastIndexOf("\\"));
/* 292 */     } else if (str.lastIndexOf("\\\\") != -1)
/*     */     {
/* 294 */       temp = str.substring(0, str.lastIndexOf("\\\\"));
/*     */     }
/*     */     else {
/* 297 */       temp = str;
/*     */     }
/*     */ 
/* 300 */     return temp;
/*     */   }
/*     */ 
/*     */   public Row getRow(int sheetIndex, int rowIndex)
/*     */   {
/* 305 */     Sheet sheet = this.wb.getSheetAt(sheetIndex);
/* 306 */     return sheet.getRow(rowIndex);
/*     */   }
/*     */ 
/*     */   public Sheet getSheet(int sheetIndex)
/*     */   {
/* 311 */     return this.wb.getSheetAt(sheetIndex);
/*     */   }
/*     */ 
/*     */   public static Object getCellValue(Cell cell)
/*     */   {
/* 316 */     if (cell == null) return null;
/* 317 */     int celltype = cell.getCellType();
/*     */ 
/* 319 */     if (celltype == 0) {
/* 320 */       return Double.valueOf(cell.getNumericCellValue());
/*     */     }
/* 322 */     if (celltype == 1) {
/* 323 */       String value = cell.getStringCellValue();
/* 324 */       if ("null".equals(value)) {
/* 325 */         value = "";
/*     */       }
/*     */ 
/* 328 */       if (!StringUtil.isEmpty(value))
/*     */       {
/* 330 */         value = value.replaceAll(" ", "");
/* 331 */         value = value.replaceAll("  ", "");
/*     */       }
/*     */ 
/* 334 */       return value;
/*     */     }
/*     */ 
/* 338 */     if (celltype == 2) {
/* 339 */       String value = cell.getStringCellValue();
/* 340 */       if ("null".equals(value)) {
/* 341 */         value = "";
/*     */       }
/*     */ 
/* 344 */       return value;
/*     */     }
/*     */ 
/* 347 */     if (celltype == 3) {
/* 348 */       return "";
/*     */     }
/* 350 */     if (celltype == 5) {
/* 351 */       return "";
/*     */     }
/*     */ 
/* 355 */     return "";
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 368 */     int a = Double.valueOf("3.0").intValue();
/*     */ 
/* 370 */     System.out.println(a);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.ExcelUtil
 * JD-Core Version:    0.6.0
 */