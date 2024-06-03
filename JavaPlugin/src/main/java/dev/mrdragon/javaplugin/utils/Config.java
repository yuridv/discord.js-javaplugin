package dev.mrdragon.javaplugin.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class Config {
    Yaml yaml = new Yaml();

    InputStream inputStream = this.getClass().getClassLoader()
            .getResourceAsStream("config.yml");

    Map<String, Object> env = yaml.load(inputStream);

    public String get(String key) {
        Object result = env.get(key);

        if (result == null) {
            System.out.println("A config '" + key + "' não foi configurada ainda...");
            return "Undefined";
        }

        if (result instanceof String) {
            return (String) result;
        } else {
            return String.valueOf(result);
        }
    }
}