package com.luv2code.forumoverflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.forumoverflow.domain.ContentStatus;
import com.luv2code.forumoverflow.repository.ContentStatusRepository;
import com.luv2code.forumoverflow.service.ContentStatusService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Wednesday, March 2020
 */

@Slf4j
@Service
public class ContentStatusServiceImpl implements ContentStatusService {

    private final ContentStatusRepository contentStatusRepository;

    @Autowired
    public ContentStatusServiceImpl(final ContentStatusRepository contentStatusRepository) {
        this.contentStatusRepository = contentStatusRepository;
    }

    @Override
    public ContentStatus findById(final Long id) {
        final ContentStatus contentStatus = contentStatusRepository.findById(id).orElse(null);
        log.info("Searching ContentStatus with id: `{}`", id);
        return contentStatus;
    }

    @Override
    public ContentStatus findByName(final String name) {
        final ContentStatus contentStatus = contentStatusRepository.findByName(name).orElse(null);
        log.info("Searching ContentStatus with name: `{}`", name);
        return contentStatus;
    }

    @Override
    public List<ContentStatus> findAll() {
        final List<ContentStatus> contentStatuses = contentStatusRepository.findAll();
        log.info("Searching all ContentStatuses.");
        return contentStatuses;
    }
}
