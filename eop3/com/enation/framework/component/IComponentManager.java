package com.enation.framework.component;

import java.util.List;

public abstract interface IComponentManager
{
  public abstract void startComponents();

  public abstract void saasStartComponents();

  public abstract List<ComponentView> list();

  public abstract void install(String paramString);

  public abstract void unInstall(String paramString);

  public abstract void start(String paramString);

  public abstract void stop(String paramString);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.component.IComponentManager
 * JD-Core Version:    0.6.0
 */