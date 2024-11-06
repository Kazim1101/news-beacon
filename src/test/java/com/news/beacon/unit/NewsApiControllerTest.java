package com.news.beacon.unit;

import com.news.beacon.service.NewsApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class NewsApiControllerTest extends RestServerMockTest{

    @MockBean
    private NewsApiService newsApiService;

    private List<Map<String, String>> headlinesResponse;

    @BeforeEach
    public void setUp() {
        headlinesResponse = List.of(
                Map.of(
                        "author", "James Powel, Eric Lagatta",
                        "publishedAt", "2024-11-02T16:03:40Z",
                        "source", "USA Today",
                        "title", "NASA's Perseverance rover captures 'googly-eye' Martian moon passing sun: See video - USA TODAY",
                        "description", "NASA released photos and video of a Martian moon passing between the planet and the sun. The occurrence is common on Mars, lasting around 30 seconds.",
                        "articleUrl", "https://www.usatoday.com/story/news/nation/2024/10/31/nasa-rover-perseverance-mars-moon-video/75975653007/"
                )
        );
    }

    @Test
    void getHeadlinesByCountryCode() throws Exception {

        when(newsApiService.getHeadlinesByCountryCode("us")).thenReturn(headlinesResponse);

        mockMvc.perform(get("/news/headlines")
                .param("country", "us")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value("James Powel, Eric Lagatta"))
                .andExpect(jsonPath("$[0].publishedAt").value("2024-11-02T16:03:40Z"))
                .andExpect(jsonPath("$[0].source").value("USA Today"));


    }

}