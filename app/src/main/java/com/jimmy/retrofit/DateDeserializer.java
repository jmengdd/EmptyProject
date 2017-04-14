package com.jimmy.retrofit;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public class DateDeserializer implements JsonDeserializer<Date> {
    private static final String TAG = DateDeserializer.class.getName();

    private static final String[] DATE_FORMATS = new String[]{
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ssZ",
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd",
            "hh:mma",
            "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
    };

    public static Date deserializeStringToDate(String s) {
        for (String format : DATE_FORMATS) {
            try {
                SimpleDateFormat df = new SimpleDateFormat(format);
                df.setTimeZone(TimeZone.getTimeZone("GMT"));
                return df.parse(s);
            } catch (ParseException e) {

            }
        }

        return null;
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        for (String format : DATE_FORMATS) {
            try {
                DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
                return formatter.parseDateTime(json.getAsString()).toDate();
            } catch (Exception e) {
            }
        }

        throw new JsonParseException("Unparseable date: \"" + json.getAsString()
                + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
    }
}
