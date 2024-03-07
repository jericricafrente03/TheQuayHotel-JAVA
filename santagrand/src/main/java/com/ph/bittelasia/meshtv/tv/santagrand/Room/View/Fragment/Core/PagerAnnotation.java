package com.ph.bittelasia.meshtv.tv.santagrand.Room.View.Fragment.Core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface  PagerAnnotation
{
    int vpLayout();
}
