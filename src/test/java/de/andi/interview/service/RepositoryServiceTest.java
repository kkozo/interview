package de.andi.interview.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RepositoryServiceTest {

    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void testSimpleQuery() {
           assertThat(repositoryService.searchRepository(Optional.of("desc"), Optional.empty(),Optional.empty(), 100 )).isNotNull();
    }

}
