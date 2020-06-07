package com.siammali.dto;

import lombok.Data;

@Data
public class GenericOutput {
	public GenericOutput(boolean result) {
		this.result = result;
	}

	boolean result;
}
