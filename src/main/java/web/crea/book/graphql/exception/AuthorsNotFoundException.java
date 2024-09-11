package web.crea.book.graphql.exception;

import java.util.List;

public class AuthorsNotFoundException extends RuntimeException {
    public AuthorsNotFoundException(List<Long> authorIds) {
        super("No authors found for IDs: " + authorIds);
    }
}