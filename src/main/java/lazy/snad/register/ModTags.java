package lazy.snad.register;

import lazy.snad.Snad;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static Tag.Named<Block> SNAD;
    public static Tag.Named<Block> VALID_PLANT;

    public static void init() {
        SNAD = BlockTags.createOptional(new ResourceLocation(Snad.MOD_ID, "snad"));
        VALID_PLANT = BlockTags.createOptional(new ResourceLocation(Snad.MOD_ID, "valid_plant"));
    }
}
