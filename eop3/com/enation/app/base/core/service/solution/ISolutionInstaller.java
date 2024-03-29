package com.enation.app.base.core.service.solution;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract interface ISolutionInstaller
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void install(Integer paramInteger1, Integer paramInteger2, String paramString);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.ISolutionInstaller
 * JD-Core Version:    0.6.0
 */