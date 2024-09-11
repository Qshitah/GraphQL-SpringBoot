package web.crea.book.graphql.service;

import web.crea.book.graphql.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> fetchAllCategories();

    Category fetchById(Long id);

    Category fetchByName(String name);

    List<Category> searchByName(String name);

    List<Category> fetchAllById(List<Long> ids);

    Category saveCategory(Category category);

    Category updateCategory(Category category);

    Boolean deleteCategory(Long id);

}
