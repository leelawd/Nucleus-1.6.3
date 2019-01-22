/*
 * This file is part of Nucleus, licensed under the MIT License (MIT). See the LICENSE.txt file
 * at the root of this project for more details.
 */
package io.github.nucleuspowered.nucleus.modules.chat.config;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import uk.co.drnaylor.quickstart.config.NoMergeIfPresent;

import java.util.HashMap;
import java.util.Map;

@ConfigSerializable
public class TemplateConfig {

    @Setting(value = "use-group-templates", comment = "config.chat.useGroupTemplates")
    private boolean useGroupTemplates = true;

    @Setting(value = "check-permission-groups", comment = "config.chat.check-permission-groups")
    private boolean checkPermissionGroups = true;

    @Setting(value = "default", comment = "config.chat.default-template")
    private ChatTemplateConfig defaultTemplate = new ChatTemplateConfig();

    @NoMergeIfPresent
    @Setting(value = "group-templates", comment = "config.chat.group-templates")
    private Map<String, WeightedChatTemplateConfig> groupTemplates = new HashMap<String, WeightedChatTemplateConfig>() {{
        // We don't want this affecting the default group, but we need an example.
        put("DefaultTemplate", new WeightedChatTemplateConfig());
    }};

    public ChatTemplateConfig getDefaultTemplate() {
        return this.defaultTemplate;
    }

    public Map<String, WeightedChatTemplateConfig> getGroupTemplates() {
        return this.groupTemplates;
    }

    public boolean isUseGroupTemplates() {
        return this.useGroupTemplates;
    }

    public boolean isCheckPermissionGroups() {
        return this.checkPermissionGroups;
    }
}
