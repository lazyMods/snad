package lazy.snad.forge;

import lazy.snad.Snad;
import lazy.snad.forge.config.SandCloth;
import me.shedaniel.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Snad.MOD_ID)
public class SnadForge {
    public SnadForge() {
        EventBuses.registerModEventBus(Snad.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Snad.init();
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> SandCloth::create);
    }
}
