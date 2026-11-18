package net.luckystudio.cozyhome.compat.everycomp;

import net.luckystudio.cozyhome.CozyHome;
import net.mehvahdjukaar.every_compat.api.ECPlugin;
import net.mehvahdjukaar.every_compat.api.EveryCompatPlugin;
import net.mehvahdjukaar.every_compat.api.PluginEvent;

/**
 * Entry point Every Compat calls before it builds its modules.
 * <ul>
 *     <li>NeoForge finds it through the {@link ECPlugin} annotation.</li>
 *     <li>Fabric finds it through the "everycomp" entrypoint in fabric.mod.json.</li>
 * </ul>
 * Neither loader touches this class unless Every Compat is installed, so it's an optional integration.
 */
@ECPlugin
public class CozyHomeEveryCompatPlugin implements EveryCompatPlugin {

    @Override
    public void register(PluginEvent registrar) {
        registrar.addModule(CozyHome.MOD_ID, () -> CozyHomeEveryCompatModule.class);
    }
}
