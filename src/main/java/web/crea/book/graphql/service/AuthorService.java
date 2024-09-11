package web.crea.book.graphql.service;

import web.crea.book.graphql.entity.Author;

import java.util.List;

public interface AuthorService {

    List<Author> fetchAllAuthors();

    List<Author> fetchAllAuthorsById(List<Long> ids);

    Author fetchById(Long id);

    List<Author> searchByName(String name);

    Author saveAuthor(Author author);

    Author updateAuthor(Author author);

    boolean deleteAuthor(Long id);



}
