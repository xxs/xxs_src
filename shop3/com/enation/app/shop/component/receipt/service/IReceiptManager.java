package com.enation.app.shop.component.receipt.service;

import com.enation.app.shop.component.receipt.Receipt;

public abstract interface IReceiptManager
{
  public abstract void add(Receipt paramReceipt);

  public abstract Receipt getByOrderid(Integer paramInteger);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.receipt.service.IReceiptManager
 * JD-Core Version:    0.6.0
 */