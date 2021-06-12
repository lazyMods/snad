package lazy.snad;

import lazy.snad.config.ModConfigs;
import lazy.snad.register.ModBlocks;
import lazy.snad.register.ModItems;
import lazy.snad.register.ModTags;

public class Snad {

    public static final String MOD_ID = "snad";

    public static ModConfigs configs = ModConfigs.get();

    public static void init() {
        ModBlocks.init();
        ModItems.init();
        ModTags.init();
    }
}
