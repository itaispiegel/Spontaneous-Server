package com.spontaneous.server.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is a util class for converting data objects to representational objects.
 */
public class Converter {

    /**
     * This method converts a list of data objects, to a list of representational objects.
     *
     * @param list       The list to convert.
     * @param converter The method that converts each data object, to its representational form.
     * @param <F>        The type of the data object.
     * @param <T>        The type of the representational object.
     * @return The list of converted data objects.
     */
    public static <F, T> List<T> convertList(List<F> list, Function<F, T> converter) {
        return list.stream()
                .map(converter)
                .collect(Collectors.toList());
    }

}
