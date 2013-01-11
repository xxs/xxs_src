package com.enation.app.base.core.service.solution;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;

public abstract interface IInstaller
{
  @Transactional(propagation=Propagation.REQUIRED)
  public abstract void install(String paramString, Node paramNode);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.service.solution.IInstaller
 * JD-Core Version:    0.6.0
 */