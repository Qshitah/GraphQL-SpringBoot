package web.crea.book.graphql.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import web.crea.book.graphql.dto.BookDTO;
import web.crea.book.graphql.entity.Author;
import web.crea.book.graphql.entity.Book;
import web.crea.book.graphql.entity.Category;
import web.crea.book.graphql.exception.AuthorsNotFoundException;
import web.crea.book.graphql.exception.BookNotFoundException;
import web.crea.book.graphql.exception.CategoriesNotFoundException;
import web.crea.book.graphql.repository.CategoryRepository;
import web.crea.book.graphql.service.AuthorService;
import web.crea.book.graphql.service.BookService;
import web.crea.book.graphql.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @QueryMapping
    public Iterable<BookDTO> books() {
        return bookService.fetchAllBooks()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    public BookDTO bookById(@Argument Long id) {
        Book book = bookService.fetchById(id);
        return mapToDTO(book);
    }

    @QueryMapping
    public List<BookDTO> booksByTitle(@Argument String title) {
        return bookService.searchByTitle(title)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @MutationMapping
    public BookDTO addBook(@Argument @Valid BookInput bookInput) {
        // Find authors by IDs
        List<Author> authors = authorService.fetchAllAuthorsById(bookInput.authorIds());

        if (authors.isEmpty()) {
            throw new AuthorsNotFoundException(bookInput.authorIds());
        }

        // Find categories by IDs
        List<Category> categories = categoryService.fetchAllById(bookInput.categoryIds);

        if (categories.isEmpty()) {
            throw new CategoriesNotFoundException(bookInput.categoryIds);
        }

        Book book = new Book();
        book.setTitle(bookInput.title);
        book.setPrice(bookInput.price);
        book.setStock(bookInput.stock);
        book.setPublisherName(bookInput.publisherName);
        book.setAuthors(authors);
        book.setCategories(categories);

        Book savedBook = bookService.saveBook(book);
        return mapToDTO(savedBook);
    }

    @MutationMapping
    public BookDTO updateBook(@Argument Long id, @Argument @Valid BookInput bookInput) {
        // Find authors by IDs
        List<Author> authors = authorService.fetchAllAuthorsById(bookInput.authorIds());

        if (authors.isEmpty()) {
            throw new AuthorsNotFoundException(bookInput.authorIds());
        }

        // Find categories by IDs
        List<Category> categories = categoryService.fetchAllById(bookInput.categoryIds);

        if (categories.isEmpty()) {
            throw new CategoriesNotFoundException(bookInput.categoryIds);
        }

        Book book = bookService.fetchById(id); // Fetch existing book
        if (book == null) {
            throw new BookNotFoundException(id); // Handle if book is not found
        }

        book.setTitle(bookInput.title);
        book.setPrice(bookInput.price);
        book.setStock(bookInput.stock);
        book.setPublisherName(bookInput.publisherName);
        book.setAuthors(authors);
        book.setCategories(categories);

        Book savedBook = bookService.updateBook(book);
        return mapToDTO(savedBook);
    }

    @MutationMapping
    public boolean deleteBook(@Argument Long id) {
        return bookService.deleteBook(id);
    }

    private BookDTO mapToDTO(Book book) {
        List<String> authorNames = book.getAuthors()
                .stream()
                .map(Author::getName)
                .collect(Collectors.toList());
        List<String> categoryNames = book.getCategories().stream()
                .map(Category::getName)
                .collect(Collectors.toList());

        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getPrice(),
                book.getStock(),
                book.getPublisherName(),
                authorNames,
                categoryNames,
                book.getCreatedAt() != null ? book.getCreatedAt().toString() : null,
                book.getUpdatedAt() != null ? book.getUpdatedAt().toString() : null
        );
    }

    public record BookInput(
            @NotEmpty String title,
            @NotEmpty List<Long> authorIds,
            @Positive Double price,
            @NotNull Integer stock,
            String publisherName,
            List<Long> categoryIds
    ) {}
}
