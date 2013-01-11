package com.enation.app.shop.core.plugin.goods;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface IGoodsBeforeAddEvent
{
  public abstract void onBeforeGoodsAdd(Map paramMap, HttpServletRequest paramHttpServletRequest);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.goods.IGoodsBeforeAddEvent
 * JD-Core Version:    0.6.0
 */