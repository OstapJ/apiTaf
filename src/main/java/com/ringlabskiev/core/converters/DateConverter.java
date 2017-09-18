package com.ringlabskiev.core.converters;

import java.time.LocalDate;

import static java.lang.Math.round;

public final class DateConverter {

    private static final double SOLS_TO_DAYS_FACTOR = 1.027491251;

    private DateConverter() {
        throw new UnsupportedOperationException("Illegal access to private constructor");
    }

    public static String convertSolToEarthDateForRover(final int sol, final String roverLandingDate) {
        long earthDate = round(sol * SOLS_TO_DAYS_FACTOR);
        return LocalDate.parse(roverLandingDate).plusDays(earthDate).toString();
    }
}
