package web.crea.book.graphql.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.crea.book.graphql.entity.Author;
import web.crea.book.graphql.exception.AuthorNotFoundException;
import web.crea.book.graphql.repository.AuthorRepository;
import web.crea.book.graphql.service.AuthorService;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> fetchAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public List<Author> fetchAllAuthorsById(List<Long> ids) {
        return authorRepository.findAllById(ids);
    }

    @Override
    public Author fetchById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @Override
    public List<Author> searchByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public Author updateAuthor(Author author) {
        if(authorRepository.existsById(author.getId())){
            return saveAuthor(author);
        }else {
            throw new AuthorNotFoundException(author.getId());
        }
    }

    @Override
    @Transactional
    public boolean deleteAuthor(Long id) {
        if(authorRepository.existsById(id)){
            authorRepository.deleteById(id);
            return true;
        }else {
            throw new AuthorNotFoundException(id);
        }
    }


}
