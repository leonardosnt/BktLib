package io.github.bktlib.ui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * @author Leonardosc
 */
public class MenuHandler
{
    MenuHandler() {}

    /**
     * @param plugin
     */
    public static void register( final Plugin plugin )
    {
        Bukkit.getServer().getPluginManager()
                .registerEvents( new MenuListener(), plugin );
    }
}
