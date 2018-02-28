package com.raj.zipcode.zipcodeparser.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.zipcode.zipcodeparser.domain.ZipCodeRange;
import com.raj.zipcode.zipcodeparser.domain.ZipCodeRangeRequest;

@RestController
@RequestMapping("/rest/zipcode")
public class ZipCodeParserController {

	@PostMapping(value = "/validate", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ZipCodeRangeRequest create(@RequestBody final ZipCodeRangeRequest zipCodeRangeRequest) {
		List<ZipCodeRange> zipCodeRanges = zipCodeRangeRequest.getZipCodeRanges();
		removeOverLap(zipCodeRanges);
		zipCodeRangeRequest.setZipCodeRanges(zipCodeRanges);
		return zipCodeRangeRequest;
	}

	/**
	 * This method will help to remove the overlapping Zipcode
	 * 
	 * @param zipCodeRanges
	 * @return List<ZipCodeRange>
	 */
	public List<ZipCodeRange> removeOverLap(List<ZipCodeRange> zipCodeRanges) {
		if (zipCodeRanges != null) {
			//Sorting the Zipcode based on lowerBound value.
			zipCodeRanges.sort((ZipCodeRange z1, ZipCodeRange z2) -> z1.getLowerBound().compareTo(z2.getLowerBound()));
			for (int i = 0; i < zipCodeRanges.size(); i++) {
				for (int j = 1; j < zipCodeRanges.size();) {
					if (i != j && zipCodeRanges.get(j).getLowerBound() <= zipCodeRanges.get(i).getUpperBound()
							&& zipCodeRanges.get(j).getLowerBound() >= zipCodeRanges.get(i).getLowerBound()) {
						if (zipCodeRanges.get(j).getLowerBound() <= zipCodeRanges.get(i).getLowerBound()) {
							zipCodeRanges.get(i).setLowerBound(zipCodeRanges.get(j).getLowerBound());
						}
						if (zipCodeRanges.get(i).getUpperBound() <= zipCodeRanges.get(j).getUpperBound()) {
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
	 * @param zipCodeRanges
	 */
	public void printData(List<ZipCodeRange> zipCodeRanges) {
		if (zipCodeRanges != null) {
			zipCodeRanges.forEach((zipCodeRange) -> System.out
					.println("[" + zipCodeRange.getLowerBound() + "," + zipCodeRange.getUpperBound() + "]"));

		}
	}
}
