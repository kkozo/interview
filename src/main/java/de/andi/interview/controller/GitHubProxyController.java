package de.andi.interview.controller;


import de.andi.interview.data.github.OrderEnum;
import de.andi.interview.data.github.RepositoryDto;
import de.andi.interview.service.RepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1")
public class GitHubProxyController {

    private final RepositoryService repositoryService;

    public GitHubProxyController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @GetMapping(value = "search")
    @Valid
    @Operation(summary = "Retrieve Repositories filtered by criteria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Repositories retrieved.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RepositoryDto.class, type = "array"))}),
            @ApiResponse(responseCode = "400", description = "Error retrieving repositories")})
    public List<RepositoryDto> retrieveRepositories(@RequestParam(value = "limit", required = false, defaultValue = "100") @Min(25) @Max(100) int limit,
                                                    @RequestParam(value = "sort" ) Optional<OrderEnum> orderEnum) {
        return repositoryService.searchRepository(orderEnum.map(OrderEnum::getMappedOrder), Optional.empty(), Optional.empty(), limit).items();
    }

}
