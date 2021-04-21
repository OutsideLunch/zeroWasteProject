package com.bitc.zero.dto;

import lombok.Data;

@Data
public class ProposalDto {
	private int partnerPk;
	private String partnerName;
	private String partnersLink;
	private String productName;
	private String replaceProduct;
	private String productMaterial;
	private String packagingMaterial;
}
