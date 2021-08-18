package lazy.snad.register;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import lazy.snad.Snad;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

@SuppressWarnings("unused")
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Snad.MOD_ID, Registry.ITEM_REGISTRY);

    //TODO: This is here for when I figure out how to create data gens

    public static final RegistrySupplier<Item> SNAD = ITEMS.register("snad", () -> new BlockItem(ModBlocks.SNAD.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistrySupplier<Item> RED_SNAD = ITEMS.register("red_snad", () -> new BlockItem(ModBlocks.RED_SNAD.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static void init() {
        ITEMS.register();
    }
}
