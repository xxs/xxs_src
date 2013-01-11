package com.enation.app.shop.core.plugin.payment;

import com.enation.app.shop.core.model.Order;
import com.enation.app.shop.core.model.PayCfg;

public abstract interface IPaymentEvent
{
  public abstract String onPay(PayCfg paramPayCfg, Order paramOrder);

  public abstract String onCallBack();

  public abstract String onReturn();
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.payment.IPaymentEvent
 * JD-Core Version:    0.6.0
 */