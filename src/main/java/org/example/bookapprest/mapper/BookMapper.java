package org.example.bookapprest.mapper;

import org.example.bookapprest.model.dto.BookDto;
import org.example.bookapprest.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "bookName", source = "title")
    BookDto toBookDto(Book book);

    @Named("mapDate")
    default String mapDate(LocalDateTime date) {
        if (date != null) {
            return DateTimeFormatter.ISO_LOCAL_DATE.format(date);
        }
        return "";
    }
}
