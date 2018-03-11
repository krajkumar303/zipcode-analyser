package com.raj.zipcode.zipcodeparser.domain;

import java.io.Serializable;

public class ZipCodeRange implements Serializable {

  private static final long serialVersionUID = -3052596279402558481L;

  private String lowerBound;

  private String upperBound;

  public String getLowerBound() {
    return lowerBound;
  }

  public void setLowerBound(String lowerBound) {
    this.lowerBound = lowerBound;
  }

  public String getUpperBound() {
    return upperBound;
  }

  public void setUpperBound(String upperBound) {
    this.upperBound = upperBound;
  }

}
