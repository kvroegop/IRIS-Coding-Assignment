package nl.first8.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No matching jokes found.")
public class JokeNotFoundException extends RuntimeException {
}
