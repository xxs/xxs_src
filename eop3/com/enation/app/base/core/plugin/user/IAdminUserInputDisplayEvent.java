package com.enation.app.base.core.plugin.user;

import com.enation.eop.resource.model.AdminUser;

public abstract interface IAdminUserInputDisplayEvent
{
  public abstract String getInputHtml(AdminUser paramAdminUser);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.app.base.core.plugin.user.IAdminUserInputDisplayEvent
 * JD-Core Version:    0.6.0
 */