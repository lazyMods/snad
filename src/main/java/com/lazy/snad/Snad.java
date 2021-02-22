package com.lazy.snad;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Snad.MOD_ID)
public class Snad {

    public static final String MOD_ID = "snad";

    public Snad() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configs.COMMON_CONFIG);
        Configs.load(Configs.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("snad-common.toml"));

        Setup.init();
    }
}
