/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.afk.listeners;

import io.github.nucleuspowered.nucleus.internal.interfaces.ListenerBase;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.MoveEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.world.World;

public class AFKRotationOnlyListener extends AbstractAFKListener implements ListenerBase.Conditional {

    @Listener(order = Order.LAST)
    public void onPlayerMove(final MoveEntityEvent event, @Root Player player,
            @Getter("getFromTransform") Transform<World> from,
            @Getter("getToTransform") Transform<World> to) {
        if (!from.getRotation().equals(to.getRotation())) {
            update(player);
        }
    }

    @Override
    public boolean shouldEnable() {
        return getTriggerConfigEntry(t -> !t.isOnMovement() && t.isOnRotation());
    }

}
