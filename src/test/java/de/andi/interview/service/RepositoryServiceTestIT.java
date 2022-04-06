package de.andi.interview.service;

import de.andi.interview.data.github.GitHubRepositoryResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("integration")
public class RepositoryServiceTestIT {

    @Autowired
    private RepositoryService repositoryService;

    @ParameterizedTest
    @ValueSource(ints = {25, 50, 100})
    public void testSimpleLimitedQuery(int limit) {
        // act
        GitHubRepositoryResponseDto test = repositoryService.searchRepository(Optional.of("desc"), Optional.empty(), Optional.empty(), limit);
        // test
        assertThat(test).isNotNull();
        assertThat(test.items()).hasSize(limit);
    }

}
