package web.crea.book.graphql.dto;

import java.util.List;

public record BookDTO(Long id, String title, String genre, List<String> authorNames) {}