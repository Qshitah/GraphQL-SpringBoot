package web.crea.book.graphql.exception;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(Long id) {
        super("Author not found with ID: " + id);

    }
}
