package com.enation.eop.sdk.widget;

import java.util.Map;

public abstract interface IWidget
{
  public abstract String process(Map<String, String> paramMap);

  public abstract String setting(Map<String, String> paramMap);

  public abstract void update(Map<String, String> paramMap);

  public abstract boolean cacheAble();
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.widget.IWidget
 * JD-Core Version:    0.6.0
 */