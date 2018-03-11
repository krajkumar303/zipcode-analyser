package com.raj.zipcode.zipcodeparser.controller;

import com.raj.zipcode.zipcodeparser.domain.ZipCodeRange;
import com.raj.zipcode.zipcodeparser.domain.ZipCodeRangeRequest;
import com.raj.zipcode.zipcodeparser.domain.ZipCodeRangeResponse;
import com.raj.zipcode.zipcodeparser.service.ZipCodeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class will help us to create a WebService which will accept range of
 * Zipcode and filter them if there are any overlap.
 * 
 * @author raja
 *
 */
@RestController
@RequestMapping("/rest")
public class ZipCodeController {

  @Autowired
  ZipCodeService zipCodeService;

  /**
   * This method used to consume the List of ZipCode range as a JSON from the
   * client and will remove the overlapping Zipcode.
   * 
   * @param request
   *          ZipCodeRanges
   * @return ZipCodeRangeResponse
   */
  @PostMapping(
      value = "/zipcode",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  public ZipCodeRangeResponse validateZipcode(@RequestBody final ZipCodeRangeRequest request) {
    List<ZipCodeRange> zipCodeRanges = request.getZipCodeRanges();
    zipCodeRanges = zipCodeService.removeOverLap(zipCodeRanges);
    ZipCodeRangeResponse response = new ZipCodeRangeResponse();
    response.setZipCodeRanges(zipCodeRanges);
    return response;
  }

}
