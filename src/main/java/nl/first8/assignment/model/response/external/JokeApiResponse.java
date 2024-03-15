package nl.first8.assignment.model.response.external;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JokeApiResponse {
    private boolean error;
    private Long amount;
    private List<Joke> jokes;
}
