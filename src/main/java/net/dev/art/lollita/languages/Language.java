package net.dev.art.lollita.languages;

import net.dev.art.lollita.config.Config;

import java.util.HashMap;
import java.util.Map;

public class Language {

    private String name;
    private Map<String, String> options = new HashMap<>();
    private Config config;
    public static String path = "assets/langs/";
    public static Map<String, Language> languages = new HashMap<>();
    public Language(String name) {
        this.name=name.contains(".json") ? name : name+".json";
        this.config = new Config(path+this.name);
        config.save();
        config.getJSONObject().toMap().forEach((key, value) -> options.put(key, (String) value));
        languages.put(this.name.replace(".json", ""), this);
    }

    public boolean has(String key){
        return options.containsKey(key);
    }

    public void remove(String key) {
        options.remove(key);
    }

    public String flash(String key, String opt) {
        if (has(key)) {
            opt = getOption(key);
            remove(key);
        } else {
            set(key, opt);
            opt = getOption(key);
        }
        return opt;
    }

    public void set(String key, String value) {
        options.put(key, value);
    }

    public String getOption(String opt) {
        return options.get(opt);
    }

}
