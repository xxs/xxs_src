package com.enation.eop.resource;

import com.enation.eop.resource.model.EopUserDetail;

public abstract interface IUserDetailManager
{
  public abstract EopUserDetail get(Integer paramInteger);

  public abstract void add(EopUserDetail paramEopUserDetail);

  public abstract void edit(EopUserDetail paramEopUserDetail);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.resource.IUserDetailManager
 * JD-Core Version:    0.6.0
 */