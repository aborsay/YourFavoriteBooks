package xyz.borsay.yourfavoritebooks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    /**
     * This is a simple way to create the genres in a map form. I however think there might be
     * a different way that would by more adaptable using the database instead.
     */
    public static final Map<String, String> genreMap = createMap();

    private static Map<String, String> createMap() {
        Map<String, String> result = new HashMap<>();
        result.put("0", "Fantasy");
        result.put("1", "Western");
        result.put("2", "Romance");
        result.put("3", "Thriller");
        result.put("4", "Mystery");
        result.put("5", "Detective story");
        result.put("6", "Dystopia");
        result.put("7", "Memoir");
        result.put("8", "Biography");
        result.put("9", "Play");
        result.put("10", "Musical");
        result.put("11", "Satire");
        result.put("12", "Haiku");
        result.put("13", "Horror");
        result.put("14", "DIY (Do It Yourself)");
        result.put("15","Other");
        return Collections.unmodifiableMap(result);
    }




}
