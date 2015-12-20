package com.spontaneous.server.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.DateTime;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * This class is a util for serializing dates to json.
 */
public class DateTimeSerializer extends JsonSerializer<DateTime> {

    /**
     * The expected date format.
     */
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Override
    public void serialize(DateTime date, JsonGenerator generator, SerializerProvider provider) throws IOException {
        generator.writeString(formatter.format(date.toDate()));
    }
}
