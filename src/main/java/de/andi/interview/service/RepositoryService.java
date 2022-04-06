package de.andi.interview.service;

import de.andi.interview.data.github.GitHubRepositoryResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RepositoryService {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryService.class);
    private final RestTemplate githubRestTemplate;
    private final GitHubQueryBuilder gitHubQueryBuilder;

    public RepositoryService(RestTemplate githubRestTemplate, GitHubQueryBuilder gitHubQueryBuilder) {
        this.githubRestTemplate = githubRestTemplate;
        this.gitHubQueryBuilder = gitHubQueryBuilder;
    }

    public GitHubRepositoryResponseDto searchRepository(Optional<String> order, Optional<LocalDate> optLocalDate, Optional<String> language, int limit) {
        String targetUrl = gitHubQueryBuilder.buildUrl(order, optLocalDate, language, limit);
        GitHubRepositoryResponseDto githubResponse = this.githubRestTemplate.getForObject(targetUrl, GitHubRepositoryResponseDto.class);

        logger.debug("Querying:{}. Response from query: {}", targetUrl, githubResponse);
        return githubResponse;
    }
}
