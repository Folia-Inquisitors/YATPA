package me.hsgamer.yatpa.config;

import me.hsgamer.hscore.config.annotation.ConfigPath;

import java.util.Arrays;
import java.util.List;

public interface GuiMessageConfig {
    @ConfigPath({"gui", "title", "teleport-menu"})
    default String getGuiTeleportMenuTitle() {
        return "&3Teleport Menu";
    }

    @ConfigPath({"gui", "title", "player-list"})
    default String getGuiPlayerListTitle() {
        return "&3Choose a Player";
    }

    @ConfigPath({"gui", "title", "accept-list"})
    default String getGuiAcceptListTitle() {
        return "&3Accept a Request";
    }

    @ConfigPath({"gui", "title", "deny-list"})
    default String getGuiDenyListTitle() {
        return "&3Deny a Request";
    }

    @ConfigPath({"gui", "title", "confirm-accept"})
    default String getGuiConfirmAcceptTitle() {
        return "&3Confirm accept";
    }

    @ConfigPath({"gui", "title", "confirm-deny"})
    default String getGuiConfirmDenyTitle() {
        return "&3Confirm deny";
    }

    @ConfigPath({"gui", "item", "tpa", "name"})
    default String getGuiTpaName() {
        return "&aTPA";
    }

    @ConfigPath({"gui", "item", "tpa", "lore"})
    default List<String> getGuiTpaLore() {
        return Arrays.asList(
                "&7Teleport yourself to another player.",
                "&eClick to choose a player."
        );
    }

    @ConfigPath({"gui", "item", "tpahere", "name"})
    default String getGuiTpaHereName() {
        return "&bTPA Here";
    }

    @ConfigPath({"gui", "item", "tpahere", "lore"})
    default List<String> getGuiTpaHereLore() {
        return Arrays.asList(
                "&7Ask another player to teleport to you.",
                "&eClick to choose a player."
        );
    }

    @ConfigPath({"gui", "item", "player", "name"})
    default String getGuiPlayerName() {
        return "&b{player}";
    }

    @ConfigPath({"gui", "item", "player", "lore"})
    default String getGuiPlayerLore() {
        return "&eClick to send /{command}.";
    }

    @ConfigPath({"gui", "item", "request", "normal-lore"})
    default String getGuiRequestNormalLore() {
        return "&7Wants to teleport to you.";
    }

    @ConfigPath({"gui", "item", "request", "here-lore"})
    default String getGuiRequestHereLore() {
        return "&7Wants you to teleport to them.";
    }

    @ConfigPath({"gui", "item", "request", "click-lore"})
    default String getGuiRequestClickLore() {
        return "&eClick to continue.";
    }

    @ConfigPath({"gui", "item", "confirmation", "accept-lore"})
    default String getGuiConfirmAcceptLore() {
        return "&7Confirm that you want to accept this request.";
    }

    @ConfigPath({"gui", "item", "confirmation", "deny-lore"})
    default String getGuiConfirmDenyLore() {
        return "&7Confirm that you want to deny this request.";
    }

    @ConfigPath({"gui", "item", "no-players"})
    default String getGuiNoPlayersName() {
        return "&cNo other players are online";
    }

    @ConfigPath({"gui", "item", "no-requests"})
    default String getGuiNoRequestsName() {
        return "&cNo pending teleport requests";
    }

    @ConfigPath({"gui", "item", "previous"})
    default String getGuiPreviousName() {
        return "&ePrevious Page";
    }

    @ConfigPath({"gui", "item", "next"})
    default String getGuiNextName() {
        return "&eNext Page";
    }

    @ConfigPath({"gui", "item", "back"})
    default String getGuiBackName() {
        return "&cBack to teleport menu";
    }

    @ConfigPath({"gui", "item", "close"})
    default String getGuiCloseName() {
        return "&cClose";
    }

    @ConfigPath({"gui", "item", "confirm"})
    default String getGuiConfirmName() {
        return "&aConfirm";
    }

    @ConfigPath({"gui", "item", "cancel"})
    default String getGuiCancelName() {
        return "&cCancel";
    }
}
