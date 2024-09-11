package web.crea.book.graphql.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Category not found with ID: " + id);
    }
}
