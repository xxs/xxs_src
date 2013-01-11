package com.enation.app.shop.core.service.promotion;

import com.enation.app.shop.core.model.Promotion;

public abstract interface ITimesPointBehavior extends IPromotionBehavior
{
  public abstract Integer countPoint(Promotion paramPromotion, Integer paramInteger);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.promotion.ITimesPointBehavior
 * JD-Core Version:    0.6.0
 */