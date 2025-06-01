package edu.du.ict4315.parking.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializer {
    public static final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .disableHtmlEscaping()
        .excludeFieldsWithoutExposeAnnotation()
        .create();
}
