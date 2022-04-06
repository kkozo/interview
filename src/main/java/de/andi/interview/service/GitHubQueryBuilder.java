package de.andi.interview.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Optional;

/**
 * For Testing purposes used as a component instead of integrating it into the repository service
 */
@Component
public class GitHubQueryBuilder {

    private static final Logger logger = LoggerFactory.getLogger(GitHubQueryBuilder.class);

    public GitHubQueryBuilder() {

    }

    public String buildUrl(Optional<String> order, Optional<LocalDate> optLocalDate, Optional<String> language, int limit) {
        String targetUrl = UriComponentsBuilder.fromUriString("/search/repositories?q=")
                .queryParam("sort", "stars") // default to sorting by stars
                .queryParamIfPresent("order", order) // sorting asc/desc
                .queryParam("per_page", limit) // limit
                .queryParam("page", 1) // default first page
                .toUriString().replaceAll("=&", "=");
        // unfortunately nasty hack to remove the additional =& the compoonents builder places
        logger.debug("Path created {}", targetUrl);
        return targetUrl;
    }

}
