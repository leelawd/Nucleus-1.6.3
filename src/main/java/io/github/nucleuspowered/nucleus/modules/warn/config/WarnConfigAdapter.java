/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.warn.config;

import io.github.nucleuspowered.nucleus.internal.qsml.NucleusConfigAdapter;

public class WarnConfigAdapter extends NucleusConfigAdapter.StandardWithSimpleDefault<WarnConfig> {

    public WarnConfigAdapter() {
        super(WarnConfig.class);
    }
}
