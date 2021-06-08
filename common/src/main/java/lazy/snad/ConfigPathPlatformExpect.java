package lazy.snad;

import me.shedaniel.architectury.annotations.ExpectPlatform;

import java.nio.file.Path;

public class ConfigPathPlatformExpect {

    @ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }
}