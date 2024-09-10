package web.crea.book.graphql.dto;


import java.util.List;

public record AuthorDTO(Long id, String name, List<BookDTO> books) {}