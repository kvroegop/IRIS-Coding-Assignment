package nl.first8.assignment.model.response.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Joke {
    Long id;
    String category;
    String type;
    String joke;
    String lang;
    boolean safe;
    boolean nsfw;
    boolean religious;
    boolean political;
    boolean racist;
    boolean sexist;
    boolean explicit;

    public boolean isSafeAccordingToSpecifications() {
        return safe && !explicit && !sexist;
    }

    //Adding just a class to hold this metadata felt unneeded, but did not want to discard any response data - this way
    //we flatten/unpack the nested boolean into this single joke object
    @JsonProperty("flags")
    private void unpackFlags(Map<String, Object> flags) {
        nsfw = castToBoolean(flags.get("nsfw"));
        religious = castToBoolean(flags.get("religious"));
        political = castToBoolean(flags.get("political"));
        racist = castToBoolean(flags.get("racist"));
        sexist = castToBoolean(flags.get("sexist"));
        explicit = castToBoolean(flags.get("explicit"));
    }

    /**
     * Parse a given Object to a boolean. Can only return true if the type is either String or Boolean. All other
     * types will be returned as false.
     */
    private boolean castToBoolean(Object object) {
        return switch (object) {
            case Boolean b -> b;
            case String s -> Boolean.parseBoolean(s);
            default -> false;
        };
    }

    public static Comparator<Joke> jokeLengthComparator() {
        return (s2, s1) -> s1.getJoke().length() > s2.getJoke().length() ? -1 : 1;
    }
}
