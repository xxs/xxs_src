package com.enation.app.shop.core.service.promotion;

import com.enation.app.shop.core.model.Promotion;
import java.util.List;

public abstract interface IGiveGiftBehavior extends IPromotionBehavior
{
  public abstract void giveGift(Promotion paramPromotion, Integer paramInteger);

  public abstract List getGiftList(Promotion paramPromotion);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.promotion.IGiveGiftBehavior
 * JD-Core Version:    0.6.0
 */