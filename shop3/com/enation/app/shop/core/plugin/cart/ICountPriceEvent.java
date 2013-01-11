package com.enation.app.shop.core.plugin.cart;

import com.enation.app.shop.core.model.support.OrderPrice;

public abstract interface ICountPriceEvent
{
  public abstract OrderPrice countPrice(OrderPrice paramOrderPrice);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.cart.ICountPriceEvent
 * JD-Core Version:    0.6.0
 */