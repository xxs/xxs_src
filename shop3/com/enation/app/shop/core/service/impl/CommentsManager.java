/*     */ package com.enation.app.shop.core.service.impl;
/*     */ 
/*     */ import com.enation.app.base.core.model.Member;
/*     */ import com.enation.app.shop.core.model.Comments;
/*     */ import com.enation.app.shop.core.model.support.CommentDTO;
/*     */ import com.enation.app.shop.core.service.ICommentsManager;
/*     */ import com.enation.eop.processor.core.EopException;
/*     */ import com.enation.eop.resource.model.EopSite;
/*     */ import com.enation.eop.sdk.context.EopContext;
/*     */ import com.enation.eop.sdk.database.BaseSupport;
/*     */ import com.enation.eop.sdk.user.IUserService;
/*     */ import com.enation.eop.sdk.user.UserServiceFactory;
/*     */ import com.enation.eop.sdk.utils.UploadUtil;
/*     */ import com.enation.framework.database.IDaoSupport;
/*     */ import com.enation.framework.database.Page;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CommentsManager extends BaseSupport<Comments>
/*     */   implements ICommentsManager
/*     */ {
/*     */   public void addComments(Comments comments)
/*     */   {
/*  34 */     this.baseDaoSupport.insert("comments", comments);
/*     */   }
/*     */ 
/*     */   public void deleteComments(String ids)
/*     */   {
/*  39 */     if ((ids == null) || (ids.equals("")))
/*  40 */       return;
/*  41 */     String sql = "update comments set disabled='true' where comment_id in (" + ids + ")";
/*     */ 
/*  43 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void cleanComments(String ids)
/*     */   {
/*  48 */     if ((ids == null) || (ids.equals("")))
/*  49 */       return;
/*  50 */     String sql = "delete  from  comments   where comment_id in (" + ids + ") or for_comment_id in (" + ids + ")";
/*     */ 
/*  52 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public CommentDTO getComments(Integer commentId)
/*     */   {
/*  57 */     String sql = "select * from comments where comment_id=?";
/*  58 */     Comments comments = (Comments)this.baseDaoSupport.queryForObject(sql, Comments.class, new Object[] { commentId });
/*     */ 
/*  61 */     CommentDTO commentDTO = new CommentDTO();
/*  62 */     commentDTO.setComments(comments);
/*  63 */     List list = this.baseDaoSupport.queryForList("select * from comments where for_comment_id = ? order by time desc", Comments.class, new Object[] { commentId });
/*     */ 
/*  66 */     commentDTO.setList(list);
/*  67 */     return commentDTO;
/*     */   }
/*     */ 
/*     */   public void revert(String ids)
/*     */   {
/*  72 */     if ((ids == null) || (ids.equals("")))
/*  73 */       return;
/*  74 */     String sql = "update comments set disabled='false' where comment_id in (" + ids + ")";
/*     */ 
/*  76 */     this.baseDaoSupport.execute(sql, new Object[0]);
/*     */   }
/*     */ 
/*     */   public void updateComments(Comments comments)
/*     */   {
/*  81 */     this.baseDaoSupport.update("comments", comments, "comment_id=" + comments.getComment_id());
/*     */   }
/*     */ 
/*     */   public Page pageComments_Display(int pageNo, int pageSize, Integer object_id, String object_type)
/*     */   {
/*  89 */     String sql = "select * from comments where for_comment_id is null and display='true' and object_id = ? and object_type=? order by time desc";
/*  90 */     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { object_id, object_type });
/*  91 */     int userid = EopContext.getContext().getCurrentSite().getUserid().intValue();
/*  92 */     int siteid = EopContext.getContext().getCurrentSite().getId().intValue();
/*  93 */     String sql_id_list = "select * from " + getTableName("comments") + " where for_comment_id in (select comment_id from " + getTableName("comments") + " where for_comment_id is null and display='true' and object_id = ? and object_type=? ) and display='true' order by time desc ";
/*  94 */     List listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, new Object[] { object_id, object_type });
/*  95 */     List list = (List)(List)page.getResult();
/*  96 */     for (Map comments : list) {
/*  97 */       List child = new ArrayList();
/*  98 */       for (Comments reply : listReply) {
/*  99 */         if (reply.getFor_comment_id().equals(Integer.valueOf(comments.get("comment_id").toString()))) {
/* 100 */           child.add(reply);
/*     */         }
/*     */       }
/* 103 */       comments.put("list", child);
/* 104 */       comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
/*     */     }
/* 106 */     return page;
/*     */   }
/*     */ 
/*     */   public Page listAll(int pageNo, int pageSize, Integer object_id, String commenttype)
/*     */   {
/* 119 */     String sql = "select * from comments where for_comment_id is null and display='true' and object_id = ? and commenttype=? order by time desc";
/* 120 */     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { object_id, commenttype });
/* 121 */     int userid = EopContext.getContext().getCurrentSite().getUserid().intValue();
/* 122 */     int siteid = EopContext.getContext().getCurrentSite().getId().intValue();
/* 123 */     String sql_id_list = "select * from " + getTableName("comments") + " where for_comment_id in (select comment_id from " + getTableName("comments") + " where for_comment_id is null and display='true' and object_id = ? and commenttype=? ) and display='true' order by time desc ";
/* 124 */     List listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, new Object[] { object_id, commenttype });
/* 125 */     List list = (List)(List)page.getResult();
/* 126 */     for (Map comments : list) {
/* 127 */       Long time = (Long)comments.get("time");
/* 128 */       comments.put("date", new Date(time.longValue()));
/* 129 */       List child = new ArrayList();
/* 130 */       for (Comments reply : listReply) {
/* 131 */         if (reply.getFor_comment_id().equals(Integer.valueOf(comments.get("comment_id").toString()))) {
/* 132 */           child.add(reply);
/*     */         }
/*     */       }
/* 135 */       comments.put("list", child);
/* 136 */       comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
/*     */     }
/* 138 */     return page;
/*     */   }
/*     */ 
/*     */   public Page pageComments(int pageNo, int pageSize, String object_type)
/*     */   {
/* 146 */     String sql = "select c.*, case c.commenttype when 'goods' then g.name when 'article' then a.title end name from " + getTableName("comments") + " c left join " + getTableName("goods") + " g on g.goods_id = c.object_id left join " + getTableName("article") + " a on a.id = c.object_id where  for_comment_id is null and c.disabled='false' and object_type=? order by time desc";
/* 147 */     Page page = this.daoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { object_type });
/* 148 */     String sql_id_list = "select * from " + getTableName("comments") + " where for_comment_id in (select comment_id from " + getTableName("comments") + " where for_comment_id is null and display='true' and object_type=? ) order by time desc ";
/* 149 */     List listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, new Object[] { object_type });
/* 150 */     List list = (List)(List)page.getResult();
/* 151 */     for (Map comments : list) {
/* 152 */       List child = new ArrayList();
/* 153 */       for (Comments reply : listReply) {
/* 154 */         if (reply.getFor_comment_id().equals(Integer.valueOf(comments.get("comment_id").toString()))) {
/* 155 */           child.add(reply);
/*     */         }
/*     */       }
/* 158 */       comments.put("list", child);
/* 159 */       comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
/*     */     }
/* 161 */     return page;
/*     */   }
/*     */ 
/*     */   public Page pageCommentsTrash(int pageNo, int pageSize, String object_type)
/*     */   {
/* 166 */     String sql = "select * from comments where for_comment_id is null and disabled='true' and object_type=? order by time desc";
/* 167 */     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { object_type });
/*     */ 
/* 169 */     String sql_id_list = "select * from " + getTableName("comments") + " where for_comment_id in (select comment_id from " + getTableName("comments") + " where for_comment_id is null and display='true' and object_type=?) order by time desc ";
/* 170 */     List listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, new Object[] { object_type });
/* 171 */     List list = (List)(List)page.getResult();
/* 172 */     for (Map comments : list) {
/* 173 */       List child = new ArrayList();
/* 174 */       for (Comments reply : listReply) {
/* 175 */         if (reply.getFor_comment_id().equals(Integer.valueOf(comments.get("comment_id").toString()))) {
/* 176 */           child.add(reply);
/*     */         }
/*     */       }
/* 179 */       comments.put("list", child);
/* 180 */       comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
/*     */     }
/* 182 */     return page;
/*     */   }
/*     */ 
/*     */   public Page pageComments_Display(int pageNo, int pageSize, Integer object_id, String object_type, String commentType)
/*     */   {
/* 188 */     String sql = "select * from comments where for_comment_id is null and display='true' and object_id = ? and object_type=? and commenttype = ? order by time desc";
/* 189 */     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { object_id, object_type, commentType });
/*     */ 
/* 191 */     String sql_id_list = "select * from " + getTableName("comments") + " where for_comment_id in (select comment_id from " + getTableName("comments") + " where for_comment_id is null and display='true' and object_id = ? and object_type=? and commenttype = ?) and display='true' order by time desc ";
/* 192 */     List listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, new Object[] { object_id, object_type, commentType });
/* 193 */     List list = (List)(List)page.getResult();
/* 194 */     for (Map comments : list) {
/* 195 */       Long time = (Long)comments.get("time");
/* 196 */       comments.put("date", new Date(time.longValue()));
/* 197 */       List child = new ArrayList();
/* 198 */       for (Comments reply : listReply) {
/* 199 */         if (reply.getFor_comment_id().equals(Integer.valueOf(comments.get("comment_id").toString()))) {
/* 200 */           child.add(reply);
/*     */         }
/*     */       }
/* 203 */       comments.put("list", child);
/* 204 */       comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
/*     */     }
/* 206 */     return page;
/*     */   }
/*     */ 
/*     */   public Page pageComments_Display(int pageNo, int pageSize)
/*     */   {
/* 213 */     IUserService userService = UserServiceFactory.getUserService();
/* 214 */     Member member = userService.getCurrentMember();
/* 215 */     if (member == null) {
/* 216 */       throw new EopException("您没有登录或已过期，请重新登录！");
/*     */     }
/* 218 */     String sql = "select * from comments where for_comment_id is null and display='true' and author_id = ? order by time desc";
/* 219 */     Page page = this.baseDaoSupport.queryForPage(sql, pageNo, pageSize, new Object[] { member.getMember_id() });
/* 220 */     String sql_id_list = "select * from " + getTableName("comments") + " where for_comment_id in (select comment_id from " + getTableName("comments") + " where for_comment_id is null and display='true' and author_id = ? ) and display='true' order by time desc ";
/* 221 */     List listReply = this.daoSupport.queryForList(sql_id_list, Comments.class, new Object[] { member.getMember_id() });
/* 222 */     List list = (List)(List)page.getResult();
/* 223 */     for (Map comments : list) {
/* 224 */       Long time = (Long)comments.get("time");
/* 225 */       comments.put("date", new Date(time.longValue()));
/* 226 */       List child = new ArrayList();
/* 227 */       for (Comments reply : listReply) {
/* 228 */         if (reply.getFor_comment_id().equals(Integer.valueOf(comments.get("comment_id").toString()))) {
/* 229 */           child.add(reply);
/*     */         }
/*     */       }
/* 232 */       comments.put("list", child);
/* 233 */       comments.put("image", UploadUtil.replacePath((String)comments.get("img")));
/*     */     }
/* 235 */     return page;
/*     */   }
/*     */ 
/*     */   public List listComments(int member_id, String object_type)
/*     */   {
/* 240 */     String sql = "select * from comments where for_comment_id is null and author_id = ? and object_type = ? order by time desc";
/* 241 */     List list = this.baseDaoSupport.queryForList(sql, Comments.class, new Object[] { Integer.valueOf(member_id), object_type });
/* 242 */     return list;
/*     */   }
/*     */ 
/*     */   public Map coutObjectGrade(String commentType, Integer objectid)
/*     */   {
/* 250 */     String sql = "select grade, count(0) num  from comments where object_type ='discuss' and commenttype=? and object_id =? and   display='true'  and for_comment_id is null group by grade";
/* 251 */     List gradeNumList = this.baseDaoSupport.queryForList(sql, new Object[] { commentType, objectid });
/* 252 */     Map gradeMap = new HashMap();
/* 253 */     for (Map map : gradeNumList) {
/* 254 */       gradeMap.put("grade_" + map.get("grade"), map.get("num"));
/*     */     }
/* 256 */     return gradeMap;
/*     */   }
/*     */ 
/*     */   public Map coutObejctNum(String commentType, Integer objectid)
/*     */   {
/* 261 */     String sql = "select object_type, count(0) num  from comments where commenttype=? and object_id =?  and   display='true'  and for_comment_id is null group by object_type";
/* 262 */     List gradeNumList = this.baseDaoSupport.queryForList(sql, new Object[] { commentType, objectid });
/* 263 */     Map numMap = new HashMap();
/* 264 */     for (Map map : gradeNumList) {
/* 265 */       numMap.put(map.get("object_type") + "_num", map.get("num"));
/*     */     }
/* 267 */     return numMap;
/*     */   }
/*     */ }

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.impl.CommentsManager
 * JD-Core Version:    0.6.0
 */