package de.andi.interview.restdoc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class GitHubProxyRestDocTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    void testRawGitHubSearch() throws Exception {
        this.mockMvc.perform(get("/api/v1/repositories").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("rawRequest",
                        responseFields(fieldWithPath("[].id").description("Unique ID of repository").type("int"),
                                fieldWithPath("[].full_name").description("Full name of Repository").type("String"),
                                fieldWithPath("[].name").description("Short Hand of name").type("String"),
                                fieldWithPath("[].description").description("Description").optional().type("String"),
                                fieldWithPath("[].html_url").description("URL to Repository on GitHub").type("String"),
                                fieldWithPath("[].git_url").description("Git URL").type("String"),
                                fieldWithPath("[].language").description("Primary used language").optional().type("String"),
                                fieldWithPath("[].topics.[]").description("Topics relating to the repository").type("array"),
                                fieldWithPath("[].stargazers_count").description("Stars").type("int"))));
    }

    @Test
    void testDateGitHubSearch() throws Exception {
        this.mockMvc.perform(get("/api/v1/repositories").queryParam("inceptionDate", LocalDate.of(2022, 04, 01).toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("dateRequest", requestParameters(parameterWithName("inceptionDate").description("The inception date of repositories in a ISO 8601 format."))));
    }

    @Test
    void testDateAndLanguageGitHubSearch() throws Exception {
        this.mockMvc.perform(get("/api/v1/repositories").queryParam("inceptionDate", LocalDate.of(2022, 04, 01).toString())
                        .queryParam("language", "java").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("dateAndLanguageRequest",
                        requestParameters(parameterWithName("inceptionDate").description("The inception date of repositories in a ISO 8601 format."),
                                parameterWithName("language").description("The main language for the repository."))));
    }

    @Test
    void testLimitsGitHubSearch() throws Exception {
        this.mockMvc.perform(get("/api/v1/repositories").queryParam("limit", "50").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("limitRequest", requestParameters(parameterWithName("limit").description("How many results should be returned. This option has allowed values 10, 50 and 100."))));
    }

    @Test
    void testOrderingGitHubSearch() throws Exception {
        this.mockMvc.perform(get("/api/v1/repositories").queryParam("sort", "ASC").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("orderRequest", requestParameters(parameterWithName("sort").description("Sorting results ascending (ASC) or descending (DESC)."))));
    }

    @Test
    void testWrongLimitsGitHubSearch() throws Exception {
        this.mockMvc.perform(get("/api/v1/repositories").queryParam("limit", "200").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(document("badLimitRequest"));
    }
}