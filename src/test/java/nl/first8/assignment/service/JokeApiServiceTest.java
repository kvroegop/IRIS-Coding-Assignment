package nl.first8.assignment.service;

import lombok.SneakyThrows;
import nl.first8.assignment.TestConfig;
import nl.first8.assignment.model.response.external.Joke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfig.class)
class JokeApiServiceTest {

    @Autowired
    private JokeApiService jokeApiService;
    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @SneakyThrows
    @Test
    public void whenRequestingMinimumRequiredContent_thenOneSafeShortJokeIsReturned() {
        URL fileWithMockResponse = getClass().getClassLoader().getResource("sample_jokeapi_response.json");
        String responseMessage = Files.readString(Path.of(fileWithMockResponse.toURI()));
        mockServer.expect(
                        ExpectedCount.once(),
                        requestTo(new URI("https://v2.jokeapi.dev/joke/Any?type=single&amount=16"))
                )
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                        withStatus(HttpStatus.OK)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(responseMessage)
                );

        Joke shortestSafeRandomJoke = jokeApiService.getShortestSafeRandomJoke();
        mockServer.verify();
        assertEquals("Debugging: Removing the needles from the haystack.", shortestSafeRandomJoke.getJoke());
    }



}