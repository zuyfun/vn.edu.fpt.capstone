package vn.edu.fpt.capstone.dto;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class TransactionDto extends Auditable<String> {
	@JsonProperty(index = 0)
	private Long id;
	@JsonProperty(index = 1)
	private String code;
	@JsonProperty(index = 2)
	private float amount;
	@JsonProperty(index = 3)
	private float actualAmount;
	@JsonProperty(index = 4)
	private float lastBalance;
	@JsonProperty(index = 5)
	private String status;
	@JsonProperty(index = 6)
	private String action;
	@JsonProperty(index = 7)
	private String transferContent;
	@JsonProperty(index = 8)
	private String transferType;
	@JsonProperty(index = 9)
	@JsonIgnoreProperties({ "email", "username", "imageLink", "role", "delete", "active", "dob", "gender",
			"phoneNumber", "lastName", "firstName", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate",
			"verify" })
	private UserDto user;
	
	@JsonProperty(index = 10)
	private String note;
}