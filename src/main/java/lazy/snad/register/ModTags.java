package lazy.snad.register;

import lazy.snad.Snad;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static TagKey<Block> SNAD;
    public static TagKey<Block> VALID_PLANT;

    public static void init() {
        SNAD = BlockTags.create(new ResourceLocation(Snad.MOD_ID, "snad"));
        VALID_PLANT = BlockTags.create(new ResourceLocation(Snad.MOD_ID, "valid_plant"));
    }
}
