package com.luv2code.forumoverflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by lzugaj on Friday, February 2020
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private Long id;

	private String title;

	private String description;

	private LocalDateTime createdDate;

}
