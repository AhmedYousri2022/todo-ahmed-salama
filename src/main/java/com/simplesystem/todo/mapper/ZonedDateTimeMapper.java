package com.simplesystem.todo.mapper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.mapstruct.Mapper;

@Mapper
public interface ZonedDateTimeMapper {

    default ZonedDateTime toZonedDateTime(Instant instant) {
        return instant == null ? null : ZonedDateTime.ofInstant(instant, ZoneId.of("Europe/Berlin"));
    }

    default Instant toInstant(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : zonedDateTime.toInstant();
    }
}
