package com.lazy.snad;

import com.lazy.snad.proxy.ClientProxy;
import com.lazy.snad.proxy.CommonProxy;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(Snad.MODID)
public class Snad {

    public static final String MODID = "snad";

    public static CommonProxy proxy;

    public Snad() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configs.COMMON_CONFIG);

        proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
        proxy.init();

        Configs.load(Configs.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("snad-common.toml"));
    }
}
