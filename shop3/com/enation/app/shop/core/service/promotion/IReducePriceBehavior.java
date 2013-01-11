package com.enation.app.shop.core.service.promotion;

import com.enation.app.shop.core.model.Promotion;

public abstract interface IReducePriceBehavior extends IPromotionBehavior
{
  public abstract Double reducedPrice(Promotion paramPromotion, Double paramDouble);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.promotion.IReducePriceBehavior
 * JD-Core Version:    0.6.0
 */