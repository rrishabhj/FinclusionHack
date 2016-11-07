package com.rishabh.github.finclusionhack.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateTypeAdapter implements JsonDeserializer<Date> {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private final DateFormat mDateFormatter;

    public DateTypeAdapter() {
        mDateFormatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        try {
            return mDateFormatter.parse(json.getAsString());
        } catch (ParseException e) {
            return null;
        }
    }
}