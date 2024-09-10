package web.crea.book.graphql;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import web.crea.book.graphql.entity.Author;
import web.crea.book.graphql.entity.Book;
import web.crea.book.graphql.repository.AuthorRepository;
import web.crea.book.graphql.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(AuthorRepository authorRepository, BookRepository bookRepository) {
		return args -> {
			// Create and save authors
			Author josh = authorRepository.save(new Author(null, "Josh"));
			Author mark = authorRepository.save(new Author(null, "Mark"));

			// Create and save books with multiple authors
			List<Author> authorsForBook1 = new ArrayList<>();
			authorsForBook1.add(josh);
			authorsForBook1.add(mark);

			List<Author> authorsForBook2 = new ArrayList<>();
			authorsForBook2.add(josh);

			bookRepository.saveAll(List.of(
					new Book("Book One", "Genre A", authorsForBook1), // Both Josh and Mark
					new Book("Book Two", "Genre B", authorsForBook2), // Only Josh
					new Book("Book Three", "Genre C", List.of(mark)) // Only Mark
			));
		};
	}
}
