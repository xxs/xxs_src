package com.enation.app.shop.core.plugin.search;

import java.sql.ResultSet;
import java.util.Map;

public abstract interface IGoodsDataFilter
{
  public abstract void filter(Map<String, Object> paramMap, ResultSet paramResultSet);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.search.IGoodsDataFilter
 * JD-Core Version:    0.6.0
 */