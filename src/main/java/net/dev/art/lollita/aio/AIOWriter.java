package net.dev.art.lollita.aio;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class AIOWriter {

    private final BufferedWriter writer;
    private int space;

    public static final char OBJECT_START = '{';
    public static final char OBJECT_END = '}';
    public static final char ARRAY_START = '[';
    public static final char ARRAY_END = ']';
    public static final char STRING_START = '\"';
    public static final char STRING_END = '\"';
    public static final char ENTRY_SEPARATOR = ':';
    public static final char ITEM_SEPARATOR = ',';
    public static final char SPACE = ' ';
    public static final char ENTER = '\n';

    public AIOWriter(String path) throws IOException {
        this(new File(path));
    }

    public AIOWriter(File file) throws IOException {
        this(new FileWriter(file));
    }

    public AIOWriter(Writer writer) {
        this(new BufferedWriter(writer));
    }

    public AIOWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    public void write(AIOList array) throws IOException {
        writer.write(ARRAY_START);
        writer.newLine();

        this.space += 2;
        String space = spaceBuilder();

        for (int i = 0; i < array.length(); i++) {
            Object object = array.get(i);

            if (object instanceof Number || object instanceof Boolean)
                writer.write(space + object);
            else if (object instanceof AIOObject)
                write((AIOObject) object, true);
            else if (object instanceof AIOList)
                write((AIOList) object);
            else
                writer.write(space + STRING_START + object.toString() + STRING_START);

            if (i < array.length() - 1)
                writer.write(ITEM_SEPARATOR);
            writer.newLine();
        }

        this.space -= 2;
        space = spaceBuilder();

        writer.write(space + ARRAY_END);
    }

    private void write(AIOObject aioObject, boolean spacing) throws IOException {
        writer.write((spacing ? spaceBuilder() : "") + OBJECT_START);
        writer.newLine();

        this.space += 2;
        String space = spaceBuilder();

        int i = 0;
        final int max = aioObject.length();

        for (String key : aioObject.keySet()) {
            writer.write(space + STRING_START + key + STRING_START + ENTRY_SEPARATOR);
            Object object = aioObject.get(key);

            if (object instanceof Number || object instanceof Boolean)
                writer.write(object.toString());
            else if (object instanceof AIOObject)
                write((AIOObject) object, false);
            else if (object instanceof AIOList)
                write((AIOList) object);
            else
                writer.write(STRING_START + object.toString() + STRING_START);

            if (i < max - 1)
                writer.write(ITEM_SEPARATOR);
            i++;

            writer.newLine();
        }

        this.space -= 2;
        space = spaceBuilder();

        writer.write(space + OBJECT_END);
    }

    public void write(AIOObject aioObject) throws IOException {
        write(aioObject, false);
    }

    private String spaceBuilder() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < space; i++)
            builder.append(" ");
        return builder.length() == 0 ? "" : builder.toString();
    }

    public void flush() throws IOException {
        writer.flush();
    }

    public void close() throws IOException {
        writer.close();
    }

}
