package com.enation.app.shop.core.plugin.goods;

public abstract interface IGoodsSearchFilter
{
  public abstract String getSelector();

  public abstract String getFrom();

  public abstract void filter(StringBuffer paramStringBuffer);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.goods.IGoodsSearchFilter
 * JD-Core Version:    0.6.0
 */