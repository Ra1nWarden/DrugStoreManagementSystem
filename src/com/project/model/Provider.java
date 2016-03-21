package com.project.model;

public class Provider {

	public int providerId;
	public String providerName;
	public String providerAddress;

	@Override
	public String toString() {
		return providerName;
	}

}
