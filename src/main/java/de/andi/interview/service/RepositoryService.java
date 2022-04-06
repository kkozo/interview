package de.andi.interview.service;

import de.andi.interview.data.github.GitHubRepositoryResponseDto;
import de.andi.interview.data.github.RepositoryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RepositoryService {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryService.class);
    private final RestTemplate githubRestTemplate;

    public RepositoryService(RestTemplate githubRestTemplate) {
        this.githubRestTemplate = githubRestTemplate;
    }

    public GitHubRepositoryResponseDto searchRepository(Optional<String> order, Optional<LocalDate> optLocalDate, Optional<String> language, int limit) {
        String targetUrl = UriComponentsBuilder.fromPath("/")
                .queryParam("sort", "stars")
                .queryParamIfPresent("order", order)
                .queryParam("per_page", limit)
                .encode().toUriString();

        GitHubRepositoryResponseDto githubResponse = this.githubRestTemplate.getForObject(targetUrl, GitHubRepositoryResponseDto.class);

        logger.debug("Retrieved response from query: {}", githubResponse);
        return githubResponse;
    }
}
