package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.PaymentLog;
import java.util.List;

public abstract interface IPaymentLogManager
{
  public abstract List<PaymentLog> list(int paramInt1, int paramInt2, int paramInt3);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.IPaymentLogManager
 * JD-Core Version:    0.6.0
 */