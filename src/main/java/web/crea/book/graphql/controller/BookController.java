package web.crea.book.graphql.controller;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import web.crea.book.graphql.dto.BookDTO;
import web.crea.book.graphql.entity.Author;
import web.crea.book.graphql.entity.Book;
import web.crea.book.graphql.repository.AuthorRepository;
import web.crea.book.graphql.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @QueryMapping
    public Iterable<BookDTO> books(){
        return bookRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @MutationMapping
    public BookDTO addBook(@Argument BookInput bookInput) {
        // Find authors by IDs
        List<Author> authors = authorRepository.findAllById(bookInput.authorIds());

        if (authors.isEmpty()) {
            throw new IllegalArgumentException("No authors found");
        }

        Book book = new Book();
        book.setTitle(bookInput.title());
        book.setGenre(bookInput.genre());
        book.setAuthors(authors);

        Book savedBook = bookRepository.save(book);
        return mapToDTO(savedBook);
    }

    private BookDTO mapToDTO(Book book) {
        List<String> authorNames = book.getAuthors()
                .stream()
                .map(Author::getName)
                .collect(Collectors.toList());
        return new BookDTO(book.getId(), book.getTitle(), book.getGenre(), authorNames);
    }

    record BookInput(String title, String genre, List<Long> authorIds) {}
}
