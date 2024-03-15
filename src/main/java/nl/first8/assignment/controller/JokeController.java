package nl.first8.assignment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.first8.assignment.exception.JokeNotFoundException;
import nl.first8.assignment.model.response.external.Joke;
import nl.first8.assignment.model.response.internal.JokeResponse;
import nl.first8.assignment.service.JokeApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/joke")
@RequiredArgsConstructor
@Slf4j
public class JokeController {
    private final JokeApiService jokeApiService;

    @GetMapping
    public JokeResponse getShortestRandomSafeJoke() {
        log.info("Received a request to fetch and filter jokes");
        Optional<Joke> matchingJoke = jokeApiService.getShortestSafJokeWithUrlFilter();

        if (matchingJoke.isEmpty()) {
            log.info("No jokes found that are considered safe to display. ");
            throw new JokeNotFoundException();
        }

        return JokeResponse.from(matchingJoke.get());
    }

}
