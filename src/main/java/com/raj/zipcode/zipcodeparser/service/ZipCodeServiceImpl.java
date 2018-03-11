package com.raj.zipcode.zipcodeparser.service;

import com.raj.zipcode.zipcodeparser.domain.ZipCodeRange;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ZipCodeServiceImpl implements ZipCodeService {

  /**
   * This method will help to remove the overlapping Zipcode ranges.
   * 
   * @param zipCodeRanges
   *          Zipcode Ranges
   * @return {@link List} of {@link ZipCodeRange}
   */
  public List<ZipCodeRange> removeOverLap(final List<ZipCodeRange> zipCodeRanges) {
    if (zipCodeRanges != null) {
      // Sorting the Zipcode based on lowerBound value.
      zipCodeRanges.sort((ZipCodeRange z1, ZipCodeRange z2) -> Integer.valueOf(z1.getLowerBound())
          .compareTo(Integer.valueOf(z2.getLowerBound())));
      int i = 0;
      while (i < zipCodeRanges.size() - 1) {
        Integer currentLowerBound = Integer.valueOf(zipCodeRanges.get(i).getLowerBound());
        Integer currentUpperBound = Integer.valueOf(zipCodeRanges.get(i).getUpperBound());

        int j = i + 1;

        Integer nextLowerBound = Integer.valueOf(zipCodeRanges.get(j).getLowerBound());
        if (nextLowerBound <= currentUpperBound && nextLowerBound >= currentLowerBound) {
          if (nextLowerBound <= currentLowerBound) {
            zipCodeRanges.get(i).setLowerBound(zipCodeRanges.get(j).getLowerBound());
          }
          Integer nextUpperBound = Integer.valueOf(zipCodeRanges.get(j).getUpperBound());
          if (currentUpperBound <= nextUpperBound) {
            zipCodeRanges.get(i).setUpperBound(zipCodeRanges.get(j).getUpperBound());
          }
          zipCodeRanges.remove(j);
        } else {
          i++;
        }
      }
    }
    return zipCodeRanges;
  }
}
