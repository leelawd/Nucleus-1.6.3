/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.configurate.datatypes;

import io.github.nucleuspowered.nucleus.dataservices.modular.ModularUserService;
import io.github.nucleuspowered.nucleus.modules.core.datamodules.CoreUserDataModule;
import io.github.nucleuspowered.nucleus.modules.jail.data.JailData;
import io.github.nucleuspowered.nucleus.modules.jail.datamodules.JailUserDataModule;
import io.github.nucleuspowered.nucleus.modules.mute.datamodules.MuteUserDataModule;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.Optional;

import javax.annotation.Nullable;

@ConfigSerializable
public class UserCacheDataNode {

    @Setting
    @Nullable
    private String ipAddress;

    @Setting
    @Nullable
    private String jail = null;

    @Setting
    private boolean isMuted = false;

    public UserCacheDataNode() {
        // ignored - for Configurate
    }

    public UserCacheDataNode(ModularUserService x) {
        set(x);
    }

    public void set(ModularUserService x) {
        this.ipAddress = x.get(CoreUserDataModule.class).getLastIp().map(y -> y.replace("/", "")).orElse(null);
        this.jail = x.get(JailUserDataModule.class).getJailData().map(JailData::getJailName).orElse(null);
        this.isMuted = x.get(MuteUserDataModule.class).getMuteData().isPresent();
    }

    public Optional<String> getIpAddress() {
        return Optional.ofNullable(this.ipAddress);
    }

    public boolean isJailed() {
        return getJailName().isPresent();
    }

    public Optional<String> getJailName() {
        return Optional.ofNullable(this.jail);
    }

    public boolean isMuted() {
        return this.isMuted;
    }
}
