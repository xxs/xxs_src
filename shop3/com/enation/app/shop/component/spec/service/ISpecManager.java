package com.enation.app.shop.component.spec.service;

import com.enation.app.shop.core.model.SpecValue;
import com.enation.app.shop.core.model.Specification;
import java.util.List;
import java.util.Map;

public abstract interface ISpecManager
{
  public abstract boolean checkUsed(Integer[] paramArrayOfInteger);

  public abstract boolean checkUsed(Integer paramInteger);

  public abstract List list();

  public abstract void add(Specification paramSpecification, List<SpecValue> paramList);

  public abstract void edit(Specification paramSpecification, List<SpecValue> paramList);

  public abstract void delete(Integer[] paramArrayOfInteger);

  public abstract Map get(Integer paramInteger);

  public abstract List<Map<String, String>> getProSpecList(int paramInt);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.service.ISpecManager
 * JD-Core Version:    0.6.0
 */