/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.8
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.grapenlp.core;

public final class rtno_type {
  public final static rtno_type LEXMASK_X_LETTER_ARRAY_RTNO = new rtno_type("LEXMASK_X_LETTER_ARRAY_RTNO");
  public final static rtno_type LEXMASK_X_WEIGHTED_LETTER_ARRAY_RTNO = new rtno_type("LEXMASK_X_WEIGHTED_LETTER_ARRAY_RTNO");
  public final static rtno_type LEXMASK_X_EXTRACTION_RTNO = new rtno_type("LEXMASK_X_EXTRACTION_RTNO");
  public final static rtno_type LEXMASK_X_WEIGHTED_EXTRACTION_RTNO = new rtno_type("LEXMASK_X_WEIGHTED_EXTRACTION_RTNO");
  public final static rtno_type LEXMASK_X_BRACKETING_RTNO = new rtno_type("LEXMASK_X_BRACKETING_RTNO");
  public final static rtno_type LEXMASK_X_WEIGHTED_BRACKETING_RTNO = new rtno_type("LEXMASK_X_WEIGHTED_BRACKETING_RTNO");

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static rtno_type swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + rtno_type.class + " with value " + swigValue);
  }

  private rtno_type(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private rtno_type(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private rtno_type(String swigName, rtno_type swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static rtno_type[] swigValues = { LEXMASK_X_LETTER_ARRAY_RTNO, LEXMASK_X_WEIGHTED_LETTER_ARRAY_RTNO, LEXMASK_X_EXTRACTION_RTNO, LEXMASK_X_WEIGHTED_EXTRACTION_RTNO, LEXMASK_X_BRACKETING_RTNO, LEXMASK_X_WEIGHTED_BRACKETING_RTNO };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

