package lazy.snad.config;

import lazy.snad.Snad;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class ConfigScreen {

    public static Screen create(Screen parent) {
        ConfigBuilder configBuilder = ConfigBuilder.create().setParentScreen(parent).setTitle(new TranslatableComponent("config.title"));

        configBuilder.getOrCreateCategory(new TranslatableComponent("config.general")).addEntry(
                configBuilder.entryBuilder()
                        .startIntField(new TranslatableComponent("config.speed"), 2)
                        .setDefaultValue(2)
                        .setSaveConsumer(Snad.configs::setSpeed)
                        .build()
        );

        return configBuilder.setSavingRunnable(() -> {
            try {
                ModConfigs.save(Snad.configs);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Snad.configs = ModConfigs.get();
        }).build();
    }
}
