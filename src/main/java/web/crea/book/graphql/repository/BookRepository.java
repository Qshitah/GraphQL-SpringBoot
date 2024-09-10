package web.crea.book.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.crea.book.graphql.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
