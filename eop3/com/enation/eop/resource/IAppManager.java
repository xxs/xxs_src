package com.enation.eop.resource;

import com.enation.eop.resource.model.EopApp;
import java.util.List;

public abstract interface IAppManager
{
  public abstract void add(EopApp paramEopApp);

  public abstract List<EopApp> list();

  public abstract EopApp get(String paramString);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.IAppManager
 * JD-Core Version:    0.6.0
 */