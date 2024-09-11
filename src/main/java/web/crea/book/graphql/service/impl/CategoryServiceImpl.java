package web.crea.book.graphql.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.crea.book.graphql.entity.Category;
import web.crea.book.graphql.exception.CategoryNotFoundException;
import web.crea.book.graphql.repository.CategoryRepository;
import web.crea.book.graphql.service.CategoryService;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> fetchAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category fetchById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    @Override
    public Category fetchByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Category not found")) ;
    }

    @Override
    public List<Category> searchByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }



    @Override
    public List<Category> fetchAllById(List<Long> ids) {
        return categoryRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Category category) {
        if(categoryRepository.existsById(category.getId())){
            return categoryRepository.save(category);
        }

        throw new CategoryNotFoundException(category.getId());
    }

    @Override
    public Boolean deleteCategory(Long id) {
        if(categoryRepository.existsById(id)){
           categoryRepository.deleteById(id);
           return true;
        }
        return false;
    }
}
