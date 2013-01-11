package com.enation.framework.database.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public abstract interface IRowMapperColumnFilter
{
  public abstract void filter(Map paramMap, ResultSet paramResultSet)
    throws SQLException;
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.impl.IRowMapperColumnFilter
 * JD-Core Version:    0.6.0
 */