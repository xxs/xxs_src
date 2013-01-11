package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Order;

public abstract interface IOrderTabShowEvent
{
  public abstract String getTabName(Order paramOrder);

  public abstract int getOrder();

  public abstract boolean canBeExecute(Order paramOrder);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.order.IOrderTabShowEvent
 * JD-Core Version:    0.6.0
 */