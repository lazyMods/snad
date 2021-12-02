package lazy.snad.register;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import lazy.snad.Snad;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class ModConfigs {

    public static ForgeConfigSpec COMMON;

    public static ForgeConfigSpec.IntValue SPEED;

    static {
        var builder = new ForgeConfigSpec.Builder();

        builder.push(Snad.MOD_ID);
        SPEED = builder.comment("The growth speed increase value that snad gives.").defineInRange("speed", 2, 1, 20);
        builder.pop();

        COMMON = builder.build();
    }

    public static void registerAndLoadConfig() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON);
        var config = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(Snad.MOD_ID.concat("-common.toml"))).sync().writingMode(WritingMode.REPLACE).build();
        config.load();
        COMMON.setConfig(config);
    }
}