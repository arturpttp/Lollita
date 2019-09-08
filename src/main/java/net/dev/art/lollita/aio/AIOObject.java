package net.dev.art.lollita.aio;

import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AIOObject {

    private final Map<String, Object> map = new HashMap<String, Object>();
    public static final Object NULL = new AIOObject.Null();

    public AIOObject put(String key, Object value) throws AIOException{
        if (key == null){
            throw new AIOException("Can't put a null key");
        }
        if (value == null){
            throw new AIOException("Can't put a null value");
        }
        map.put(key, value);
        return this;
    }

    public Object get(String key) throws AIOException{
        if (key == null){
            throw new AIOException("Can't get a null key");
        }
        return map.get(key);
    }

    public String getString(String key) throws AIOException{
        if (key == null){
            throw new AIOException("Can't get a null key");
        }
        Object object = get(key);
        if (object instanceof String)
            return (String) object;
        else
            throw new AIOException("AIOObject[" + quote(key) + "] not a string.");
    }

    public int getInt(String key) throws AIOException{
        if (key == null){
            throw new AIOException("Can't get a null key");
        }
        Object object = get(key);
        if (object instanceof Integer)
            return (int) object;
        else
            throw new AIOException("AIOObject[" + quote(key) + "] not an int.");
    }

    public double getDouble(String key) throws AIOException{
        if (key == null){
            throw new AIOException("Can't get a null key");
        }
        Object object = get(key);
        if (object instanceof Double)
            return (Double) object;
        else
            throw new AIOException("AIOObject[" + quote(key) + "] not a double.");
    }

    public float getFloat(String key) throws AIOException{
        if (key == null){
            throw new AIOException("Can't get a null key");
        }
        Object object = get(key);
        if (object instanceof Float)
            return (float) object;
        else
            throw new AIOException("AIOObject[" + quote(key) + "] not a float.");
    }

    public short getShort(String key) throws AIOException{
        if (key == null){
            throw new AIOException("Can't get a null key");
        }
        Object object = get(key);
        if (object instanceof Short)
            return (short) object;
        else
            throw new AIOException("AIOObject[" + quote(key) + "] not a short.");
    }

    public boolean has(String key) {
        return this.map.containsKey(key);
    }

    public AIOObject remove(String key) {
        map.remove(key);
        return this;
    }

    public boolean isNull(String key) {
        return NULL.equals(this.opt(key));
    }

    public Iterator<String> keys() {
        return this.keySet().iterator();
    }

    public Set<String> keySet() {
        return this.map.keySet();
    }

    protected Set<Map.Entry<String, Object>> entrySet() {
        return this.map.entrySet();
    }

    public int length() {
        return this.map.size();
    }

    public Object opt(String key) {
        return key == null ? null : this.map.get(key);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public static String quote(String string) {
        StringWriter sw = new StringWriter();
        synchronized(sw.getBuffer()) {
            String var10000;
            try {
                var10000 = quote(string, sw).toString();
            } catch (IOException var5) {
                return "";
            }

            return var10000;
        }
    }

    public static Writer quote(String string, Writer w) throws IOException {
        if (string != null && !string.isEmpty()) {
            char c = 0;
            int len = string.length();
            w.write(34);

            for(int i = 0; i < len; ++i) {
                char b = c;
                c = string.charAt(i);
                switch(c) {
                    case '\b':
                        w.write("\\b");
                        continue;
                    case '\t':
                        w.write("\\t");
                        continue;
                    case '\n':
                        w.write("\\n");
                        continue;
                    case '\f':
                        w.write("\\f");
                        continue;
                    case '\r':
                        w.write("\\r");
                        continue;
                    case '"':
                    case '\\':
                        w.write(92);
                        w.write(c);
                        continue;
                    case '/':
                        if (b == '<') {
                            w.write(92);
                        }

                        w.write(c);
                        continue;
                }

                if (c >= ' ' && (c < 128 || c >= 160) && (c < 8192 || c >= 8448)) {
                    w.write(c);
                } else {
                    w.write("\\u");
                    String hhhh = Integer.toHexString(c);
                    w.write("0000", 0, 4 - hhhh.length());
                    w.write(hhhh);
                }
            }

            w.write(34);
            return w;
        } else {
            w.write("\"\"");
            return w;
        }
    }

    public Map<String, Object> getMap() {
        return map;
    }

    private static final class Null {
        private Null() {
        }

        protected final Object clone() {
            return this;
        }

        public boolean equals(Object object) {
            return object == null || object == this;
        }

        public int hashCode() {
            return 0;
        }

        public String toString() {
            return "null";
        }
    }

}
