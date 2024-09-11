package web.crea.book.graphql.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public GraphQLError handleBookNotFound(BookNotFoundException ex) {
        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(AuthorsNotFoundException.class)
    public GraphQLError handleAuthorsNotFound(AuthorsNotFoundException ex) {
        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public GraphQLError handleAuthorNotFound(AuthorNotFoundException ex) {
        return GraphqlErrorBuilder.newError()
                .message(ex.getMessage())
                .build();
    }

    // Add other exception handlers as needed
}
