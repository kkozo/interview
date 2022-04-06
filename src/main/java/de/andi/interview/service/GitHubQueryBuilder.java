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

    public String buildUrl(LocalDate inceptionDate, Optional<String> order, Optional<String> language, int limit) {
        String targetUrl = UriComponentsBuilder.fromUriString("/search/repositories?q=" + createInitialQuery(inceptionDate, language))
                .queryParam("sort", "stars") // default to sorting by stars
                .queryParamIfPresent("order", order) // sorting asc/desc
                .queryParam("per_page", limit) // limit
                .queryParam("page", 1) // default first page
                .build().toUriString(); // forgot build last time :/
        logger.debug("Path created {}", targetUrl);
        return targetUrl;
    }

    /**
     * Build the initial query string for date and language.
     * @param inceptionDate
     * @param language
     * @return
     */
    private String createInitialQuery(LocalDate inceptionDate, Optional<String> language) {
        return language.map(lang -> "created:>%s+language:%s".formatted(inceptionDate, lang))
                .orElse("created:>%s".formatted(inceptionDate));


    }

}
