package lazy.snad.data;

import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lazy.snad.Snad;
import lazy.snad.register.ModBlocks;
import lazy.snad.register.ModItems;
import lazy.snad.register.ModTags;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.LogManager;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        @NotNull final DataGenerator generator = event.getGenerator();
        if (event.includeServer()) {
            generator.addProvider(new BlockTagGen(generator, event.getExistingFileHelper()));
            generator.addProvider(new RecipeGen(generator));
            generator.addProvider(new LootTableGen(generator));
        }
    }

    private static class BlockTagGen extends BlockTagsProvider {
        private BlockTagGen(DataGenerator generator, @Nullable ExistingFileHelper existingFileHelper) {
            super(generator, Snad.MOD_ID, existingFileHelper);
        }

        @Override
        protected void addTags() {
            this.tag(BlockTags.BAMBOO_PLANTABLE_ON).addTag(ModTags.SNAD).addTag(Tags.Blocks.SAND).add(Blocks.BAMBOO, Blocks.BAMBOO_SAPLING,
                    Blocks.GRAVEL, Blocks.DIRT, Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.COARSE_DIRT, Blocks.MYCELIUM);
            this.tag(BlockTags.SOUL_SPEED_BLOCKS).add(ModBlocks.SOUL_SNAD.get());
            this.tag(Tags.Blocks.SAND_COLORLESS).add(ModBlocks.SNAD.get());
            this.tag(Tags.Blocks.SAND_RED).add(ModBlocks.RED_SNAD.get());
            this.tag(ModTags.SNAD).add(ModBlocks.SNAD.get(), ModBlocks.RED_SNAD.get());
            this.tag(ModTags.VALID_PLANT).add(Blocks.SUGAR_CANE, Blocks.CACTUS, Blocks.BAMBOO, Blocks.BAMBOO_SAPLING, Blocks.NETHER_WART);
        }
    }

    private static class RecipeGen extends RecipeProvider {
        private RecipeGen(DataGenerator generator) {
            super(generator);
        }

        @Override
        protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
            ShapedRecipeBuilder.shaped(ModItems.SNAD.get())
                    .pattern("S")
                    .pattern("S")
                    .define('S', Blocks.SAND)
                    .unlockedBy("has_sand", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.SAND))
                    .save(consumer, new ResourceLocation(Snad.MOD_ID, "snad"));
            ShapedRecipeBuilder.shaped(ModItems.RED_SNAD.get())
                    .pattern("S")
                    .pattern("S")
                    .define('S', Blocks.RED_SAND)
                    .unlockedBy("has_sand", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.RED_SAND))
                    .save(consumer, new ResourceLocation(Snad.MOD_ID, "red_snad"));
            ShapedRecipeBuilder.shaped(ModItems.SOUL_SNAD.get())
                    .pattern("S")
                    .pattern("S")
                    .define('S', Blocks.SOUL_SAND)
                    .unlockedBy("has_sand", InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.SOUL_SAND))
                    .save(consumer, new ResourceLocation(Snad.MOD_ID, "soul_snad"));
        }
    }

    private static class LootTableGen implements DataProvider {
        @Nonnull private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
        @Nonnull protected final Map<ResourceLocation, LootTable> lootTables = new HashMap<>();
        @Nonnull private final Logger logger = LoggerFactory.getLogger(this.getClass());
        @Nonnull private final DataGenerator generator;
        @Nonnull private final String modId;

        private LootTableGen(DataGenerator generator) {
            this.generator = generator;
            this.modId = Snad.MOD_ID;
        }

        private void createTables() {
            registerSelfDrop(ModBlocks.SNAD.get());
            registerSelfDrop(ModBlocks.RED_SNAD.get());
            registerSelfDrop(ModBlocks.SOUL_SNAD.get());
        }

        private static LootPool.Builder singleItem(ItemLike item) {
            return LootPool.lootPool().when(ExplosionCondition.survivesExplosion()).setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item));
        }

        @Override
        public void run(@NotNull HashCache cache) {
            lootTables.clear();
            @Nonnull final Path outFolder = generator.getOutputFolder();

            createTables();

            @Nonnull final ValidationContext validator = new ValidationContext(LootContextParamSets.ALL_PARAMS,
                    function -> null, lootTables::get);
            lootTables.forEach((name, table) -> LootTables.validate(validator, name, table));
            @Nonnull final Multimap<String, String> problems = validator.getProblems();
            if (!problems.isEmpty()) {
                problems.forEach((name, table) -> logger.warn("Found validation problem in " + name + ": " + table));
                throw new IllegalStateException("Failed to validate loot tables, see logs");
            } else {
                lootTables.forEach((name, table) -> {
                    @Nonnull final Path out = outFolder.resolve("data/" + name.getNamespace() + "/loot_tables/blocks/" + name.getPath() + ".json");

                    try {
                        DataProvider.save(GSON, cache, LootTables.serialize(table), out);
                    } catch (IOException e) {
                        logger.error("Couldn't save loot table " + out);
                        logger.error(Arrays.toString(e.getStackTrace()));
                    }
                });
            }
        }

        @NotNull
        @Override
        public String getName() {
            return modId + " LootTables";
        }

        protected void register(@Nonnull final Block block, @Nonnull final LootPool.Builder... pools) {
            @Nonnull final LootTable.Builder builder = LootTable.lootTable();
            for (@Nonnull final LootPool.Builder pool : pools) {
                builder.withPool(pool);
            }
            register(block, builder);
        }

        protected void registerSelfDrop(@Nonnull final Block block) {
            register(block, singleItem(block));
        }

        private void register(@Nonnull final Block block, @Nonnull final LootTable.Builder table) {
            @Nullable final ResourceLocation resourceLocation = block.getRegistryName();
            if (resourceLocation == null)
                return;
            register(resourceLocation, table);
        }

        private void register(@Nonnull final ResourceLocation registryName, @Nonnull final LootTable.Builder table) {
            if (lootTables.put(toTableLoc(registryName), table.setParamSet(LootContextParamSets.BLOCK).build()) != null) {
                throw new IllegalStateException("Duplicate loot table: " + table);
            }
        }

        @Nonnull
        private ResourceLocation toTableLoc(@Nonnull final ResourceLocation registryName) {
            return new ResourceLocation(registryName.getNamespace(), "blocks/" + registryName.getPath());
        }
    }
}
