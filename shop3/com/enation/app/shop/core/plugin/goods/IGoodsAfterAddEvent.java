package com.enation.app.shop.core.plugin.goods;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public abstract interface IGoodsAfterAddEvent
{
  public abstract void onAfterGoodsAdd(Map paramMap, HttpServletRequest paramHttpServletRequest)
    throws RuntimeException;
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.goods.IGoodsAfterAddEvent
 * JD-Core Version:    0.6.0
 */