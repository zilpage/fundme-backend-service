package com.skillink.fundme.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dashboard implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<DashboardItem> items;

	public List<DashboardItem> getItems() {
		return items;
	}

	public void setItems(List<DashboardItem> items) {
		this.items = items;
	}
	
	
	
}
