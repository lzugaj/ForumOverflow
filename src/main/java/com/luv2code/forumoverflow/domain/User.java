package com.luv2code.forumoverflow.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "username")
	private String username;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

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
