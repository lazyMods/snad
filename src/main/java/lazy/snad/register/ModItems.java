package lazy.snad.register;

import lazy.snad.Snad;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Snad.MOD_ID);
    public static final RegistryObject<Item> SNAD = ITEMS.register("snad", () -> new BlockItem(ModBlocks.SNAD.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_SNAD = ITEMS.register("red_snad", () -> new BlockItem(ModBlocks.RED_SNAD.get(), new Item.Properties()));
    public static final RegistryObject<Item> SOUL_SNAD = ITEMS.register("soul_snad", () -> new BlockItem(ModBlocks.SOUL_SNAD.get(), new Item.Properties()));

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
