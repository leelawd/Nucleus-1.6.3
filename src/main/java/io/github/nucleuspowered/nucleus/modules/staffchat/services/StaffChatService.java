/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.staffchat.services;

import io.github.nucleuspowered.nucleus.api.chat.NucleusChatChannel;
import io.github.nucleuspowered.nucleus.api.service.NucleusStaffChatService;
import io.github.nucleuspowered.nucleus.internal.annotations.APIService;
import io.github.nucleuspowered.nucleus.internal.interfaces.ServiceBase;
import io.github.nucleuspowered.nucleus.modules.staffchat.StaffChatMessageChannel;

@APIService(NucleusStaffChatService.class)
public class StaffChatService implements NucleusStaffChatService, ServiceBase {

    @Override
    public NucleusChatChannel.StaffChat getStaffChat() {
        return StaffChatMessageChannel.getInstance();
    }
}
