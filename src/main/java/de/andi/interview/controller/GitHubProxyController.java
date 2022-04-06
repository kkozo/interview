package de.andi.interview.controller;


import de.andi.interview.data.github.OrderEnum;
import de.andi.interview.data.github.RepositoryDto;
import de.andi.interview.service.GitHubRepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController("")
@RequestMapping("api/v1")
@Tag(name="GitHub Proxy Controller")
public class GitHubProxyController {

    private final GitHubRepositoryService repositoryService;

    public GitHubProxyController(GitHubRepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping(name = "Search Repositories", value = "/repositories")
    @Operation(description = """
                Retrieve Repositories filtered by criteria. \n
                If no inceptionDate is provided, the query will instead use 1970-01-01 as a default. \n
                Languages are optional. \n
                A search can be limited to 10, 50 or a 100 results. \n
                A search can be ordered with the appropriate sort key ASC or DESC. \n
                """)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repositories retrieved.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RepositoryDto.class, type = "array"))}),
            @ApiResponse(responseCode = "400", description = "Error retrieving repositories")})
    public ResponseEntity<List<RepositoryDto>> retrieveRepositories(@RequestParam(required = false, name = "inceptionDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> inceptionDate,
                                                                   @RequestParam(value = "limit", required = false, defaultValue = "100") int limit,
                                                                   @RequestParam(value = "language", required = false) Optional<String> language,
                                                                   @RequestParam(value = "sort", required = false) Optional<OrderEnum> orderEnum) {
        if (limit != 10 && limit != 50 && limit != 100) { // probably better with validated and exception handler -> quick & dirty
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(repositoryService.searchRepository(inceptionDate.orElse(LocalDate.EPOCH), language, orderEnum.map(OrderEnum::getMappedOrder), limit).items());
    }

}
