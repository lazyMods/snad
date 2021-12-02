package lazy.snad;

import lazy.snad.register.ModConfigs;
import lazy.snad.register.ModBlocks;
import lazy.snad.register.ModItems;
import lazy.snad.register.ModTags;
import net.minecraftforge.fml.common.Mod;

@Mod("snad")
public class Snad {

    public static final String MOD_ID = "snad";

    public Snad() {
        ModConfigs.registerAndLoadConfig();
        ModBlocks.init();
        ModItems.init();
        ModTags.init();
    }
}
