package com.luv2code.forumoverflow.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.luv2code.forumoverflow.domain.ContentStatus;
import com.luv2code.forumoverflow.repository.ContentStatusRepository;

/**
 * Created by lzugaj on Sunday, March 2020
 */

@SpringBootTest
public class ContentStatusServiceImplTest {

    @Mock
    private ContentStatusRepository contentStatusRepository;

    @InjectMocks
    private ContentStatusService contentStatusService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        ContentStatus contentStatus = new ContentStatus(id, "VALID", null);

        when(contentStatusRepository.findById(contentStatus.getId())).thenReturn(java.util.Optional.of(contentStatus));

        ContentStatus searchedContentStatus = contentStatusService.findById(contentStatus.getId());

        assertNotNull(searchedContentStatus);
        assertEquals("1", searchedContentStatus.getId().toString());
        assertEquals("VALID", searchedContentStatus.getName());
    }

    @Test
    public void testFindByIdNullPointerException() {
        Long id = 1L;
        ContentStatus contentStatus = new ContentStatus(id, "VALID", null);

        when(contentStatusRepository.findById(contentStatus.getId())).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> contentStatusService.findById(contentStatus.getId()));
    }

    @Test
    public void testFindByName() {
        Long id = 1L;
        ContentStatus contentStatus = new ContentStatus(id, "VALID", null);

        when(contentStatusRepository.findByName(contentStatus.getName())).thenReturn(java.util.Optional.of(contentStatus));

        ContentStatus searchedContentStatus = contentStatusService.findByName(contentStatus.getName());

        assertNotNull(searchedContentStatus);
        assertEquals("1", searchedContentStatus.getId().toString());
        assertEquals("VALID", searchedContentStatus.getName());
    }

    @Test
    public void testFindByNameNullPointerException() {
        Long id = 1L;
        ContentStatus contentStatus = new ContentStatus(id, "VALID", null);

        when(contentStatusRepository.findByName(contentStatus.getName())).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> contentStatusService.findByName(contentStatus.getName()));
    }

    @Test
    public void testFindAll() {
        Long firstContentStatusId = 1L;
        ContentStatus firstContentStatus = new ContentStatus(firstContentStatusId, "VALID", null);

        Long secondContentStatusId = 1L;
        ContentStatus secondContentStatus = new ContentStatus(secondContentStatusId, "INVALID", null);

        List<ContentStatus> contentStatuses = new ArrayList<>();
        contentStatuses.add(firstContentStatus);
        contentStatuses.add(secondContentStatus);

        when(contentStatusRepository.findAll()).thenReturn(contentStatuses);

        List<ContentStatus> searchedContentStatuses = contentStatusService.findAll();

        assertEquals(2, contentStatuses.size());
        assertEquals(2, searchedContentStatuses.size());
        verify(contentStatusRepository, times(1)).findAll();
    }
}
