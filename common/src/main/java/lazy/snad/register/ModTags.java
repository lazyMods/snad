package lazy.snad.register;

import lazy.snad.Snad;
import me.shedaniel.architectury.hooks.TagHooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class ModTags {

    public static Tag.Named<Block> SNAD;

    public static void init(){
        SNAD = TagHooks.getBlockOptional(new ResourceLocation(Snad.MOD_ID, "snad"));
    }
}
