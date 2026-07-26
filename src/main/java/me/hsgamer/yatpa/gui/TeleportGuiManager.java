package me.hsgamer.yatpa.gui;

import io.github.projectunified.craftux.common.Position;
import io.github.projectunified.craftux.simple.SimpleButton;
import io.github.projectunified.craftux.simple.SimpleButtonMask;
import io.github.projectunified.craftux.spigot.SpigotInventoryUI;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.yatpa.YATPA;
import me.hsgamer.yatpa.config.MessageConfig;
import me.hsgamer.yatpa.request.RequestEntry;
import me.hsgamer.yatpa.request.RequestType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class TeleportGuiManager {
    private static final int PAGE_SIZE = 45;
    private static final int PREVIOUS_SLOT = 45;
    private static final int BACK_SLOT = 49;
    private static final int NEXT_SLOT = 53;
    private final YATPA plugin;

    public TeleportGuiManager(YATPA plugin) {
        this.plugin = plugin;
    }

    public void openTeleportMenu(Player player) {
        MessageConfig messages = plugin.getMessageConfig();
        SimpleButtonMask mask = new SimpleButtonMask();
        mask.setButton(position(2), new SimpleButton(
                item(Material.ENDER_PEARL, messages.getGuiTpaName(), messages.getGuiTpaLore()),
                event -> openPlayers(player, RequestType.NORMAL, 0)
        ));
        mask.setButton(position(6), new SimpleButton(
                item(Material.COMPASS, messages.getGuiTpaHereName(), messages.getGuiTpaHereLore()),
                event -> openPlayers(player, RequestType.HERE, 0)
        ));
        open(player, messages.getGuiTeleportMenuTitle(), 9, mask);
    }

    public void openAcceptRequests(Player player) {
        openRequests(player, RequestAction.ACCEPT, 0);
    }

    public void openDenyRequests(Player player) {
        openRequests(player, RequestAction.DENY, 0);
    }

    private void openPlayers(Player viewer, RequestType requestType, int requestedPage) {
        MessageConfig messages = plugin.getMessageConfig();
        List<Player> players = new ArrayList<>();
        Collection<? extends Player> onlinePlayers = plugin.getServer().getOnlinePlayers();
        for (Player player : onlinePlayers) {
            if (!player.getUniqueId().equals(viewer.getUniqueId())) {
                players.add(player);
            }
        }
        players.sort(Comparator.comparing(Player::getName, String.CASE_INSENSITIVE_ORDER));

        int page = clampPage(requestedPage, players.size());
        SimpleButtonMask mask = new SimpleButtonMask();
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, players.size());
        for (int index = start; index < end; index++) {
            Player target = players.get(index);
            String command = commandFor(requestType);
            mask.setButton(position(index - start), new SimpleButton(
                    playerHead(
                            target,
                            replace(messages.getGuiPlayerName(), "{player}", target.getName()),
                            singletonLore(replace(messages.getGuiPlayerLore(), "{command}", command))
                    ),
                    event -> sendRequest(viewer, target.getUniqueId(), command)
            ));
        }

        if (players.isEmpty()) {
            mask.setButton(position(22), new SimpleButton(item(Material.BARRIER, messages.getGuiNoPlayersName())));
        }
        addPageControls(
                mask,
                page,
                players.size(),
                messages.getGuiBackName(),
                () -> openPlayers(viewer, requestType, page - 1),
                () -> openTeleportMenu(viewer),
                () -> openPlayers(viewer, requestType, page + 1)
        );
        open(viewer, messages.getGuiPlayerListTitle(), 54, mask);
    }

    private void openRequests(Player viewer, RequestAction action, int requestedPage) {
        MessageConfig messages = plugin.getMessageConfig();
        List<RequestEntry> requests = plugin.getRequestManager().getRequests(viewer.getUniqueId());
        int page = clampPage(requestedPage, requests.size());
        SimpleButtonMask mask = new SimpleButtonMask();
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, requests.size());
        for (int index = start; index < end; index++) {
            RequestEntry request = requests.get(index);
            OfflinePlayer requester = Bukkit.getOfflinePlayer(request.requester);
            String name = playerName(requester);
            String description = request.type == RequestType.NORMAL
                    ? messages.getGuiRequestNormalLore()
                    : messages.getGuiRequestHereLore();
            mask.setButton(position(index - start), new SimpleButton(
                    playerHead(
                            requester,
                            replace(messages.getGuiPlayerName(), "{player}", name),
                            Arrays.asList(description, messages.getGuiRequestClickLore())
                    ),
                    event -> openConfirmation(viewer, action, request.requester, page)
            ));
        }

        if (requests.isEmpty()) {
            mask.setButton(position(22), new SimpleButton(item(Material.BARRIER, messages.getGuiNoRequestsName())));
        }
        addPageControls(
                mask,
                page,
                requests.size(),
                messages.getGuiCloseName(),
                () -> openRequests(viewer, action, page - 1),
                viewer::closeInventory,
                () -> openRequests(viewer, action, page + 1)
        );
        String title = action == RequestAction.ACCEPT
                ? messages.getGuiAcceptListTitle()
                : messages.getGuiDenyListTitle();
        open(viewer, title, 54, mask);
    }

    private void openConfirmation(Player viewer, RequestAction action, UUID requesterId, int returnPage) {
        MessageConfig messages = plugin.getMessageConfig();
        OfflinePlayer requester = Bukkit.getOfflinePlayer(requesterId);
        String title = action == RequestAction.ACCEPT
                ? messages.getGuiConfirmAcceptTitle()
                : messages.getGuiConfirmDenyTitle();
        String lore = action == RequestAction.ACCEPT
                ? messages.getGuiConfirmAcceptLore()
                : messages.getGuiConfirmDenyLore();
        SimpleButtonMask mask = new SimpleButtonMask();
        mask.setButton(position(13), new SimpleButton(playerHead(
                requester,
                replace(messages.getGuiPlayerName(), "{player}", playerName(requester)),
                singletonLore(lore)
        )));
        mask.setButton(position(11), new SimpleButton(
                item(Material.LIME_WOOL, messages.getGuiConfirmName()),
                event -> answerRequest(viewer, action, requesterId)
        ));
        mask.setButton(position(15), new SimpleButton(
                item(Material.RED_WOOL, messages.getGuiCancelName()),
                event -> openRequests(viewer, action, returnPage)
        ));
        open(viewer, title, 27, mask);
    }

    private void sendRequest(Player sender, UUID targetId, String command) {
        Player target = plugin.getServer().getPlayer(targetId);
        sender.closeInventory();
        if (target == null) {
            MessageUtils.sendMessage(sender, plugin.getMessageConfig().getTeleportOffline());
            return;
        }
        plugin.getServer().dispatchCommand(sender, command + " " + target.getName());
    }

    private void answerRequest(Player player, RequestAction action, UUID requesterId) {
        Player requester = plugin.getServer().getPlayer(requesterId);
        player.closeInventory();
        if (requester == null) {
            MessageUtils.sendMessage(player, plugin.getMessageConfig().getTeleportOffline());
            return;
        }
        plugin.getServer().dispatchCommand(player, action.command + " " + requester.getName());
    }

    private void addPageControls(
            SimpleButtonMask mask,
            int page,
            int entryCount,
            String backName,
            Runnable previousAction,
            Runnable backAction,
            Runnable nextAction
    ) {
        MessageConfig messages = plugin.getMessageConfig();
        if (page > 0) {
            mask.setButton(position(PREVIOUS_SLOT), new SimpleButton(
                    item(Material.ARROW, messages.getGuiPreviousName()),
                    event -> previousAction.run()
            ));
        }
        mask.setButton(position(BACK_SLOT), new SimpleButton(
                item(Material.BARRIER, backName),
                event -> backAction.run()
        ));
        if ((page + 1) * PAGE_SIZE < entryCount) {
            mask.setButton(position(NEXT_SLOT), new SimpleButton(
                    item(Material.ARROW, messages.getGuiNextName()),
                    event -> nextAction.run()
            ));
        }
    }

    private static void open(Player player, String title, int size, SimpleButtonMask mask) {
        SpigotInventoryUI ui = new SpigotInventoryUI(player.getUniqueId(), color(title), size);
        ui.setMask(mask);
        ui.update();
        ui.open(player);
    }

    private static Position position(int slot) {
        return Position.of(slot % 9, slot / 9);
    }

    private static int clampPage(int requestedPage, int entryCount) {
        int lastPage = entryCount == 0 ? 0 : (entryCount - 1) / PAGE_SIZE;
        return Math.max(0, Math.min(requestedPage, lastPage));
    }

    private static String commandFor(RequestType requestType) {
        return requestType == RequestType.HERE ? "tpahere" : "tpa";
    }

    private static ItemStack item(Material material, String name, String... lore) {
        return item(material, name, Arrays.asList(lore));
    }

    private static ItemStack item(Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(color(name));
            if (!lore.isEmpty()) {
                meta.setLore(color(lore));
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    private static ItemStack playerHead(OfflinePlayer player, String name, List<String> lore) {
        ItemStack head = item(Material.PLAYER_HEAD, name, lore);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        if (meta != null) {
            meta.setOwningPlayer(player);
            head.setItemMeta(meta);
        }
        return head;
    }

    private static String playerName(OfflinePlayer player) {
        String name = player.getName();
        return name == null ? player.getUniqueId().toString() : name;
    }

    private static String replace(String value, String placeholder, String replacement) {
        return value.replace(placeholder, replacement);
    }

    private static List<String> singletonLore(String value) {
        return Arrays.asList(value);
    }

    private static String color(String value) {
        return ChatColor.translateAlternateColorCodes('&', value);
    }

    private static List<String> color(List<String> values) {
        List<String> colored = new ArrayList<>();
        for (String value : values) {
            colored.add(color(value));
        }
        return colored;
    }

    private enum RequestAction {
        ACCEPT("tpaccept"),
        DENY("tpdeny");

        private final String command;

        RequestAction(String command) {
            this.command = command;
        }
    }
}
