package com.luv2code.forumoverflow.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@Table(name = "post")
@ApiModel(value = "Post", description = "Defines Post instance variables")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@ApiModelProperty(value = "Post's id", example = "1")
	private Long id;

	@Column(name = "title")
	@ApiModelProperty(value = "Post's title", example = "Java or C#?")
	private String title;

	@Column(name = "description")
	@ApiModelProperty(value = "Post's description", example = "Is better to learn Java or C# for first programming language?")
	private String description;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@JsonIgnore
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "id_status", nullable = false)
	private ContentStatus contentStatus;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "id_user", nullable = false)
	@ApiModelProperty(value = "Post's user")
	private User user;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "id_category", nullable = false)
	@ApiModelProperty(value = "Post's category")
	private Category category;

	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "post")
	private List<Comment> comments;
}
