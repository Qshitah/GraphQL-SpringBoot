package web.crea.book.graphql.dto;

import java.time.LocalDateTime;
import java.util.List;


public record BookDTO(
        Long id,
        String title,
        Double price,
        Integer stock,
        String publisherName,
        List<String> authorNames,
        List<String> categoryNames,
        String createdAt,
        String updateAt
) {}
