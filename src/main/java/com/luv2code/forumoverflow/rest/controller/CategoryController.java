package com.luv2code.forumoverflow.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.exception.NameAlreadyExistsException;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.rest.message.MessageHandler;
import com.luv2code.forumoverflow.service.CategoryService;
import com.luv2code.forumoverflow.util.DomainConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Thursday, March 2020
 */

@Slf4j
@Api(value = "Category Controller")
@RequestMapping(
        path = "/category",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "Create")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created category"),
            @ApiResponse(code = 401, message = "You are not authorized to create the category"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to create is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to create is not found")
    })
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> save(@RequestBody final Category category) {
        final Optional<Category> searchedCategory = Optional.ofNullable(categoryService.findByName(category.getName()));
        if (!searchedCategory.isPresent()) {
            final Category newCategory = categoryService.save(category);
            log.info("Successfully created Category with id: `{}`.", category.getId());
            return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
        } else {
            log.info("Category name `{}` already exists. ", category.getName());
            throw new NameAlreadyExistsException(MessageHandler.entityNameAlreadyExists(DomainConstants.CATEGORY, category.getName()));
        }
    }

    @ApiOperation(value = "Find by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully founded category"),
            @ApiResponse(code = 401, message = "You are not authorized to find the category"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to find is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to find is not found")
    })
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> findById(@PathVariable final Long id) {
        final Optional<Category> searchedCategory = Optional.ofNullable(categoryService.findById(id));
        if (searchedCategory.isPresent()) {
            log.info("Successfully founded Category with id: `{}`.", id);
            return new ResponseEntity<>(searchedCategory.get(), HttpStatus.OK);
        } else {
            log.info("Category with id `{}` wasn't founded.", id);
            throw new EntityNotFoundException(MessageHandler.entityNotFound(DomainConstants.CATEGORY, id));
        }
    }

    @ApiOperation(value = "Find all")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully founded all categories"),
            @ApiResponse(code = 401, message = "You are not authorized to find all the categories"),
            @ApiResponse(code = 403, message = "Accessing the resources you were trying to find is forbidden"),
            @ApiResponse(code = 404, message = "The resources you were trying to find is not found")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> findAll() {
        final List<Category> searchedCategories = categoryService.findAll();
        log.info("Successfully founded all Categories.");
        return new ResponseEntity<>(searchedCategories, HttpStatus.OK);
    }

    @ApiOperation(value = "Update")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated category"),
            @ApiResponse(code = 401, message = "You are not authorized to update the category"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to update is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to update is not found")
    })
    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> update(@PathVariable final Long id, @RequestBody Category category) {
        final Optional<Category> searchedCategory = Optional.ofNullable(categoryService.findById(id));
        if (searchedCategory.isPresent()) {
            if (categoryService.isNameAlreadyUsed(category.getName())) {
                log.info("Category name `{}` already exists. ", category.getName());
                throw new NameAlreadyExistsException(MessageHandler.entityNameAlreadyExists(DomainConstants.CATEGORY, category.getName()));
            } else {
                final Category updatedCategory = categoryService.update(searchedCategory.get(), category);
                log.info("Successfully updated Category with id: `{}`.", id);
                return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
            }
        } else {
            log.info("Category with id `{}` wasn't founded.", id);
            throw new EntityNotFoundException(MessageHandler.entityNotFound(DomainConstants.CATEGORY, id));
        }
    }

    @ApiOperation(value = "Delete")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted category"),
            @ApiResponse(code = 401, message = "You are not authorized to delete the category"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to delete is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to delete is not found")
    })
    @DeleteMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> delete(@PathVariable final Long id) {
        final Optional<Category> searchedCategory = Optional.ofNullable(categoryService.findById(id));
        if (searchedCategory.isPresent()) {
            categoryService.delete(searchedCategory.get());
            log.info("Successfully deleted Category with id: `{}`.", id);
            return ResponseEntity.noContent().build();
        } else {
            log.info("Category with id `{}` wasn't founded.", id);
            throw new EntityNotFoundException(MessageHandler.entityNotFound(DomainConstants.CATEGORY, id));
        }
    }
}
