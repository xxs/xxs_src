package com.enation.app.base.core.service;

import javax.sql.DataSource;

public abstract interface IDataSourceCreator
{
  public abstract DataSource createDataSource(String paramString1, String paramString2, String paramString3, String paramString4);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.IDataSourceCreator
 * JD-Core Version:    0.6.0
 */