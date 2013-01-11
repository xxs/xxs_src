package com.enation.eop.sdk.provider;

import java.util.Map;

public abstract interface DataProvider
{
  public abstract Object load(Map<String, String> paramMap);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.provider.DataProvider
 * JD-Core Version:    0.6.0
 */