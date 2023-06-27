package com.simplesystem.todo.mapper;

import java.time.Instant;
import java.time.ZonedDateTime;

import org.mapstruct.Mapper;
import utils.TimeUtil;

@Mapper
public interface ZonedDateTimeMapper {

    default ZonedDateTime toZonedDateTime(Instant instant) {
        return instant == null ? null : ZonedDateTime.ofInstant(instant, TimeUtil.TIMEZONE_BERLIN);
    }

    default Instant toInstant(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : zonedDateTime.toInstant();
    }
}
