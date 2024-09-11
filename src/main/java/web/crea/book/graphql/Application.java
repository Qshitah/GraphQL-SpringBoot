package web.crea.book.graphql;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import web.crea.book.graphql.entity.Author;
import web.crea.book.graphql.entity.Book;
import web.crea.book.graphql.entity.Category;
import web.crea.book.graphql.repository.AuthorRepository;
import web.crea.book.graphql.repository.BookRepository;
import web.crea.book.graphql.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(AuthorRepository authorRepository, BookRepository bookRepository, CategoryRepository categoryRepository) {
		return args -> {
			// Create and save authors
			Author josh = authorRepository.save(new Author(null, "Josh"));
			Author mark = authorRepository.save(new Author(null, "Mark"));

			// Create and save categories
			Category fiction = categoryRepository.save(new Category(null, "Fiction"));
			Category nonFiction = categoryRepository.save(new Category(null, "Non-Fiction"));
			Category science = categoryRepository.save(new Category(null, "Science"));

			// Create and save books with multiple authors and categories
			List<Author> authorsForBook1 = new ArrayList<>();
			authorsForBook1.add(josh);
			authorsForBook1.add(mark);

			List<Author> authorsForBook2 = new ArrayList<>();
			authorsForBook2.add(josh);

			List<Category> categoriesForBook1 = new ArrayList<>();
			categoriesForBook1.add(fiction);
			categoriesForBook1.add(science);

			List<Category> categoriesForBook2 = new ArrayList<>();
			categoriesForBook2.add(nonFiction);

			bookRepository.saveAll(List.of(
					new Book("Book One",  19.99, 10, "Publisher A", authorsForBook1, categoriesForBook1), // Both Josh and Mark, Fiction and Science
					new Book("Book Two",  29.99, 5, "Publisher B", authorsForBook2, categoriesForBook2), // Only Josh, Non-Fiction
					new Book("Book Three",  9.99, 2, "Publisher C", List.of(mark), List.of(science)) // Only Mark, Science
			));
		};
	}}
