package com.luv2code.forumoverflow.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "category")
@ApiModel(value = "Category", description = "Defines Category instance variables")
public class Category {

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="category_seq")
	@SequenceGenerator(name = "category_seq", sequenceName = "category_seq_generator", allocationSize = 1)
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
