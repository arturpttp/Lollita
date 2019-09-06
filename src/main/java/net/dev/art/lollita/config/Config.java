package net.dev.art.lollita.config;


import net.dev.art.lollita.Utils;
import net.dev.art.lollita.objects.JSONReader;
import net.dev.art.lollita.objects.JSONWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Config {

    private org.json.JSONObject JSONObject;
    private String path;
    private File file;
    private String[] split;
    private String name;

    public Config(String path) {
        this.file = new File(path);
        if (this.file.exists()) {
            try {
                JSONReader reader = new JSONReader(this.file);
                this.JSONObject = reader.toJSONObject();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.JSONObject = new JSONObject();
        }
        split = path.split("/");
        name = split[split.length - 1];
    }

    public void save(String path, Object object) {
        JSONObject obj = new JSONObject();
        for (Field f : object.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            if (path == null) {
                try {
                    set(f.getName(), f.get(object).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                obj.put(f.getName(), f.toString());
            }
        }
        if (path != null) {
            set(path, obj);
        }
    }


    public JSONObject getJSON(String path) {
        return JSONObject.getJSONObject(path);
    }


    public List<JSONObject> getAll() {
        List<JSONObject> list = new ArrayList<JSONObject>();
        Scanner scn = null;
        try {
            scn = new Scanner(file, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (scn.hasNext()) {
            JSONObject obj = null;
            try {
                obj = (JSONObject) new JSONParser().parse(scn.nextLine());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            list.add(obj);
        }
        return list;
    }


    public void save() {
        try {
            JSONWriter writer = new JSONWriter(file);
            writer.write(JSONObject);
            writer.flush();
            Utils.print("Carregando arquivo " + name);
        } catch (IOException e) {
            e.printStackTrace();
            Utils.print("Não foi possivel salvar o arquivo " + name);
        }

    }

    public boolean exists() {
        return file.exists();
    }

    public JSONArray getArray(String path) {
        if (!getJSONObject().has(path)) {
            set(path, new JSONObject());
        }
        return (JSONArray) get(path);
    }

    public Object get(String path, Object defaults) {
        if (JSONObject.has(path)) {
            return JSONObject.get(path);
        }
        JSONObject.put(path, defaults);
        return this.get(path, defaults);
    }

    public void set(String path, Object value) {
        if (JSONObject.has(path))
            return;
        JSONObject.put(path, value);
    }

    public void replace(String path, Object value) {
        JSONObject.put(path, value);
    }

    public Object get(String path) {
        return this.get(path, "Default object value");
    }

    public boolean has(String path) {
        return JSONObject.has(path);
    }

    public String getString(String path, String defaults) {
        if (JSONObject.has(path)) {
            return JSONObject.getString(path);
        }
        JSONObject.put(path, defaults);
        return this.getString(path, defaults);
    }

    public JSONObject getSection(String section) {
        if (JSONObject.getJSONObject(section) == null)
            set(section, new JSONObject());
        return JSONObject.getJSONObject(section);
    }

    public boolean delete() {
        Utils.print("Arquivo " + name + " deletado com sucesso!");
        return this.file.delete();
    }

    public String getString(String path) {
        return this.getString(path, "Default object value");
    }

    public JSONObject getJSONObject() {
        return JSONObject;
    }

    public String getPath() {
        return path;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

}
