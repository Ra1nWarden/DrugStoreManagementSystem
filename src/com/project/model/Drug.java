package com.project.model;

public class Drug {
	
	public int drugId;
	public String drugName;
	public int providerId;
	public double price;
	public int stock;
	
	@Override
	public String toString() {
		return drugName;
	}

}
