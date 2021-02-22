package com.lazy.snad;

import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Setup {

    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Snad.MOD_ID);
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Snad.MOD_ID);

    public static RegistryObject<SnadBlock> SNAD_BLOCK = BLOCKS.register("snad", () -> new SnadBlock(-2370656, MaterialColor.SAND));
    public static RegistryObject<SnadBlock> RED_SNAD_BLOCK = BLOCKS.register("red_snad", () -> new SnadBlock(-5679071, MaterialColor.ADOBE));

    public static ITag.INamedTag<Block> CAN_STAND_ON_SNAD;

    public static void init(){
        ITEMS.register("snad", ()-> new BlockItem(SNAD_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
        ITEMS.register("red_snad", ()-> new BlockItem(RED_SNAD_BLOCK.get(), new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));

        CAN_STAND_ON_SNAD = BlockTags.createOptional(new ResourceLocation(Snad.MOD_ID, "can_stand_on_snad"));
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
