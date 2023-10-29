package lazy.snad;

import lazy.snad.register.ModBlocks;
import lazy.snad.register.ModConfigs;
import lazy.snad.register.ModItems;
import lazy.snad.register.ModTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("snad")
public class Snad {

    public static final String MOD_ID = "snad";

    public Snad() {
        ModConfigs.registerAndLoadConfig();
        ModBlocks.init();
        ModItems.init();
        ModTags.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(Snad::onCreativeTabEvent);
    }

    @SubscribeEvent
    public static void onCreativeTabEvent(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS)) {
            event.getEntries().put(new ItemStack(ModBlocks.SNAD.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().put(new ItemStack(ModBlocks.RED_SNAD.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().put(new ItemStack(ModBlocks.SOUL_SNAD.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
