package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Order;

public abstract interface IOrderConfirmPayEvent
{
  public abstract void confirmPay(Order paramOrder);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.order.IOrderConfirmPayEvent
 * JD-Core Version:    0.6.0
 */