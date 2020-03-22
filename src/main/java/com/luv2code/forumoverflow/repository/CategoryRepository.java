package com.luv2code.forumoverflow.repository;

import java.util.Optional;

import com.luv2code.forumoverflow.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lzugaj on Wednesday, February 2020
 */

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

}
