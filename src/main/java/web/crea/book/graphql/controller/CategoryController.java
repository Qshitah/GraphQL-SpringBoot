package web.crea.book.graphql.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import web.crea.book.graphql.dto.AuthorDTO;
import web.crea.book.graphql.dto.BookDTO;
import web.crea.book.graphql.dto.CategoryDTO;
import web.crea.book.graphql.entity.Author;
import web.crea.book.graphql.entity.Book;
import web.crea.book.graphql.entity.Category;
import web.crea.book.graphql.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @QueryMapping
    public List<CategoryDTO> categories() {
        return categoryService.fetchAllCategories()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    @QueryMapping
    public CategoryDTO categoryById(@Argument Long id){
        Category category = categoryService.fetchById(id);
        return mapToDTO(category);
    }



    @QueryMapping
    public List<CategoryDTO> categoriesByName(@Argument String name){
        return categoryService.searchByName(name)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @QueryMapping
    public CategoryDTO categoryByName(@Argument String name){
        return mapToDTO(categoryService.fetchByName(name));
    }

    @MutationMapping
    public CategoryDTO addCategory(@Argument CategoryInput categoryInput){
        Category category = new Category(null,categoryInput.name);
        return mapToDTO(categoryService.saveCategory(category));
    }

    @MutationMapping
    public CategoryDTO updateCategory(@Argument Long id, @Argument CategoryInput categoryInput){
        Category category = new Category(id,categoryInput.name);
        return mapToDTO(categoryService.updateCategory(category));
    }

    @MutationMapping
    public Boolean deleteCategory(@Argument Long id){
        return categoryService.deleteCategory(id);
    }

    private CategoryDTO mapToDTO(Category category) {
        List<BookDTO> books = category.getBooks()
                .stream()
                .map(this::mapBookToDTO)
                .collect(Collectors.toList());
        return new CategoryDTO(category.getId(), category.getName(), books);
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

    public record CategoryInput(@NotEmpty String name){}

}
