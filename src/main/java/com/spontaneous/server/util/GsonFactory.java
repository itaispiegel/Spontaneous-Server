package com.spontaneous.server.util;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by eidan on 5/25/15.
 */
public final class GsonFactory {

    private static Gson sGson;

    private GsonFactory() {
    }

    public static synchronized Gson getGson() {
        if (sGson == null) {
            sGson = Converters.registerDateTime(new GsonBuilder()
                    .serializeNulls())
                    .create();
        }
        return sGson;
    }
}
