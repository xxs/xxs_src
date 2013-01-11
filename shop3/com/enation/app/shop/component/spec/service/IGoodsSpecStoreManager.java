package com.enation.app.shop.component.spec.service;

import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface IGoodsSpecStoreManager
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract List<Map> listGoodsStore(int paramInt);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int stock(int paramInt, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int saveStore(int paramInt, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4);

  @Transactional(propagation=Propagation.REQUIRED)
  public abstract int ship(int paramInt, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3, String[] paramArrayOfString4);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.service.IGoodsSpecStoreManager
 * JD-Core Version:    0.6.0
 */