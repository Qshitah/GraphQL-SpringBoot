package web.crea.book.graphql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.crea.book.graphql.entity.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByNameContainingIgnoreCase(String name);

}