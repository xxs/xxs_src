/*     */ package com.enation.eop.sdk.utils;
/*     */ 
/*     */ import com.enation.framework.context.webcontext.ThreadContextHolder;
/*     */ import com.enation.framework.context.webcontext.WebSessionContext;
/*     */ import com.sun.image.codec.jpeg.JPEGCodec;
/*     */ import com.sun.image.codec.jpeg.JPEGImageEncoder;
/*     */ import java.awt.Color;
/*     */ import java.awt.Font;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.font.FontRenderContext;
/*     */ import java.awt.font.TextLayout;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.text.AttributedString;
/*     */ import java.util.Random;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ public class ValidCodeServlet extends HttpServlet
/*     */ {
/*     */   public static final String SESSION_VALID_CODE = "valid_code";
/*  28 */   private Random generator = new Random();
/*     */ 
/*  30 */   private static char[] captchars = { 'a', 'b', 'c', 'd', 'e', '2', '3', '4', '5', '6', '7', '8', 'g', 'f', 'y', 'n', 'm', 'n', 'p', 'w', 'x' };
/*     */ 
/*     */   private Font getFont()
/*     */   {
/*  40 */     Random random = new Random();
/*  41 */     Font[] font = new Font[5];
/*  42 */     font[0] = new Font("Ravie", 0, 45);
/*  43 */     font[1] = new Font("Antique Olive Compact", 0, 45);
/*  44 */     font[2] = new Font("Forte", 0, 45);
/*  45 */     font[3] = new Font("Wide Latin", 0, 40);
/*  46 */     font[4] = new Font("Gill Sans Ultra Bold", 0, 45);
/*  47 */     return font[random.nextInt(5)];
/*     */   }
/*     */ 
/*     */   private Color getRandColor()
/*     */   {
/*  56 */     Random random = new Random();
/*  57 */     Color[] color = new Color[10];
/*  58 */     color[0] = new Color(32, 158, 25);
/*  59 */     color[1] = new Color(218, 42, 19);
/*  60 */     color[2] = new Color(31, 75, 208);
/*  61 */     return color[random.nextInt(3)];
/*     */   }
/*     */ 
/*     */   public void doGet(HttpServletRequest req, HttpServletResponse resp)
/*     */     throws ServletException, IOException
/*     */   {
/*  72 */     resp.addHeader("Cache-Control", "no-cache");
/*  73 */     resp.addHeader("Cache-Control", "no-store");
/*  74 */     resp.addHeader("Cache-Control", "must-revalidate");
/*  75 */     resp.setHeader("Pragma", "no-cache");
/*  76 */     resp.setDateHeader("Expires", -1L);
/*     */ 
/*  78 */     String vtype = "";
/*     */ 
/*  80 */     if (req.getParameter("vtype") != null) {
/*  81 */       vtype = req.getParameter("vtype");
/*     */     }
/*     */ 
/*  85 */     int ImageWidth = 200;
/*  86 */     int ImageHeight = 100;
/*     */ 
/*  88 */     int car = captchars.length - 1;
/*     */ 
/*  92 */     String test = "";
/*  93 */     for (int i = 0; i < 4; i++) {
/*  94 */       test = test + captchars[(this.generator.nextInt(car) + 1)];
/*     */     }
/*     */ 
/* 101 */     ThreadContextHolder.getSessionContext().setAttribute("valid_code" + vtype, test);
/*     */ 
/* 106 */     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(resp.getOutputStream());
/*     */ 
/* 109 */     BufferedImage bi = new BufferedImage(ImageWidth + 10, ImageHeight, 13);
/*     */ 
/* 112 */     Graphics2D graphics = bi.createGraphics();
/*     */ 
/* 117 */     graphics.setColor(Color.white);
/*     */ 
/* 119 */     graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
/*     */ 
/* 121 */     graphics.setColor(Color.black);
/* 122 */     AttributedString attstr = new AttributedString(test);
/*     */ 
/* 124 */     TextLayout textTl = new TextLayout(test, new Font("Courier", 1, 70), new FontRenderContext(null, true, false));
/*     */ 
/* 126 */     AffineTransform textAt = graphics.getTransform();
/* 127 */     graphics.setFont(new Font("Courier", 1, 70));
/* 128 */     graphics.setColor(getRandColor());
/* 129 */     graphics.drawString(test, 10, 70);
/*     */ 
/* 131 */     int w = bi.getWidth();
/* 132 */     int h = bi.getHeight();
/* 133 */     shear(graphics, w, h, Color.white);
/*     */ 
/* 137 */     resp.setContentType("image/jpg");
/*     */ 
/* 139 */     encoder.encode(bi);
/*     */   }
/*     */ 
/*     */   private void shear(Graphics g, int w1, int h1, Color color)
/*     */   {
/* 145 */     shearX(g, w1, h1, color);
/* 146 */     shearY(g, w1, h1, color);
/*     */   }
/*     */ 
/*     */   public void shearX(Graphics g, int w1, int h1, Color color)
/*     */   {
/* 151 */     int period = this.generator.nextInt(2);
/*     */ 
/* 153 */     boolean borderGap = true;
/* 154 */     int frames = 1;
/* 155 */     int phase = this.generator.nextInt(2);
/*     */ 
/* 157 */     for (int i = 0; i < h1; i++) {
/* 158 */       double d = (period >> 1) * Math.sin(i / period + 6.283185307179586D * phase / frames);
/*     */ 
/* 162 */       g.copyArea(0, i, w1, 1, (int)d, 0);
/* 163 */       if (borderGap) {
/* 164 */         g.setColor(color);
/* 165 */         g.drawLine((int)d, i, 0, i);
/* 166 */         g.drawLine((int)d + w1, i, w1, i);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void shearY(Graphics g, int w1, int h1, Color color)
/*     */   {
/* 174 */     int period = this.generator.nextInt(40) + 10;
/*     */ 
/* 176 */     boolean borderGap = true;
/* 177 */     int frames = 20;
/* 178 */     int phase = 7;
/* 179 */     for (int i = 0; i < w1; i++) {
/* 180 */       double d = (period >> 1) * Math.sin(i / period + 6.283185307179586D * phase / frames);
/*     */ 
/* 184 */       g.copyArea(i, 0, 1, h1, 0, (int)d);
/* 185 */       if (borderGap) {
/* 186 */         g.setColor(color);
/* 187 */         g.drawLine(i, (int)d, i, 0);
/* 188 */         g.drawLine(i, (int)d + h1, i, h1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, int thickness, Color c)
/*     */   {
/* 199 */     g.setColor(c);
/* 200 */     int dX = x2 - x1;
/* 201 */     int dY = y2 - y1;
/*     */ 
/* 203 */     double lineLength = Math.sqrt(dX * dX + dY * dY);
/*     */ 
/* 205 */     double scale = thickness / (2.0D * lineLength);
/*     */ 
/* 209 */     double ddx = -scale * dY;
/* 210 */     double ddy = scale * dX;
/* 211 */     ddx += (ddx > 0.0D ? 0.5D : -0.5D);
/* 212 */     ddy += (ddy > 0.0D ? 0.5D : -0.5D);
/* 213 */     int dx = (int)ddx;
/* 214 */     int dy = (int)ddy;
/*     */ 
/* 217 */     int[] xPoints = new int[4];
/* 218 */     int[] yPoints = new int[4];
/*     */ 
/* 220 */     xPoints[0] = (x1 + dx);
/* 221 */     yPoints[0] = (y1 + dy);
/* 222 */     xPoints[1] = (x1 - dx);
/* 223 */     yPoints[1] = (y1 - dy);
/* 224 */     xPoints[2] = (x2 - dx);
/* 225 */     yPoints[2] = (y2 - dy);
/* 226 */     xPoints[3] = (x2 + dx);
/* 227 */     yPoints[3] = (y2 + dy);
/*     */ 
/* 229 */     g.fillPolygon(xPoints, yPoints, 4);
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.utils.ValidCodeServlet
 * JD-Core Version:    0.6.0
 */