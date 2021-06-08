package lazy.snad.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lazy.snad.ConfigPathPlatformExpect;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModConfigs {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Path CONFIG_PATH = ConfigPathPlatformExpect.getConfigDirectory();
    public static final String FULL_PATH = CONFIG_PATH.toString() + "/snad_config.json";
    private int speed = 2;

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    private static ModConfigs load() {
        ModConfigs config = new ModConfigs();
        try {
            checkExistence();
            config = GSON.fromJson(new FileReader(FULL_PATH), ModConfigs.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    public static ModConfigs get() {
        ModConfigs config = load();
        if (config.speed < 1)
            config.speed = 1;
        return config;
    }

    public static void save(ModConfigs config) {
        try {
            checkExistence();
            FileWriter writer = new FileWriter(FULL_PATH);
            GSON.toJson(config, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkExistence() throws IOException {
        if (!Files.exists(CONFIG_PATH)) Files.createDirectories(CONFIG_PATH);
        if (!Files.exists(Paths.get(FULL_PATH))) {
            Files.createFile(Paths.get(FULL_PATH));
            save(new ModConfigs());
        }
    }
}