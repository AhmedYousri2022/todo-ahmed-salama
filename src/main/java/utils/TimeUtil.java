package utils;

import java.time.ZoneId;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TimeUtil {

    public static final String TIMEZONE_BERLIN_STRING = "Europe/Berlin";

    public static final ZoneId TIMEZONE_BERLIN = ZoneId.of(TIMEZONE_BERLIN_STRING);

    public static final String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";
}
