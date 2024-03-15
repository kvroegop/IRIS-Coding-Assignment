package nl.first8.assignment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.first8.assignment.model.response.external.Joke;
import nl.first8.assignment.model.response.external.JokeApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static nl.first8.assignment.model.response.external.Joke.jokeLengthComparator;

@Service
@RequiredArgsConstructor
@Slf4j
public class JokeApiService {

    @Value("${external.jokeapi.base-url}")
    private String baseJokeapiUrl;

    private final RestTemplate restTemplate;

    private static final String defaultJokeFilter = "Any?type=single&amount=10";

    public Optional<Joke> getShortestSafeRandomJoke() {
        log.info("Fetching jokes from external service and applying own logic on filtering. Returning shorts matching joke.");
        return getRandomJokes().getJokes()
                .stream()
                .filter(Joke::isSafeAccordingToSpecifications)
                .min(jokeLengthComparator());
    }


    public Optional<Joke> getShortestSafJokeWithUrlFilter() {
        log.info("Fetching jokes that are filtered on external service and returning only the shortest one");
        return getJokesWithCustomFilter("Any?type=single&amount=10&blacklistFlags=explicit,sexist").getJokes()
                .stream()
                .filter(Joke::isSafe)
                .min(jokeLengthComparator());
    }

    private JokeApiResponse getRandomJokes() {
        log.info("Calling external service to retrieve jokes");
        return getJokesWithCustomFilter(defaultJokeFilter);
    }

    private JokeApiResponse getJokesWithCustomFilter(String filter) {
        log.info("Calling external service to retrieve jokes with filter settings: {}", filter);
        return restTemplate.getForObject(baseJokeapiUrl + filter, JokeApiResponse.class);
    }

}
