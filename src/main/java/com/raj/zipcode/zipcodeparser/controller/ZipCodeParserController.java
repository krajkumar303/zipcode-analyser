package com.raj.zipcode.zipcodeparser.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.zipcode.zipcodeparser.domain.ZipCodeRange;
import com.raj.zipcode.zipcodeparser.domain.ZipCodeRangeRequest;

/**
 * This class will help us to create a WebService which will accept range of Zipcode 
 * and filter them if there are any overlap.
 * 
 * @author raja
 *
 */
@RestController
@RequestMapping("/rest/zipcode")
public class ZipCodeParserController {

	@PostMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ZipCodeRangeRequest create(@RequestBody final ZipCodeRangeRequest zipCodeRangeRequest) {
		List<ZipCodeRange> zipCodeRanges = zipCodeRangeRequest.getZipCodeRanges();
		printData(zipCodeRanges);
		removeOverLap(zipCodeRanges);
		zipCodeRangeRequest.setZipCodeRanges(zipCodeRanges);
		return zipCodeRangeRequest;
	}

	/**
	 * This method will help to remove the overlapping Zipcode
	 * 
	 * @param List
	 *            of zipCodeRanges
	 * @return List<ZipCodeRange> {@link}ZipCodeRange
	 */
	public List<ZipCodeRange> removeOverLap(List<ZipCodeRange> zipCodeRanges) {
		if (zipCodeRanges != null) {
			// Sorting the Zipcode based on lowerBound value.
			zipCodeRanges.sort((ZipCodeRange z1, ZipCodeRange z2) -> Integer.valueOf(z1.getLowerBound())
					.compareTo(Integer.valueOf(z2.getLowerBound())));
			for (int i = 0; i < zipCodeRanges.size(); i++) {
				for (int j = 1; j < zipCodeRanges.size();) {
					if (i != j
							&& Integer.valueOf(zipCodeRanges.get(j).getLowerBound()) <= Integer
									.valueOf(zipCodeRanges.get(i).getUpperBound())
							&& Integer.valueOf(zipCodeRanges.get(j).getLowerBound()) >= Integer
									.valueOf(zipCodeRanges.get(i).getLowerBound())) {
						if (Integer.valueOf(zipCodeRanges.get(j).getLowerBound()) <= Integer
								.valueOf(zipCodeRanges.get(i).getLowerBound())) {
							zipCodeRanges.get(i).setLowerBound(zipCodeRanges.get(j).getLowerBound());
						}
						if (Integer.valueOf(zipCodeRanges.get(i).getUpperBound()) <= Integer
								.valueOf(zipCodeRanges.get(j).getUpperBound())) {
							zipCodeRanges.get(i).setUpperBound(zipCodeRanges.get(j).getUpperBound());
						}
						zipCodeRanges.remove(j);
					} else {
						j++;
					}
				}
			}
		}
		return zipCodeRanges;
	}

	/**
	 * This method used iterate the List of ZipcodeRange and print them.
	 * 
	 * @param zipCodeRanges {@link ZipCodeRange}
	 */
	private void printData(List<ZipCodeRange> zipCodeRanges) {
		if (zipCodeRanges != null) {
			zipCodeRanges.forEach((zipCodeRange) -> System.out
					.println("[" + zipCodeRange.getLowerBound() + "," + zipCodeRange.getUpperBound() + "]"));

		}
	}
}
