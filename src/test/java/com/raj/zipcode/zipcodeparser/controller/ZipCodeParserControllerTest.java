package com.raj.zipcode.zipcodeparser.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.raj.zipcode.zipcodeparser.domain.ZipCodeRange;

@RunWith(SpringJUnit4ClassRunner.class)
public class ZipCodeParserControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private ZipCodeParserController parserController;

	@Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(parserController)
               .build();
    }
	
	@Test
	public void testValidateService() throws Exception {
	        String json = "{\n" +
	        		"    \"zipCodeRanges\" : [{\n" + 
	        		"        \"lowerBound\":\"94133\",\n" + 
	        		"        \"upperBound\":\"94133\"\n" + 
	        		"    }, {\n" + 
	        		"        \"lowerBound\":\"94200\",\n" + 
	        		"        \"upperBound\":\"94299\"\n" + 
	        		"    }, {\n" + 
	        		"        \"lowerBound\":\"94600\",\n" + 
	        		"        \"upperBound\":\"94699\"\n" + 
	        		"    }, {\n" + 
	        		"        \"lowerBound\":\"94670\",\n" + 
	        		"        \"upperBound\":\"94899\"\n" + 
	        		"    }\n" + 
	        		"    ]\n" + 
	        		"}";
	        
	        mockMvc.perform(post("/rest/zipcode/validate")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(json))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.zipCodeRanges", Matchers.hasSize(3)))
	                .andExpect(jsonPath("$.zipCodeRanges[2].lowerBound", is(94600)))
	                ;
	    }
	 
	@Test
	public void testRemoveverLap() {
		List<ZipCodeRange> zipCodeRanges = new ArrayList<>();
		ZipCodeRange zipcodeRange = new ZipCodeRange();
		zipcodeRange.setLowerBound(94133);
		zipcodeRange.setUpperBound(94133);
		zipCodeRanges.add(zipcodeRange);

		zipcodeRange = new ZipCodeRange();
		zipcodeRange.setLowerBound(94200);
		zipcodeRange.setUpperBound(94400);
		zipCodeRanges.add(zipcodeRange);

		zipcodeRange = new ZipCodeRange();
		zipcodeRange.setLowerBound(94100);
		zipcodeRange.setUpperBound(94299);
		zipCodeRanges.add(zipcodeRange);

		zipcodeRange = new ZipCodeRange();
		zipcodeRange.setLowerBound(94510);
		zipcodeRange.setUpperBound(94670);
		zipCodeRanges.add(zipcodeRange);

		zipcodeRange = new ZipCodeRange();
		zipcodeRange.setLowerBound(94300);
		zipcodeRange.setUpperBound(94599);
		zipCodeRanges.add(zipcodeRange);

		List<ZipCodeRange> zipCodeList = parserController.removeOverLap(zipCodeRanges);
		assertEquals(1, zipCodeList.size());

	}
}
