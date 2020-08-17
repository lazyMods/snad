package com.lazy.snad;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Configs {

    public static final String CATEGORY_GENERAL = "snad";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.IntValue SPEED_INCREASE_DEFAULT_VALUE;

    static {
        COMMON_BUILDER.comment("Snad Configs").push(CATEGORY_GENERAL);

        SPEED_INCREASE_DEFAULT_VALUE = COMMON_BUILDER.comment("Growth modifier on snad blocks.").defineInRange("speedIncrease", 2, 1, Integer.MAX_VALUE);

        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static void load(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
        configData.load();
        spec.setConfig(configData);
    }
}