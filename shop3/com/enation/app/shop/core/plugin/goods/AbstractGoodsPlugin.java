package com.enation.app.shop.core.plugin.goods;

import com.enation.framework.plugin.AutoRegisterPlugin;

public abstract class AbstractGoodsPlugin extends AutoRegisterPlugin
  implements IGetGoodsAddHtmlEvent, IGoodsBeforeAddEvent, IGetGoodsEditHtmlEvent, IGoodsAfterAddEvent, IGoodsAfterEditEvent, IGoodsBeforeEditEvent
{
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.plugin.goods.AbstractGoodsPlugin
 * JD-Core Version:    0.6.0
 */