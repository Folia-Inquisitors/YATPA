package me.hsgamer.yatpa;

import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Permissions {
    public static final Permission TPA = new Permission("yatpa.tpa", PermissionDefault.TRUE);
    public static final Permission TPA_HERE = new Permission("yatpa.tpahere", PermissionDefault.TRUE);
    public static final Permission TPA_ACCEPT = new Permission("yatpa.tpaccept", PermissionDefault.TRUE);
    public static final Permission TPA_DENY = new Permission("yatpa.tpdeny", PermissionDefault.TRUE);
}
