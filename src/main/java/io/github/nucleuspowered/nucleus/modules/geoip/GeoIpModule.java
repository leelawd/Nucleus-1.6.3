/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.geoip;

import io.github.nucleuspowered.nucleus.internal.qsml.module.ConfigurableModule;
import io.github.nucleuspowered.nucleus.modules.geoip.config.GeoIpConfigAdapter;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import uk.co.drnaylor.quickstart.annotations.ModuleData;
import uk.co.drnaylor.quickstart.enums.LoadingStatus;

@ModuleData(id = GeoIpModule.ID, name = "Geo IP", status = LoadingStatus.DISABLED)
public class GeoIpModule extends ConfigurableModule<GeoIpConfigAdapter> {

    public static final String ID = "geo-ip";

    @Override public GeoIpConfigAdapter createAdapter() {
        return new GeoIpConfigAdapter();
    }

    @Override protected void performPreTasks() throws Exception {
        ConsoleSource source = Sponge.getServer().getConsole();
        source.sendMessage(Text.of(TextColors.RED, "WARNING: Deprecation of GeoIP module"));
        source.sendMessage(Text.of(TextColors.RED, "-------------------------------------"));
        source.sendMessage(Text.of(TextColors.RED, "In Nucleus 1.7, the GeoIP module will be separated out into its own plugin, Heisenberg."));
        source.sendMessage(Text.of(TextColors.RED, "This will be available on Ore as Nucleus Heisenberg."));
        super.performPreTasks();
    }
}
