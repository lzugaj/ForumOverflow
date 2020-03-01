package com.luv2code.forumoverflow.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Created by lzugaj on Friday, February 2020
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
@ApiModel(value = "Category", description = "Defines Category instance variables")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@ApiModelProperty(value = "Category's id", example = "1")
	private Long id;

	@Column(name = "name")
	@ApiModelProperty(value = "Category's name", example = "Feed")
	private String name;

	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "category")
	private List<Post> posts;

}
