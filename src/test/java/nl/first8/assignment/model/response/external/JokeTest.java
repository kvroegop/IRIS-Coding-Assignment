package nl.first8.assignment.model.response.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JokeTest {

    @SneakyThrows
    @Test
    public void testValidJokeFlagsAreCorrectlyParsed() {
        Joke joke = new ObjectMapper()
                .readerFor(Joke.class)
                .readValue(getClass().getClassLoader().getResourceAsStream("single_valid_joke.json"));

        assertTrue(joke.nsfw);
        assertFalse(joke.religious);
        assertTrue(joke.political);
        assertFalse(joke.racist);
        assertTrue(joke.sexist);
        assertFalse(joke.explicit);
    }

    @Test
    public void testJokeIsSafeAccordingToExactSpecificationMatch() {
        Joke joke = Joke.builder()
                .safe(true)
                .sexist(false)
                .explicit(false)
                .build();

        assertTrue(joke.isSafeAccordingToSpecifications());
    }

    @Test
    public void testJokeIsSafeWithAdditionalFieldsSetToTrue() {
        Joke joke = Joke.builder()
                .safe(true)
                .sexist(false)
                .explicit(false)
                .political(true)
                .religious(true)
                .build();

        assertTrue(joke.isSafeAccordingToSpecifications());
    }

    @Test
    public void testJokeIsUnsafeDueToSafeParam() {
        Joke joke = Joke.builder()
                .safe(false)
                .sexist(false)
                .explicit(false)
                .build();

        assertFalse(joke.isSafeAccordingToSpecifications());
    }

    @Test
    public void testJokeIsUnsafeDueToSexistParam() {
        Joke joke = Joke.builder()
                .safe(true)
                .sexist(true)
                .explicit(false)
                .build();

        assertFalse(joke.isSafeAccordingToSpecifications());
    }

    @Test
    public void testJokeIsUnsafeDueToExplicitParam() {
        Joke joke = Joke.builder()
                .safe(true)
                .sexist(false)
                .explicit(true)
                .build();

        assertFalse(joke.isSafeAccordingToSpecifications());
    }

    @Test
    public void testJokeIsUnsafeDueToAllMatching() {
        Joke joke = Joke.builder()
                .safe(false)
                .sexist(true)
                .explicit(true)
                .build();

        assertFalse(joke.isSafeAccordingToSpecifications());
    }
}