package lazy.snad.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import lazy.snad.Snad;
import lazy.snad.block.SnadBlock;
import lazy.snad.block.SoulSnadBlock;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Snad.MOD_ID, Registry.BLOCK_REGISTRY);

    public static final RegistrySupplier<Block> SNAD = BLOCKS.register("snad", () -> new SnadBlock(-2370656, MaterialColor.SAND));
    public static final RegistrySupplier<Block> RED_SNAD = BLOCKS.register("red_snad", () -> new SnadBlock(-5679071, MaterialColor.SAND));
    public static final RegistrySupplier<Block> SOUL_SNAD = BLOCKS.register("soul_snad", SoulSnadBlock::new);

    public static void init() {
        BLOCKS.register();
    }
}
