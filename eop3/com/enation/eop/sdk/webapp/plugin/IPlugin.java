package com.enation.eop.sdk.webapp.plugin;

public abstract interface IPlugin
{
  public abstract String getType();

  public abstract String getId();

  public abstract String getName();

  public abstract String getVersion();

  public abstract String getAuthor();

  public abstract void perform(Object[] paramArrayOfObject);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.webapp.plugin.IPlugin
 * JD-Core Version:    0.6.0
 */