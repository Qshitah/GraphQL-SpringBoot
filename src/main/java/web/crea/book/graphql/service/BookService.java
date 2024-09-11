package web.crea.book.graphql.service;

import web.crea.book.graphql.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> fetchAllBooks();

    Book fetchById(Long id);

    List<Book> searchByTitle(String title);

    Book saveBook(Book book);

    Book updateBook(Book book);

    boolean deleteBook(Long id);
}
