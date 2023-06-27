package com.simplesystem.todo.mapper;

import java.time.Instant;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import utils.TimeUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

class ZonedTimeDateMapperTest {

    private final ZonedDateTimeMapper mapper = Mappers.getMapper(ZonedDateTimeMapper.class);

    @Test
    void should_Map_To_Instant() {
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(Instant.now(), TimeUtil.TIMEZONE_BERLIN);

        assertThat(mapper.toInstant(zonedDateTime), is(zonedDateTime.toInstant()));
    }

    @Test
    void should_Map_To_Instant_When_ZonedDateTime_Is_Null() {
        ZonedDateTime zonedDateTime = null;

        assertThat(mapper.toInstant(zonedDateTime), is(nullValue()));
    }

    @Test
    void should_Map_To_ZonedDateTime_From_Instant() {
        Instant instant = Instant.now();

        assertThat(mapper.toZonedDateTime(instant), is(instant.atZone(TimeUtil.TIMEZONE_BERLIN)));
    }

    @Test
    void should_Map_To_ZonedDateTime_From_Instant_When_Instant_Is_Null() {
        Instant instant = null;

        assertThat(mapper.toZonedDateTime(instant), is(nullValue()));
    }
}
