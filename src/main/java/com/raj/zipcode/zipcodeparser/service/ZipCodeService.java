package com.raj.zipcode.zipcodeparser.service;

import com.raj.zipcode.zipcodeparser.domain.ZipCodeRange;

import java.util.List;

public interface ZipCodeService {

  List<ZipCodeRange> removeOverLap(List<ZipCodeRange> zipCodeRanges);

}
