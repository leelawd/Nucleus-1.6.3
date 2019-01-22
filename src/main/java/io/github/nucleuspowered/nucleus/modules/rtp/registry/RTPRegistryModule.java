/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.rtp.registry;

import io.github.nucleuspowered.nucleus.api.rtp.RTPKernel;
import io.github.nucleuspowered.nucleus.api.rtp.RTPKernels;
import io.github.nucleuspowered.nucleus.internal.annotations.Registry;
import io.github.nucleuspowered.nucleus.internal.registry.NucleusRegistryModule;
import io.github.nucleuspowered.nucleus.modules.rtp.kernels.AroundPlayerAndSurfaceKernel;
import io.github.nucleuspowered.nucleus.modules.rtp.kernels.AroundPlayerKernel;
import io.github.nucleuspowered.nucleus.modules.rtp.kernels.DefaultKernel;
import io.github.nucleuspowered.nucleus.modules.rtp.kernels.SurfaceKernel;

import javax.inject.Singleton;

@Singleton
@Registry(RTPKernels.class)
public class RTPRegistryModule extends NucleusRegistryModule<RTPKernel> {

    private static RTPRegistryModule INSTANCE;

    public static RTPRegistryModule getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Instance is not yet initialised");
        }
        return INSTANCE;
    }

    public RTPRegistryModule() {
        if (INSTANCE != null) {
            throw new IllegalStateException("Singleton already exists");
        }
        INSTANCE = this;
    }

    @Override
    public Class<RTPKernel> catalogClass() {
        return RTPKernel.class;
    }

    @Override
    public void registerDefaults() {
        this.registerAdditionalCatalog(DefaultKernel.INSTANCE);
        this.registerAdditionalCatalog(new AroundPlayerAndSurfaceKernel());
        this.registerAdditionalCatalog(new AroundPlayerKernel());
        this.registerAdditionalCatalog(new SurfaceKernel());
    }
}
