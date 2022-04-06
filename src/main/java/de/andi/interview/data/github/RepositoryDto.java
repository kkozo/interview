package de.andi.interview.data.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RepositoryDto(long id, String name, @JsonProperty("full_name") String fullName) {
}
