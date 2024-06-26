package de.locoqlix.kitpvpv3;

import de.locoqlix.kitpvpv3.commands.CommandHandler;
import de.locoqlix.kitpvpv3.listener.EventHandler;
import de.locoqlix.kitpvpv3.utils.Messages;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class KitPVP extends JavaPlugin {

    public static FileConfiguration config;
    public HashMap<Player, String> playerKit;
    static LuckPerms luckPerms;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        playerKit = new HashMap<Player, String>();
        luckPerms = LuckPermsProvider.get();

        Messages.debug(config.getBoolean("debug"));

        // Load modules
        new CommandHandler(this);
        new EventHandler(this);

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            LuckPerms api = provider.getProvider();

        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void addPermission(UUID userUuid, String permission) {
        // Load, modify, then save
        luckPerms.getUserManager().modifyUser(userUuid, user -> {
            // Add the permission
            user.data().add(Node.builder(permission).build());
        });
    }


}
