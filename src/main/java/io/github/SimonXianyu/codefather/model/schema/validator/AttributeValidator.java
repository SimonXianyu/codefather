package io.github.SimonXianyu.codefather.model.schema.validator;

import io.github.SimonXianyu.codefather.model.schema.AttributeDef;
import io.github.SimonXianyu.codefather.model.PropertyDef;

/**
 *
 * Created by simon on 14/11/30.
 */
public interface AttributeValidator {
    boolean isValid(AttributeDef attributeDef, PropertyDef propertyDef);
}
