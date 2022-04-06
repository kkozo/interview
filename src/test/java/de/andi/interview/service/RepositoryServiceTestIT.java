package de.andi.interview.service;

import de.andi.interview.data.github.GitHubRepositoryResponseDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("integration")
class RepositoryServiceTestIT {

    @Autowired
    private GitHubRepositoryService repositoryService;

    @ParameterizedTest(name = "Test returns of GitHub")
    @ValueSource(ints = {10, 50, 100})
    void testSimpleLimitedDateQuery(int limit) {
        // act
        GitHubRepositoryResponseDto test = repositoryService.searchRepository(LocalDate.EPOCH, Optional.empty(), Optional.of("desc"), limit);
        // test
        assertThat(test).isNotNull();
        assertThat(test.items()).hasSize(limit);
    }

}
