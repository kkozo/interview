package de.andi.interview.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = GitHubQueryBuilder.class)
class UriBuilderTest {

    @Autowired
    private GitHubQueryBuilder gitHubQueryBuilder;



    @Test
    void testSimpleDefaultDateQuery() {
        // act
        String targetUrl = gitHubQueryBuilder.buildUrl(LocalDate.EPOCH, Optional.empty(), Optional.empty(), 100);
        // test
        assertThat(targetUrl).isEqualTo("/search/repositories?q=created:>1970-01-01&sort=stars&per_page=100&page=1");
    }

    @Test
    void testSimpleOrderedDateQuery() {
        // act
        String targetUrl = gitHubQueryBuilder.buildUrl(LocalDate.EPOCH, Optional.of("desc"), Optional.empty(), 100);
        // test
        assertThat(targetUrl).isEqualTo("/search/repositories?q=created:>1970-01-01&sort=stars&order=desc&per_page=100&page=1");
    }

    @Test
    void testSimpleDateQuery() {
        // act
        String targetUrl = gitHubQueryBuilder.buildUrl(LocalDate.of(2022,04,06), Optional.of("desc"), Optional.empty(), 100);
        // test
        assertThat(targetUrl).isEqualTo("/search/repositories?q=created:>2022-04-06&sort=stars&order=desc&per_page=100&page=1");
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 50, 100})
    void testSimpleLimitQuery(int limit) {
        // act
        String targetUrl = gitHubQueryBuilder.buildUrl(LocalDate.EPOCH, Optional.empty(), Optional.empty(), limit);
        // test
        assertThat(targetUrl).isEqualTo("/search/repositories?q=created:>1970-01-01&sort=stars&per_page=%s&page=1".formatted(limit));
    }


    @ParameterizedTest
    @ValueSource(strings = {"c", "kotlin", "java"})
    void testSimpleLanguageQuery(String limit) {
        // act
        String targetUrl = gitHubQueryBuilder.buildUrl(LocalDate.EPOCH, Optional.empty(), Optional.of(limit), 100);
        // test
        assertThat(targetUrl).isEqualTo("/search/repositories?q=created:>1970-01-01+language:%s&sort=stars&per_page=100&page=1".formatted(limit));
    }
}
