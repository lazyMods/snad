package lazy.snad.register;

import lazy.snad.Snad;
import lazy.snad.block.SnadBlock;
import lazy.snad.block.SoulSnadBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Snad.MOD_ID);

    public static final RegistryObject<Block> SNAD = BLOCKS.register("snad", () -> new SnadBlock(-2370656, MaterialColor.SAND));
    public static final RegistryObject<Block> RED_SNAD = BLOCKS.register("red_snad", () -> new SnadBlock(-5679071, MaterialColor.SAND));
    public static final RegistryObject<Block> SOUL_SNAD = BLOCKS.register("soul_snad", SoulSnadBlock::new);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
