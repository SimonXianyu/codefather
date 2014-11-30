package com.github.SimonXianyu.codefather.model.vali;

import com.github.SimonXianyu.codefather.model.AttributeDef;
import com.github.SimonXianyu.codefather.model.PropertyDef;

/**
 *
 * Created by simon on 14/11/30.
 */
public interface AttributeValidator {
    boolean isValid(AttributeDef attributeDef, PropertyDef propertyDef);
}
