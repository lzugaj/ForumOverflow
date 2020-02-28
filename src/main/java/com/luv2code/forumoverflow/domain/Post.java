package com.luv2code.forumoverflow.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

	@ManyToOne
	@ToString.Exclude
	@JoinColumn(name = "id_user", nullable = false)
	@ApiModelProperty(value = "Post's user")
	private User user;

	@ManyToOne
	@ToString.Exclude
	@JoinColumn(name = "id_category", nullable = false)
	@ApiModelProperty(value = "Post's category")
	private Category category;

	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "post")
	private List<Comment> comments;

}
