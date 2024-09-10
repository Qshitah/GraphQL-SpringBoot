package web.crea.book.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.crea.book.graphql.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}