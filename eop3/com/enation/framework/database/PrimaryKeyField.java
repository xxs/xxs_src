package com.enation.framework.database;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface PrimaryKeyField
{
}

/* Location:           C:\Users\xiaxiaoshi\Desktop\eop\
 * Qualified Name:     com.enation.framework.database.PrimaryKeyField
 * JD-Core Version:    0.6.0
 */