package com.enation.app.shop.core.service.promotion;

public abstract interface IPromotionMethod
{
  public abstract String getName();

  public abstract String getInputHtml(Integer paramInteger, String paramString);

  public abstract String onPromotionSave(Integer paramInteger);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.promotion.IPromotionMethod
 * JD-Core Version:    0.6.0
 */