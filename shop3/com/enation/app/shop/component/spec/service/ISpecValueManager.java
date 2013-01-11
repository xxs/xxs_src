package com.enation.app.shop.component.spec.service;

import com.enation.app.shop.core.model.SpecValue;
import java.util.List;
import java.util.Map;

public abstract interface ISpecValueManager
{
  public abstract void add(SpecValue paramSpecValue);

  public abstract void update(SpecValue paramSpecValue);

  public abstract List<SpecValue> list(Integer paramInteger);

  public abstract Map get(Integer paramInteger);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\component-shop-core\
 * Qualified Name:     com.enation.app.shop.component.spec.service.ISpecValueManager
 * JD-Core Version:    0.6.0
 */