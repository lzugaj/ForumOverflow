package com.luv2code.forumoverflow.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@ApiModel(value = "User", description = "Defines User instance variables")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@ApiModelProperty(value = "User's id", example = "1")
	private Long id;

	@Column(name = "first_name")
	@ApiModelProperty(value = "User's first name", example = "Luka")
	private String firstName;

	@Column(name = "last_name")
	@ApiModelProperty(value = "User's last name", example = "Žugaj")
	private String lastName;

	@Column(name = "username")
	@ApiModelProperty(value = "User's username", example = "lzugaj")
	private String username;

	@Column(name = "email")
	@ApiModelProperty(value = "User's email", example = "luka.zugaj@gmail.com")
	private String email;

	@Column(name = "password")
	@ApiModelProperty(value = "User's password", example = "luka123")
	private String password;

	@JsonIgnore
	@Column(name = "blocker_counter")
	@ApiModelProperty(value = "User's blocker counter", example = "2")
	private int blockerCounter;

	@JsonIgnore
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "id_status", nullable = false)
	private UserStatus userStatus;

	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "user")
	private List<Post> posts;

	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "user")
	private List<Comment> comments;

	@JsonIgnore
	@ToString.Exclude
	@ManyToMany
	@JoinTable(name = "user_role",
			joinColumns = @JoinColumn(name = "id_user", nullable = false),
			inverseJoinColumns = @JoinColumn(name = "id_role", nullable = false))
	private List<Role> roles;
}
