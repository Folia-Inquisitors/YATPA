package me.hsgamer.yatpa.config;

import com.cryptomorin.xseries.XPotion;
import me.hsgamer.hscore.config.annotation.ConfigPath;
import me.hsgamer.yatpa.config.converter.PotionEffectListConverter;

import java.util.Collections;
import java.util.List;

public interface MainConfig {
    @ConfigPath({"teleport", "delay"})
    default int teleportDelay() {
        return 0;
    }

    default long teleportDelayTicks() {
        return teleportDelay() * 20L;
    }

    default boolean hasTeleportDelay() {
        return teleportDelay() > 0;
    }

    @ConfigPath({"teleport", "cooldown"})
    default int teleportCooldown() {
        return 0;
    }

    default long teleportCooldownMillis() {
        return teleportCooldown() * 1000L;
    }

    @ConfigPath(value = {"teleport", "effect", "value"}, converter = PotionEffectListConverter.class)
    default List<XPotion.Effect> teleportEffect() {
        return Collections.singletonList(
                new XPotion.Effect(XPotion.DAMAGE_RESISTANCE.buildPotionEffect(1, 5), 100)
        );
    }

    @ConfigPath({"teleport", "effect", "period"})
    default int teleportEffectPeriod() {
        return 5;
    }
}
