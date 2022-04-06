package de.andi.interview.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = GitHubQueryBuilder.class)
public class UriBuilderTest {

    @Autowired
    private GitHubQueryBuilder gitHubQueryBuilder;

    @Test
    public void testSimpleStarQuery() {
        String targetUrl = gitHubQueryBuilder.buildUrl(Optional.empty(), Optional.empty(), Optional.empty(), 100);
        assertThat(targetUrl).isEqualTo("/search/repositories?q=sort=stars&per_page=100&page=1");
    }

    @Test
    public void testSimpleOrderedStarQuery() {
        String targetUrl = gitHubQueryBuilder.buildUrl(Optional.of("asc"), Optional.empty(), Optional.empty(), 100);
        assertThat(targetUrl).isEqualTo("/search/repositories?q=sort=stars&order=asc&per_page=100&page=1");
    }

}
