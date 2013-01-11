package com.enation.app.base.core.plugin.database;

import java.sql.ResultSet;
import java.util.Map;

public abstract interface IColumnFilterEvent
{
  public abstract void filter(Map paramMap, ResultSet paramResultSet);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.plugin.database.IColumnFilterEvent
 * JD-Core Version:    0.6.0
 */