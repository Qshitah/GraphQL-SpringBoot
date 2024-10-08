package web.crea.book.graphql.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import web.crea.book.graphql.dto.AuthorDTO;
import web.crea.book.graphql.dto.BookDTO;
import web.crea.book.graphql.entity.Author;
import web.crea.book.graphql.entity.Book;
import web.crea.book.graphql.entity.Category;
import web.crea.book.graphql.repository.AuthorRepository;
import web.crea.book.graphql.service.AuthorService;
import web.crea.book.graphql.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @QueryMapping
    public List<AuthorDTO> authors() {
        return authorService.fetchAllAuthors()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    public AuthorDTO authorById(@Argument Long id){
        Author author = authorService.fetchById(id);
        return mapToDTO(author);
    }

    @QueryMapping
    public List<AuthorDTO> authorsByName(@Argument String name){
        return authorService.searchByName(name)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @MutationMapping
    public AuthorDTO addAuthor(@Argument AuthorInput authorInput){
        Author author = new Author(null,authorInput.name);
        return mapToDTO(authorService.saveAuthor(author));
    }

    @MutationMapping
    public AuthorDTO updateAuthor(@Argument Long id, @Argument AuthorInput authorInput){
        Author author = new Author(id,authorInput.name);
        return mapToDTO(authorService.updateAuthor(author));
    }

    @MutationMapping
    public Boolean deleteAuthor(@Argument Long id){
        return authorService.deleteAuthor(id);
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
                book.getCreatedAt().toString(),
                book.getUpdatedAt().toString()
        );
    }

    public record AuthorInput(@NotEmpty String name){}
}
