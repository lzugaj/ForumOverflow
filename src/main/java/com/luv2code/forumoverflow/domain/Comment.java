package com.luv2code.forumoverflow.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by lzugaj on Friday, February 2020
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "description")
	private String description;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@JsonIgnore
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "id_status", nullable = false)
	private ContentStatus contentStatus;

	@JsonIgnore
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false)
	private User user;

	@JsonIgnore
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "id_post", nullable = false)
	private Post post;

}
