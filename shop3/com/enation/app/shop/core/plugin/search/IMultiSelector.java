package com.enation.app.shop.core.plugin.search;

import com.enation.app.shop.core.model.Cat;
import java.util.List;
import java.util.Map;

public abstract interface IMultiSelector
{
  public abstract Map<String, List<SearchSelector>> createMultiSelector(Cat paramCat, String paramString1, String paramString2);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.search.IMultiSelector
 * JD-Core Version:    0.6.0
 */