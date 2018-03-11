package com.raj.zipcode.zipcodeparser.domain;

import java.util.List;

public class ZipCodeRangeRequest {

  private List<ZipCodeRange> zipCodeRanges;

  public List<ZipCodeRange> getZipCodeRanges() {
    return zipCodeRanges;
  }

  public void setZipCodeRanges(List<ZipCodeRange> zipCodeRanges) {
    this.zipCodeRanges = zipCodeRanges;
  }

}
