package com.luv2code.forumoverflow.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.luv2code.forumoverflow.domain.ContentStatus;
import com.luv2code.forumoverflow.repository.ContentStatusRepository;
import com.luv2code.forumoverflow.service.impl.ContentStatusServiceImpl;

/**
 * Created by lzugaj on Sunday, March 2020
 */

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ContentStatusServiceImplTest {

    @Mock
    private ContentStatusRepository contentStatusRepository;

    @InjectMocks
    private ContentStatusServiceImpl contentStatusService;

    private ContentStatus firstContentStatus;

    private ContentStatus secondContentStatus;

    private List<ContentStatus> contentStatuses;

    @BeforeEach
    public void setup() {
        firstContentStatus = new ContentStatus();
        firstContentStatus.setId(1L);
        firstContentStatus.setName("VALID");
        firstContentStatus.setPosts(null);

        secondContentStatus = new ContentStatus();
        secondContentStatus.setId(2L);
        secondContentStatus.setName("INVALID");
        secondContentStatus.setPosts(null);

        contentStatuses = new ArrayList<>();
        contentStatuses.add(firstContentStatus);
        contentStatuses.add(secondContentStatus);

        Mockito.when(contentStatusRepository.findById(firstContentStatus.getId())).thenReturn(java.util.Optional.ofNullable(firstContentStatus));
        Mockito.when(contentStatusRepository.findByName(firstContentStatus.getName())).thenReturn(java.util.Optional.ofNullable(firstContentStatus));
        Mockito.when(contentStatusRepository.findAll()).thenReturn(contentStatuses);
    }

    @Test
    public void testFindById() {
        ContentStatus searchedContentStatus = contentStatusService.findById(firstContentStatus.getId());

        assertNotNull(searchedContentStatus);
        assertEquals("1", searchedContentStatus.getId().toString());
        assertEquals("VALID", searchedContentStatus.getName());
    }

    @Test
    public void testFindByIdNullPointerException() {
        when(contentStatusRepository.findById(secondContentStatus.getId())).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> contentStatusService.findById(secondContentStatus.getId()));
    }

    @Test
    public void testFindByName() {
        ContentStatus searchedContentStatus = contentStatusService.findByName(firstContentStatus.getName());

        assertNotNull(searchedContentStatus);
        assertEquals("1", searchedContentStatus.getId().toString());
        assertEquals("VALID", searchedContentStatus.getName());
    }

    @Test
    public void testFindByNameNullPointerException() {
        when(contentStatusRepository.findByName(secondContentStatus.getName())).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> contentStatusService.findByName(secondContentStatus.getName()));
    }

    @Test
    public void testFindAll() {
        List<ContentStatus> searchedContentStatuses = contentStatusService.findAll();

        assertEquals(2, contentStatuses.size());
        assertEquals(2, searchedContentStatuses.size());
        verify(contentStatusRepository, times(1)).findAll();
    }
}
