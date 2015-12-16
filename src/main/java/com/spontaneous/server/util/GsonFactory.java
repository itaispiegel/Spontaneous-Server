package com.spontaneous.server.util;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gson instance factory.
 */
public final class GsonFactory {

    /**
     * Static Gson instance.
     */
    private static Gson sGson;

    /**
     * The class is static, so there is no login in creating a class instance.
     */
    private GsonFactory() {
    }

    /**
     * @return The Gson instance.
     */
    public static synchronized Gson getGson() {
        if (sGson == null) {
            sGson = Converters.registerDateTime(new GsonBuilder()
                    .serializeNulls())
                    .create();
        }
        return sGson;
    }
}
