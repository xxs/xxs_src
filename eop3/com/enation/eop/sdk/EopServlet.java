package com.enation.eop.sdk;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface EopServlet
{
  public abstract void service(HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException;
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.sdk.EopServlet
 * JD-Core Version:    0.6.0
 */