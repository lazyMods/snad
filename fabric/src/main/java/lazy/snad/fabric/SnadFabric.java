package lazy.snad.fabric;

import lazy.snad.Snad;
import net.fabricmc.api.ModInitializer;

public class SnadFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Snad.init();
    }
}
