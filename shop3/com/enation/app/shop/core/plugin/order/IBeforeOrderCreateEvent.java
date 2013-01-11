package com.enation.app.shop.core.plugin.order;

import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.support.CartItem;
import java.util.List;

public abstract interface IBeforeOrderCreateEvent
{
  public abstract void onBeforeOrderCreate(Order paramOrder, List<CartItem> paramList, String paramString);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.order.IBeforeOrderCreateEvent
 * JD-Core Version:    0.6.0
 */