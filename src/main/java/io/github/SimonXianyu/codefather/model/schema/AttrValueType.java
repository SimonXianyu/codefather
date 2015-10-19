package io.github.SimonXianyu.codefather.model.schema;

/**
 * Define acceptable attribute type
 * Created by simon on 14-6-20.
 */
public enum AttrValueType {
     STRING, ENUM, BOOLEAN, NUM_BOOL, NUMBER,INT;

     public static AttrValueType fromString(String str) {
          if ("string".equalsIgnoreCase(str)) {
               return STRING;
          } else if ("ENUM".equalsIgnoreCase(str)) {
               return ENUM;
          } else if ("BOOLEAN".equalsIgnoreCase(str)) {
               return BOOLEAN;
          } else if ("NUM_BOOL".equalsIgnoreCase(str)) {
               return NUM_BOOL;
          } else if ("NUMBER".equalsIgnoreCase(str)) {
               return NUMBER;
          } else if ("INT".equalsIgnoreCase(str)) {
               return INT;
          }
          return null;
     }
}
