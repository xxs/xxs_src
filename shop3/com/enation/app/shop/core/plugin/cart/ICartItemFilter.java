package com.enation.app.shop.core.plugin.cart;

import com.enation.app.shop.core.model.support.CartItem;
import java.util.List;

public abstract interface ICartItemFilter
{
  public abstract void filter(List<CartItem> paramList, String paramString);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.cart.ICartItemFilter
 * JD-Core Version:    0.6.0
 */