package lazy.snad;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

public class ConfigPathPlatformExpect {

    @ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }
}