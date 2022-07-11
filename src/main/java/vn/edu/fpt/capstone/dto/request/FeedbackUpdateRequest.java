package vn.edu.fpt.capstone.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FeedbackUpdateRequest {
	private Long id;
	private String content;
	private float rating;
}