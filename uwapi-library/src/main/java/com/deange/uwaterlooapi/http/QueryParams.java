package com.deange.uwaterlooapi.http;

import com.deange.uwaterlooapi.utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class QueryParams extends ArrayList<Pair<String, String>> {

    public static QueryParams make(final String... args) {

        if (args.length % 2 != 0) {
            throw new IllegalArgumentException("Argument length must be multiple of two");
        }

        // Collect the arguments into a single container
        final QueryParams result = new QueryParams();
        for (int i = 0; i < args.length; i += 2) {
            result.add(Pair.make(args[i], args[i + 1]));
        }

        return result;
    }

    public static QueryParams make(final Pair<String, String>... args) {
        return make(Arrays.asList(args));
    }

    public static QueryParams make(final Collection<Pair<String, String>> args) {
        final QueryParams result = new QueryParams();
        result.addAll(args);
        return result;
    }


}
