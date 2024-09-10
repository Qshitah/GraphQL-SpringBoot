package web.crea.book.graphql.controller;

import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import web.crea.book.graphql.dto.AuthorDTO;
import web.crea.book.graphql.dto.BookDTO;
import web.crea.book.graphql.entity.Author;
import web.crea.book.graphql.entity.Book;
import web.crea.book.graphql.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    @QueryMapping
    public Iterable<AuthorDTO> authors() {
        return authorRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private AuthorDTO mapToDTO(Author author) {
        List<BookDTO> books = author.getBooks()
                .stream()
                .map(this::mapBookToDTO)
                .collect(Collectors.toList());
        return new AuthorDTO(author.getId(), author.getName(), books);
    }

    private BookDTO mapBookToDTO(Book book) {
        List<String> authorNames = book.getAuthors()
                .stream()
                .map(Author::getName)
                .collect(Collectors.toList());
        return new BookDTO(book.getId(), book.getTitle(), book.getGenre(), authorNames);
    }
}
