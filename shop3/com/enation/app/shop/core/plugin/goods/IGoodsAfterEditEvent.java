package com.enation.app.shop.core.plugin.goods;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface IGoodsAfterEditEvent
{
  public abstract void onAfterGoodsEdit(Map paramMap, HttpServletRequest paramHttpServletRequest);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.goods.IGoodsAfterEditEvent
 * JD-Core Version:    0.6.0
 */