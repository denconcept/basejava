package com.basejava.webapp.util;

import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;

import com.basejava.webapp.model.AbstractSection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParser {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter<>())
            .registerTypeAdapter(LocalDate.class, new JsonLocalDateAdapter())
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }
}
