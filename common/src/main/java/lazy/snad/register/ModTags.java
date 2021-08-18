package lazy.snad.register;

import dev.architectury.hooks.tags.TagHooks;
import lazy.snad.Snad;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static Tag.Named<Block> SNAD;
    public static Tag.Named<Block> VALID_PLANT;

    public static void init() {
        SNAD = TagHooks.optionalBlock(new ResourceLocation(Snad.MOD_ID, "snad"));
        VALID_PLANT = TagHooks.optionalBlock(new ResourceLocation(Snad.MOD_ID, "valid_plant"));
    }
}
