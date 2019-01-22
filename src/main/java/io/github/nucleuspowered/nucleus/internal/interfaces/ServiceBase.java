/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.internal.interfaces;

import io.github.nucleuspowered.nucleus.annotationprocessor.Store;
import io.github.nucleuspowered.nucleus.internal.Constants;
import io.github.nucleuspowered.nucleus.internal.annotations.EntryPoint;
import org.spongepowered.api.util.annotation.NonnullByDefault;

/**
 * Marker interface to indicate to the AP that this is a service
 */
@EntryPoint
@NonnullByDefault
@Store(Constants.SERVICE)
public interface ServiceBase {

}
