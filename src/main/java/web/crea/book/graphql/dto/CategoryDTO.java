package web.crea.book.graphql.dto;

import java.util.List;

public record CategoryDTO(Long id, String name, List<BookDTO> books) {
}
