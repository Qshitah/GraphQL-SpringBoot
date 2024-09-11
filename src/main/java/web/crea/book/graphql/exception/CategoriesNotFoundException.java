package web.crea.book.graphql.exception;

import java.util.List;

public class CategoriesNotFoundException extends RuntimeException {
    public CategoriesNotFoundException(List<Long> categoriesIds) {
        super("No categories found for IDs: " + categoriesIds);
    }
}