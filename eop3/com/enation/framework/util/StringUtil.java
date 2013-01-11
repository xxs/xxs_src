/*     */ package com.enation.framework.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URL;
/*     */ import java.net.URLDecoder;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class StringUtil
/*     */ {
/*     */   public static String doubleToIntString(Double d)
/*     */   {
/*  40 */     int value = d.intValue();
/*  41 */     return String.valueOf(value);
/*     */   }
/*     */ 
/*     */   public static boolean checkFloat(String num, String type)
/*     */   {
/*  53 */     String eL = "";
/*  54 */     if (type.equals("0+"))
/*  55 */       eL = "^\\d+(\\.\\d+)?$";
/*  56 */     else if (type.equals("+"))
/*  57 */       eL = "^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$";
/*  58 */     else if (type.equals("-0"))
/*  59 */       eL = "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";
/*  60 */     else if (type.equals("-"))
/*  61 */       eL = "^(-((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*)))$";
/*     */     else
/*  63 */       eL = "^(-?\\d+)(\\.\\d+)?$";
/*  64 */     Pattern p = Pattern.compile(eL);
/*  65 */     Matcher m = p.matcher(num);
/*  66 */     boolean b = m.matches();
/*  67 */     return b;
/*     */   }
/*     */ 
/*     */   public static boolean isInArray(String value, String[] array)
/*     */   {
/*  78 */     if (array == null)
/*  79 */       return false;
/*  80 */     for (String v : array) {
/*  81 */       if (v.equals(value))
/*  82 */         return true;
/*     */     }
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean isInArray(int value, String[] array)
/*     */   {
/*  89 */     if (array == null)
/*  90 */       return false;
/*  91 */     for (String v : array) {
/*  92 */       if (Integer.valueOf(v).intValue() == value)
/*  93 */         return true;
/*     */     }
/*  95 */     return false;
/*     */   }
/*     */ 
/*     */   public static String implode(String str, Object[] array)
/*     */   {
/* 106 */     if ((str == null) || (array == null)) {
/* 107 */       return "";
/*     */     }
/* 109 */     String result = "";
/* 110 */     for (int i = 0; i < array.length; i++) {
/* 111 */       if (i == array.length - 1)
/* 112 */         result = result + array[i].toString();
/*     */       else {
/* 114 */         result = result + array[i].toString() + str;
/*     */       }
/*     */     }
/* 117 */     return result;
/*     */   }
/*     */ 
/*     */   public static String implodeValue(String str, Object[] array) {
/* 121 */     if ((str == null) || (array == null)) {
/* 122 */       return "";
/*     */     }
/* 124 */     String result = "";
/* 125 */     for (int i = 0; i < array.length; i++) {
/* 126 */       if (i == array.length - 1)
/* 127 */         result = result + "?";
/*     */       else {
/* 129 */         result = result + "?" + str;
/*     */       }
/*     */     }
/* 132 */     return result;
/*     */   }
/*     */ 
/*     */   public static String md5(String str)
/*     */   {
/* 143 */     MessageDigest messageDigest = null;
/*     */     try {
/* 145 */       messageDigest = MessageDigest.getInstance("MD5");
/*     */     } catch (NoSuchAlgorithmException ex) {
/* 147 */       ex.printStackTrace();
/* 148 */       return null;
/*     */     }
/* 150 */     byte[] resultByte = messageDigest.digest(str.getBytes());
/* 151 */     StringBuffer result = new StringBuffer();
/* 152 */     for (int i = 0; i < resultByte.length; i++) {
/* 153 */       result.append(Integer.toHexString(0xFF & resultByte[i]));
/*     */     }
/* 155 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public static boolean validEmail(String sEmail)
/*     */   {
/* 165 */     String pattern = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
/* 166 */     return sEmail.matches(pattern);
/*     */   }
/*     */ 
/*     */   public static boolean validMaxLen(String str, int length)
/*     */   {
/* 176 */     if ((str == null) && (str.equals(""))) {
/* 177 */       return false;
/*     */     }
/*     */ 
/* 180 */     return str.length() <= length;
/*     */   }
/*     */ 
/*     */   public static boolean validMinLen(String str, int length)
/*     */   {
/* 193 */     if ((str == null) && (str.equals(""))) {
/* 194 */       return false;
/*     */     }
/*     */ 
/* 197 */     return str.length() >= length;
/*     */   }
/*     */ 
/*     */   public static boolean isEmpty(String str)
/*     */   {
/* 211 */     if ((str == null) || ("".equals(str))) {
/* 212 */       return true;
/*     */     }
/* 214 */     String pattern = "\\S";
/* 215 */     Pattern p = Pattern.compile(pattern, 34);
/* 216 */     Matcher m = p.matcher(str);
/* 217 */     return !m.find();
/*     */   }
/*     */ 
/*     */   public static boolean equals(String str1, String str2)
/*     */   {
/* 228 */     if ((str1 == null) || (str1.equals("")) || (str2 == null) || (str2.equals(""))) {
/* 229 */       return false;
/*     */     }
/* 231 */     return str1.equals(str2);
/*     */   }
/*     */ 
/*     */   public static int toInt(String str, boolean checked)
/*     */   {
/* 242 */     int value = 0;
/* 243 */     if ((str == null) || (str.equals("")))
/* 244 */       return 0;
/*     */     try
/*     */     {
/* 247 */       value = Integer.parseInt(str);
/*     */     } catch (Exception ex) {
/* 249 */       if (checked) {
/* 250 */         throw new RuntimeException("整型数字格式不正确");
/*     */       }
/* 252 */       return 0;
/*     */     }
/*     */ 
/* 255 */     return value;
/*     */   }
/*     */ 
/*     */   public static int toInt(String str, int defaultValue)
/*     */   {
/* 265 */     int value = defaultValue;
/* 266 */     if ((str == null) || (str.equals("")))
/* 267 */       return defaultValue;
/*     */     try
/*     */     {
/* 270 */       value = Integer.parseInt(str);
/*     */     } catch (Exception ex) {
/* 272 */       throw new RuntimeException("整型数字格式不正确");
/*     */     }
/* 274 */     return value;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static int toInt(String str)
/*     */   {
/* 285 */     int value = 0;
/* 286 */     if ((str == null) || (str.equals("")))
/* 287 */       return 0;
/*     */     try
/*     */     {
/* 290 */       value = Integer.parseInt(str);
/*     */     } catch (Exception ex) {
/* 292 */       value = 0;
/* 293 */       ex.printStackTrace();
/*     */     }
/* 295 */     return value;
/*     */   }
/*     */   @Deprecated
/*     */   public static Double toDouble(String str) {
/* 300 */     Double value = Double.valueOf(0.0D);
/* 301 */     if ((str == null) || (str.equals("")))
/* 302 */       return Double.valueOf(0.0D);
/*     */     try
/*     */     {
/* 305 */       value = Double.valueOf(str);
/*     */     } catch (Exception ex) {
/* 307 */       value = Double.valueOf(0.0D);
/*     */     }
/*     */ 
/* 310 */     return value;
/*     */   }
/*     */ 
/*     */   public static Double toDouble(String str, boolean checked)
/*     */   {
/* 321 */     Double value = Double.valueOf(0.0D);
/* 322 */     if ((str == null) || (str.equals("")))
/* 323 */       return Double.valueOf(0.0D);
/*     */     try
/*     */     {
/* 326 */       value = Double.valueOf(str);
/*     */     } catch (Exception ex) {
/* 328 */       if (checked) {
/* 329 */         throw new RuntimeException("数字格式不正确");
/*     */       }
/* 331 */       return Double.valueOf(0.0D);
/*     */     }
/* 333 */     return value;
/*     */   }
/*     */ 
/*     */   public static String arrayToString(Object[] array, String split)
/*     */   {
/* 343 */     if (array == null) {
/* 344 */       return "";
/*     */     }
/* 346 */     String str = "";
/* 347 */     for (int i = 0; i < array.length; i++) {
/* 348 */       if (i != array.length - 1)
/* 349 */         str = str + array[i].toString() + split;
/*     */       else {
/* 351 */         str = str + array[i].toString();
/*     */       }
/*     */     }
/* 354 */     return str;
/*     */   }
/*     */ 
/*     */   public static String listToString(List list, String split)
/*     */   {
/* 365 */     if ((list == null) || (list.isEmpty()))
/* 366 */       return "";
/* 367 */     StringBuffer sb = new StringBuffer();
/* 368 */     for (Iterator i$ = list.iterator(); i$.hasNext(); ) { Object obj = i$.next();
/* 369 */       if (sb.length() != 0) {
/* 370 */         sb.append(split);
/*     */       }
/* 372 */       sb.append(obj.toString());
/*     */     }
/* 374 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String getWebInfPath()
/*     */   {
/* 383 */     String filePath = Thread.currentThread().getContextClassLoader().getResource("").toString();
/*     */ 
/* 385 */     if (filePath.toLowerCase().indexOf("file:") > -1) {
/* 386 */       filePath = filePath.substring(6, filePath.length());
/*     */     }
/* 388 */     if (filePath.toLowerCase().indexOf("classes") > -1) {
/* 389 */       filePath = filePath.replaceAll("/classes", "");
/*     */     }
/* 391 */     if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
/* 392 */       filePath = "/" + filePath;
/*     */     }
/* 394 */     if (!filePath.endsWith("/"))
/* 395 */       filePath = filePath + "/";
/* 396 */     return filePath;
/*     */   }
/*     */ 
/*     */   public static String getRootPath()
/*     */   {
/* 406 */     String filePath = Thread.currentThread().getContextClassLoader().getResource("").toString();
/*     */ 
/* 408 */     if (filePath.toLowerCase().indexOf("file:") > -1) {
/* 409 */       filePath = filePath.substring(6, filePath.length());
/*     */     }
/* 411 */     if (filePath.toLowerCase().indexOf("classes") > -1) {
/* 412 */       filePath = filePath.replaceAll("/classes", "");
/*     */     }
/* 414 */     if (filePath.toLowerCase().indexOf("web-inf") > -1) {
/* 415 */       filePath = filePath.substring(0, filePath.length() - 9);
/*     */     }
/* 417 */     if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
/* 418 */       filePath = "/" + filePath;
/*     */     }
/*     */ 
/* 421 */     if (filePath.endsWith("/")) {
/* 422 */       filePath = filePath.substring(0, filePath.length() - 1);
/*     */     }
/* 424 */     return filePath;
/*     */   }
/*     */ 
/*     */   public static String getRootPath(String resource) {
/* 428 */     String filePath = Thread.currentThread().getContextClassLoader().getResource(resource).toString();
/*     */ 
/* 430 */     if (filePath.toLowerCase().indexOf("file:") > -1) {
/* 431 */       filePath = filePath.substring(6, filePath.length());
/*     */     }
/*     */ 
/* 439 */     if (System.getProperty("os.name").toLowerCase().indexOf("window") < 0) {
/* 440 */       filePath = "/" + filePath;
/*     */     }
/* 442 */     if (!filePath.endsWith("/")) {
/* 443 */       filePath = filePath + "/";
/*     */     }
/* 445 */     return filePath;
/*     */   }
/*     */ 
/*     */   public static int formatPage(String page)
/*     */   {
/* 455 */     int iPage = 1;
/* 456 */     if ((page == null) || (page.equals("")))
/* 457 */       return iPage;
/*     */     try
/*     */     {
/* 460 */       iPage = Integer.parseInt(page);
/*     */     } catch (Exception ex) {
/* 462 */       iPage = 1;
/*     */     }
/* 464 */     return iPage;
/*     */   }
/*     */ 
/*     */   public static String getFileSize(String fileSize)
/*     */   {
/* 474 */     String temp = "";
/* 475 */     DecimalFormat df = new DecimalFormat("0.00");
/* 476 */     double dbFileSize = Double.parseDouble(fileSize);
/* 477 */     if (dbFileSize >= 1024.0D) {
/* 478 */       if (dbFileSize >= 1048576.0D) {
/* 479 */         if (dbFileSize >= 1073741824.0D)
/* 480 */           temp = df.format(dbFileSize / 1024.0D / 1024.0D / 1024.0D) + " GB";
/*     */         else
/* 482 */           temp = df.format(dbFileSize / 1024.0D / 1024.0D) + " MB";
/*     */       }
/*     */       else
/* 485 */         temp = df.format(dbFileSize / 1024.0D) + " KB";
/*     */     }
/*     */     else {
/* 488 */       temp = df.format(dbFileSize / 1024.0D) + " KB";
/*     */     }
/* 490 */     return temp;
/*     */   }
/*     */ 
/*     */   public static String getEntry()
/*     */   {
/* 499 */     Random random = new Random(100L);
/* 500 */     Date now = new Date();
/* 501 */     SimpleDateFormat formatter = new SimpleDateFormat(new String("yyyyMMddHHmmssS"));
/*     */ 
/* 503 */     return md5(formatter.format(now) + random.nextDouble());
/*     */   }
/*     */ 
/*     */   public static String toUTF8(String str)
/*     */   {
/* 513 */     if ((str == null) || (str.equals("")))
/* 514 */       return "";
/*     */     try
/*     */     {
/* 517 */       return new String(str.getBytes("ISO8859-1"), "UTF-8");
/*     */     } catch (Exception ex) {
/* 519 */       ex.printStackTrace();
/* 520 */     }return "";
/*     */   }
/*     */ 
/*     */   public static String to(String str, String charset)
/*     */   {
/* 525 */     if ((str == null) || (str.equals("")))
/* 526 */       return "";
/*     */     try
/*     */     {
/* 529 */       return new String(str.getBytes("ISO8859-1"), charset);
/*     */     } catch (Exception ex) {
/* 531 */       ex.printStackTrace();
/* 532 */     }return "";
/*     */   }
/*     */ 
/*     */   public static String getRandStr(int n)
/*     */   {
/* 537 */     Random random = new Random();
/* 538 */     String sRand = "";
/* 539 */     for (int i = 0; i < n; i++) {
/* 540 */       String rand = String.valueOf(random.nextInt(10));
/* 541 */       sRand = sRand + rand;
/*     */     }
/* 543 */     return sRand;
/*     */   }
/*     */ 
/*     */   public static String getChineseNum(int num)
/*     */   {
/* 553 */     String[] chineseNum = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
/*     */ 
/* 555 */     return chineseNum[num];
/*     */   }
/*     */ 
/*     */   public static String replaceEnter(String str) {
/* 559 */     if (str == null)
/* 560 */       return null;
/* 561 */     return str.replaceAll("\r", "").replaceAll("\n", "");
/*     */   }
/*     */ 
/*     */   public static String replaceAll(String source, String target, String content) {
/* 565 */     StringBuffer buffer = new StringBuffer(source);
/* 566 */     int start = buffer.indexOf(target);
/* 567 */     if (start > 0) {
/* 568 */       source = buffer.replace(start, start + target.length(), content).toString();
/*     */     }
/*     */ 
/* 571 */     return source;
/*     */   }
/*     */ 
/*     */   public static String getTxtWithoutHTMLElement(String element)
/*     */   {
/* 581 */     if ((null == element) || ("".equals(element.trim()))) {
/* 582 */       return element;
/*     */     }
/*     */ 
/* 585 */     Pattern pattern = Pattern.compile("<[^<|^>]*>");
/* 586 */     Matcher matcher = pattern.matcher(element);
/* 587 */     StringBuffer txt = new StringBuffer();
/* 588 */     while (matcher.find()) {
/* 589 */       String group = matcher.group();
/* 590 */       if (group.matches("<[\\s]*>"))
/* 591 */         matcher.appendReplacement(txt, group);
/*     */       else {
/* 593 */         matcher.appendReplacement(txt, "");
/*     */       }
/*     */     }
/* 596 */     matcher.appendTail(txt);
/* 597 */     String temp = txt.toString().replaceAll("\n", "");
/* 598 */     temp = temp.replaceAll(" ", "");
/* 599 */     return temp;
/*     */   }
/*     */ 
/*     */   public static String toTrim(String strtrim)
/*     */   {
/* 608 */     if ((null != strtrim) && (!strtrim.equals(""))) {
/* 609 */       return strtrim.trim();
/*     */     }
/* 611 */     return "";
/*     */   }
/*     */ 
/*     */   public static String filterDollarStr(String str)
/*     */   {
/* 621 */     String sReturn = "";
/* 622 */     if (!toTrim(str).equals("")) {
/* 623 */       if (str.indexOf(36, 0) > -1) {
/* 624 */         while (str.length() > 0) {
/* 625 */           if (str.indexOf(36, 0) > -1) {
/* 626 */             sReturn = sReturn + str.subSequence(0, str.indexOf(36, 0));
/* 627 */             sReturn = sReturn + "\\$";
/* 628 */             str = str.substring(str.indexOf(36, 0) + 1, str.length()); continue;
/*     */           }
/*     */ 
/* 631 */           sReturn = sReturn + str;
/* 632 */           str = "";
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 637 */       sReturn = str;
/*     */     }
/*     */ 
/* 640 */     return sReturn;
/*     */   }
/*     */ 
/*     */   public static String compressHtml(String html) {
/* 644 */     if (html == null) {
/* 645 */       return null;
/*     */     }
/* 647 */     html = html.replaceAll("[\\t\\n\\f\\r]", "");
/* 648 */     return html;
/*     */   }
/*     */ 
/*     */   public static String toCurrency(Double d) {
/* 652 */     if (d != null) {
/* 653 */       DecimalFormat df = new DecimalFormat("￥#,###.00");
/* 654 */       return df.format(d);
/*     */     }
/* 656 */     return "";
/*     */   }
/*     */ 
/*     */   public static String toString(Integer i) {
/* 660 */     if (i != null) {
/* 661 */       return String.valueOf(i);
/*     */     }
/* 663 */     return "";
/*     */   }
/*     */ 
/*     */   public static String toString(Double d) {
/* 667 */     if (null != d) {
/* 668 */       return String.valueOf(d);
/*     */     }
/* 670 */     return "";
/*     */   }
/*     */ 
/*     */   public static String getRandom() {
/* 674 */     int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
/* 675 */     Random rand = new Random();
/* 676 */     for (int i = 10; i > 1; i--) {
/* 677 */       int index = rand.nextInt(i);
/* 678 */       int tmp = array[index];
/* 679 */       array[index] = array[(i - 1)];
/* 680 */       array[(i - 1)] = tmp;
/*     */     }
/* 682 */     int result = 0;
/* 683 */     for (int i = 0; i < 6; i++) {
/* 684 */       result = result * 10 + array[i];
/*     */     }
/* 686 */     return "" + result;
/*     */   }
/*     */ 
/*     */   public static int getMaxLevelCode(int code)
/*     */   {
/* 696 */     String codeStr = "" + code;
/* 697 */     StringBuffer str = new StringBuffer();
/* 698 */     boolean flag = true;
/* 699 */     for (int i = codeStr.length() - 1; i >= 0; i--) {
/* 700 */       char c = codeStr.charAt(i);
/* 701 */       if ((c == '0') && (flag)) {
/* 702 */         str.insert(0, '9');
/*     */       } else {
/* 704 */         str.insert(0, c);
/* 705 */         flag = false;
/*     */       }
/*     */     }
/* 708 */     return Integer.valueOf(str.toString()).intValue();
/*     */   }
/*     */ 
/*     */   public static String delSqlComment(String content)
/*     */   {
/* 715 */     String pattern = "/\\*(.|[\r\n])*?\\*/";
/* 716 */     Pattern p = Pattern.compile(pattern, 34);
/* 717 */     Matcher m = p.matcher(content);
/* 718 */     if (m.find()) {
/* 719 */       content = m.replaceAll("");
/*     */     }
/* 721 */     return content;
/*     */   }
/*     */ 
/*     */   public static String inputStream2String(InputStream is) {
/* 725 */     BufferedReader in = new BufferedReader(new InputStreamReader(is));
/* 726 */     StringBuffer buffer = new StringBuffer();
/* 727 */     String line = "";
/*     */     try {
/* 729 */       while ((line = in.readLine()) != null)
/* 730 */         buffer.append(line);
/*     */     }
/*     */     catch (IOException e) {
/* 733 */       e.printStackTrace();
/*     */     }
/* 735 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   public static String decode(String keyword) {
/*     */     try {
/* 740 */       keyword = URLDecoder.decode(keyword, "UTF-8");
/*     */     }
/*     */     catch (UnsupportedEncodingException e) {
/* 743 */       e.printStackTrace();
/*     */     }
/*     */ 
/* 746 */     return keyword;
/*     */   }
/*     */ 
/*     */   public static String doFilter(String regex, String rpstr, String source)
/*     */   {
/* 758 */     Pattern p = Pattern.compile(regex, 34);
/* 759 */     Matcher m = p.matcher(source);
/* 760 */     return m.replaceAll(rpstr);
/*     */   }
/*     */ 
/*     */   public static String formatScript(String source)
/*     */   {
/* 770 */     source = source.replaceAll("javascript", "&#106avascript");
/* 771 */     source = source.replaceAll("jscript:", "&#106script:");
/* 772 */     source = source.replaceAll("js:", "&#106s:");
/* 773 */     source = source.replaceAll("value", "&#118alue");
/* 774 */     source = source.replaceAll("about:", "about&#58");
/* 775 */     source = source.replaceAll("file:", "file&#58");
/* 776 */     source = source.replaceAll("document.cookie", "documents&#46cookie");
/* 777 */     source = source.replaceAll("vbscript:", "&#118bscript:");
/* 778 */     source = source.replaceAll("vbs:", "&#118bs:");
/* 779 */     source = doFilter("(on(mouse|exit|error|click|key))", "&#111n$2", source);
/*     */ 
/* 781 */     return source;
/*     */   }
/*     */ 
/*     */   public static String htmlDecode(String htmlContent)
/*     */   {
/* 791 */     htmlContent = formatScript(htmlContent);
/* 792 */     htmlContent = htmlContent.replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n\r", "<br>").replaceAll("\r\n", "<br>").replaceAll("\r", "<br>");
/*     */ 
/* 796 */     return htmlContent;
/*     */   }
/*     */ 
/*     */   public static String addPrefix(String table, String prefix)
/*     */   {
/* 807 */     String result = "";
/* 808 */     if (table.length() > prefix.length()) {
/* 809 */       if (table.substring(0, prefix.length()).toLowerCase().equals(prefix.toLowerCase()))
/*     */       {
/* 811 */         result = table;
/*     */       }
/* 813 */       else result = prefix + table; 
/*     */     }
/*     */     else {
/* 815 */       result = prefix + table;
/*     */     }
/* 817 */     return result;
/*     */   }
/*     */ 
/*     */   public static String addSuffix(String table, String suffix) {
/* 821 */     String result = "";
/* 822 */     if (table.length() > suffix.length()) {
/* 823 */       int start = table.length() - suffix.length();
/* 824 */       int end = start + suffix.length();
/* 825 */       if (table.substring(start, end).toLowerCase().equals(suffix.toLowerCase()))
/*     */       {
/* 827 */         result = table;
/*     */       }
/* 829 */       else result = table + suffix; 
/*     */     }
/*     */     else {
/* 831 */       result = table + suffix;
/*     */     }
/* 833 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.util.StringUtil
 * JD-Core Version:    0.6.0
 */