package com.raj.zipcode.zipcodeparser.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.raj.zipcode.zipcodeparser.domain.ZipCodeRange;
import com.raj.zipcode.zipcodeparser.domain.ZipCodeRangeRequest;
import com.raj.zipcode.zipcodeparser.service.ZipCodeService;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class ZipCodeControllerTest {

  private MockMvc mockMvc;
  Gson gson;

  @Mock
  private ZipCodeService zipCodeService;

  @InjectMocks
  private ZipCodeController parserController;

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.standaloneSetup(parserController).build();
    gson = new Gson();
  }

  /**
   * This method used to test the validateZipcode method in
   * {@link ZipCodeController}.
   * 
   * @throws Exception
   *           exception thrown
   */
  @Test
  public void testValidateZipCode() throws Exception {
    // Populating request List
    List<ZipCodeRange> requestList = new ArrayList<>();
    ZipCodeRange zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("94133");
    zipcodeRange.setUpperBound("94133");
    requestList.add(zipcodeRange);

    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("94200");
    zipcodeRange.setUpperBound("94400");
    requestList.add(zipcodeRange);

    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("94220");
    zipcodeRange.setUpperBound("94299");
    requestList.add(zipcodeRange);

    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("94510");
    zipcodeRange.setUpperBound("94670");
    requestList.add(zipcodeRange);

    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("94300");
    zipcodeRange.setUpperBound("94599");
    requestList.add(zipcodeRange);

    ZipCodeRangeRequest request = new ZipCodeRangeRequest();
    request.setZipCodeRanges(requestList);

    // Populating Response list
    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("94133");
    zipcodeRange.setUpperBound("94133");
    List<ZipCodeRange> responseList = new ArrayList<>();
    responseList.add(zipcodeRange);

    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("94200");
    zipcodeRange.setUpperBound("94670");
    responseList.add(zipcodeRange);

    when(zipCodeService.removeOverLap(refEq(request.getZipCodeRanges()))).thenReturn(responseList);

    mockMvc
        .perform(post("/rest/zipcode").contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(request)))
        .andExpect(status().isOk()).andExpect(jsonPath("$.zipCodeRanges", Matchers.hasSize(2)))
        .andExpect(jsonPath("$.zipCodeRanges[0].upperBound", is("94133")));

    verify(zipCodeService).removeOverLap(refEq(request.getZipCodeRanges()));
  }

  /**
   * This method will test the removeOverLap method in {@link ZipCodeService}.
   */
  @Test
  public void testRemoveverLap() {
    List<ZipCodeRange> requestList = new ArrayList<>();
    ZipCodeRange zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("07120");
    zipcodeRange.setUpperBound("07500");
    requestList.add(zipcodeRange);

    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("30000");
    zipcodeRange.setUpperBound("30002");
    requestList.add(zipcodeRange);

    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("06000");
    zipcodeRange.setUpperBound("07300");
    requestList.add(zipcodeRange);

    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("30004");
    zipcodeRange.setUpperBound("30008");
    requestList.add(zipcodeRange);

    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("06030");
    zipcodeRange.setUpperBound("07500");
    List<ZipCodeRange> responseList = new ArrayList<>();
    responseList.add(zipcodeRange);

    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("94200");
    zipcodeRange.setUpperBound("94400");
    responseList.add(zipcodeRange);

    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("30000");
    zipcodeRange.setUpperBound("30002");
    responseList.add(zipcodeRange);

    zipcodeRange = new ZipCodeRange();
    zipcodeRange.setLowerBound("30004");
    zipcodeRange.setUpperBound("30008");
    responseList.add(zipcodeRange);

    when(zipCodeService.removeOverLap(requestList)).thenReturn(responseList);

    List<ZipCodeRange> zipCodeList = zipCodeService.removeOverLap(requestList);
    assertEquals(4, zipCodeList.size());

  }
}
