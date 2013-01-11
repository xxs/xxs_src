package com.enation.eop.processor;

import com.enation.eop.processor.core.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract interface Processor
{
  public abstract Response process(int paramInt, HttpServletResponse paramHttpServletResponse, HttpServletRequest paramHttpServletRequest);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.eop.processor.Processor
 * JD-Core Version:    0.6.0
 */