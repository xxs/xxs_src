package com.enation.framework.database;

public abstract interface IDBRouter
{
  public abstract String getTableName(String paramString);

  public abstract void doSaasInstall(String paramString);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.IDBRouter
 * JD-Core Version:    0.6.0
 */