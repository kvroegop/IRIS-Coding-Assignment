package nl.first8.assignment.model.response.internal;

import lombok.Builder;
import nl.first8.assignment.model.response.external.Joke;

@Builder
public record JokeResponse(Long id, String joke) {

    public static JokeResponse from(Joke joke) {
        return JokeResponse.builder()
                .id(joke.getId())
                .joke(joke.getJoke())
                .build();
    }
}
