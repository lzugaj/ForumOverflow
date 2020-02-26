package com.luv2code.forumoverflow.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@ManyToOne
	@ToString.Exclude
	@JoinColumn(name = "id_user", nullable = false)
	private User user;

	@ManyToOne
	@ToString.Exclude
	@JoinColumn(name = "id_category", nullable = false)
	private Category category;

	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "post")
	private List<Comment> comments;

}
