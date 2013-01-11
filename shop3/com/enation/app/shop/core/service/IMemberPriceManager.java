package com.enation.app.shop.core.service;

import com.enation.app.shop.core.model.GoodsLvPrice;
import java.util.List;

public abstract interface IMemberPriceManager
{
  public abstract void save(List<GoodsLvPrice> paramList);

  public abstract List<GoodsLvPrice> listPriceByGid(int paramInt);

  public abstract List<GoodsLvPrice> listPriceByLvid(int paramInt);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.IMemberPriceManager
 * JD-Core Version:    0.6.0
 */