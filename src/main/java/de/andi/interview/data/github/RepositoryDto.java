package de.andi.interview.data.github;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RepositoryDto(long id, String name, @JsonProperty("full_name") String fullName,
                                  @JsonProperty("html_url") String htmlUrl,
                                  @JsonProperty("git_url") String gitUrl,
                                  String description,
                                  @JsonProperty("stargazers_count") long stars,
                                  String language,
                                  List<String> topics) {
}
