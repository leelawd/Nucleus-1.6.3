/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.jail.listeners;


import io.github.nucleuspowered.nucleus.Nucleus;
import io.github.nucleuspowered.nucleus.internal.interfaces.ListenerBase;
import io.github.nucleuspowered.nucleus.modules.jail.JailModule;
import io.github.nucleuspowered.nucleus.modules.jail.config.JailConfig;
import io.github.nucleuspowered.nucleus.modules.jail.config.JailConfigAdapter;
import io.github.nucleuspowered.nucleus.modules.jail.data.JailData;
import io.github.nucleuspowered.nucleus.modules.jail.datamodules.JailUserDataModule;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

public class LogoutJailListener implements ListenerBase.Conditional {

    @Listener
    public void onLogout(ClientConnectionEvent.Disconnect event, @Getter("getTargetEntity") Player player) {
        Nucleus.getNucleus().getUserDataManager().get(player).ifPresent(mus -> {
            Optional<JailData> ojd = mus.get(JailUserDataModule.class).getJailData();
            ojd.ifPresent(jailData -> {
                Optional<Instant> end = jailData.getEndTimestamp();
                end.ifPresent(instant -> jailData.setTimeFromNextLogin(Duration.between(Instant.now(), instant)));
                mus.get(JailUserDataModule.class).setJailData(jailData);
            });
        });
    }

    @Override public boolean shouldEnable() {
        return Nucleus.getNucleus().getConfigValue(JailModule.ID, JailConfigAdapter.class, JailConfig::isJailOnlineOnly).orElse(false);
    }

}
