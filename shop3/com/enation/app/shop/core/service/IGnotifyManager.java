package com.enation.app.shop.core.service;

import com.enation.framework.database.Page;

public abstract interface IGnotifyManager
{
  public abstract Page pageGnotify(int paramInt1, int paramInt2);

  public abstract void deleteGnotify(int paramInt);

  public abstract void addGnotify(int paramInt);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.IGnotifyManager
 * JD-Core Version:    0.6.0
 */