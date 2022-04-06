package de.andi.interview.data.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GitHubRepositoryResponseDto(@JsonProperty("total_count") long totalCount, List<RepositoryDto> items) {
}
