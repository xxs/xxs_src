package com.enation.framework.cache;

public abstract interface ICache<T>
{
  public abstract T get(Object paramObject);

  public abstract void put(Object paramObject, T paramT);

  public abstract void remove(Object paramObject);

  public abstract void clear();
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.cache.ICache
 * JD-Core Version:    0.6.0
 */