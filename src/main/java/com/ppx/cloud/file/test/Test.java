package com.ppx.cloud.file.test;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ppx.cloud.common.jdbc.annotation.Id;
import com.ppx.cloud.common.util.DateUtils;

public class Test {
    @Id
	private Integer testId;
	
	private String testName;
	
	private Float testPrice;
	
	@JsonFormat(pattern=DateUtils.DATE_PATTERN, timezone="GMT+8")
	@DateTimeFormat(pattern=DateUtils.DATE_PATTERN)
	private Date testDate;
	
	private Date created;

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}
	
	public Float getTestPrice() {
		return testPrice;
	}

	public void setTestPrice(Float testPrice) {
		this.testPrice = testPrice;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	
}	
