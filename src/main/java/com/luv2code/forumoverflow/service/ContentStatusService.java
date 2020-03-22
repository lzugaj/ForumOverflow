package com.luv2code.forumoverflow.service;

import java.util.List;

import com.luv2code.forumoverflow.domain.ContentStatus;

/**
 * Created by lzugaj on Wednesday, March 2020
 */

public interface ContentStatusService {

    ContentStatus findById(Long id);

    ContentStatus findByName(String name);

    List<ContentStatus> findAll();

}
