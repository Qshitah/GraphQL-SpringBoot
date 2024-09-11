package web.crea.book.graphql.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.crea.book.graphql.entity.Book;
import web.crea.book.graphql.exception.BookNotFoundException;
import web.crea.book.graphql.repository.AuthorRepository;
import web.crea.book.graphql.repository.BookRepository;
import web.crea.book.graphql.service.BookService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public List<Book> fetchAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book fetchById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    @Transactional
    public Book saveBook(Book book){
         return bookRepository.save(book);
    }

    @Override
    @Transactional
    public Book updateBook(Book book) {
        if(bookRepository.existsById(book.getId())){
            return bookRepository.save(book);
        }else {
            throw new BookNotFoundException(book.getId());
        }
    }

    @Override
    @Transactional
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
