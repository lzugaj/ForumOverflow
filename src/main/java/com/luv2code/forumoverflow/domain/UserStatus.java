package com.luv2code.forumoverflow.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * Created by lzugaj on Friday, March 2020
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_status")
@ApiModel(value = "User status", description = "Defines User status instance variables")
public class UserStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@ApiModelProperty(value = "User status id", example = "1")
	private Long id;

	@Column(name = "name")
	@ApiModelProperty(value = "User status name", example = "ACTIVE")
	private String name;

	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "userStatus")
	private List<User> user;
}
