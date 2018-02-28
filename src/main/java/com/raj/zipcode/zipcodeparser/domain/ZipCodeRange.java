package com.raj.zipcode.zipcodeparser.domain;

import java.io.Serializable;

public class ZipCodeRange implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3052596279402558481L;

	Integer lowerBound;

	Integer upperBound;

	public Integer getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(Integer lowerBound) {
		this.lowerBound = lowerBound;
	}

	public Integer getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(Integer upperBound) {
		this.upperBound = upperBound;
	}

}
