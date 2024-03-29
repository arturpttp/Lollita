package net.dev.art.lollita.objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONReader {

    private final String json;

    public JSONReader(String path) throws IOException {
        this(new File(path));
    }

    public JSONReader(File file) throws IOException {
        this(new InputStreamReader(new FileInputStream(file)));
    }

    public JSONReader(Reader reader) throws IOException {
        this(new BufferedReader(reader));
    }

    public JSONReader(BufferedReader reader) throws IOException {
        json = load(reader);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            JSONObject jsn = new JSONObject(jsonText);
            return jsn;
        } finally {
            is.close();
        }
    }

    public static <E> List<E> toList(String path) {
        return toList(new File(path));
    }

    public static <E> List<E> toList(File file) {
        try {
            return toList(new InputStreamReader(new FileInputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<E>();
    }

    public static <E> List<E> toList(Reader reader) {
        return toList(new BufferedReader(reader));
    }

    @SuppressWarnings("unchecked")
    public static <E> List<E> toList(BufferedReader bufferedReader) {
        List<E> list = new ArrayList<E>();

        try {
            JSONReader reader = new JSONReader(bufferedReader);
            JSONArray array = reader.toJSONArray();
            for (int i = 0; i < array.length(); i++) {
                try {
                    list.add((E) array.get(i));
                } catch (ClassCastException e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static <V> Map<String, V> toMap(String path) {
        return toMap(new File(path));
    }

    public static <V> Map<String, V> toMap(File file) {
        try {
            return toMap(new InputStreamReader(new FileInputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<String, V>();
    }

    public static <V> Map<String, V> toMap(Reader reader) {
        return toMap(new BufferedReader(reader));
    }

    @SuppressWarnings("unchecked")
    public static <V> Map<String, V> toMap(BufferedReader bufferedReader) {
        Map<String, V> map = new HashMap<String, V>();

        try {
            JSONReader reader = new JSONReader(bufferedReader);
            JSONObject object = reader.toJSONObject();
            for (String key : object.keySet()) {
                Object obj = object.get(key);
                try {
                    map.put(key, (V) object.get(key));
                } catch (ClassCastException e) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    private String load(BufferedReader reader) throws IOException {
        StringBuilder builder = new StringBuilder();

        while (reader.ready())
            builder.append(reader.readLine());

        reader.close();

        return builder.length() == 0 ? "[]" : builder.toString();
    }

    public JSONArray toJSONArray() {
        return new JSONArray(json);
    }

    public JSONObject toJSONObject() {
        return new JSONObject(json);
    }

}
