package com.enation.app.shop.core.service.batchimport;

import org.apache.poi.ss.usermodel.Sheet;
import org.w3c.dom.Node;

public abstract interface IImportPreprocess
{
  public abstract void preprocess(Sheet paramSheet, Node paramNode);
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\shop\
 * Qualified Name:     com.enation.app.shop.core.service.batchimport.IImportPreprocess
 * JD-Core Version:    0.6.0
 */