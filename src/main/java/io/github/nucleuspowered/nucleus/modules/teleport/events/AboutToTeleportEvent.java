/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.teleport.events;

import io.github.nucleuspowered.nucleus.api.events.NucleusTeleportEvent;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.annotation.NonnullByDefault;
import org.spongepowered.api.world.World;

import java.util.Optional;

import javax.annotation.Nullable;

@NonnullByDefault
public class AboutToTeleportEvent extends AbstractEvent implements NucleusTeleportEvent.AboutToTeleport {

    @Nullable private Text cancelMessage;
    private boolean isCancelled = false;

    private final Cause cause;
    private final Transform<World> toTransform;
    private final Player teleportingEntity;

    public AboutToTeleportEvent(Cause cause, Transform<World> toTransform, Player teleportingEntity) {
        this.cause = cause;
        this.toTransform = toTransform;
        this.teleportingEntity = teleportingEntity;
    }

    @Override public Optional<Text> getCancelMessage() {
        return Optional.ofNullable(this.cancelMessage);
    }

    @Override public void setCancelMessage(@Nullable Text message) {
        this.cancelMessage = message;
    }

    @Override public Transform<World> getToTransform() {
        return this.toTransform;
    }

    @Override public Player getTargetEntity() {
        return this.teleportingEntity;
    }

    @Override public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override public Cause getCause() {
        return this.cause;
    }
}
