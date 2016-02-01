package io.github.leonardosnt.testy;

import java.util.concurrent.RecursiveAction;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.bktlib.BktLib;
import io.github.bktlib.command.CommandManager;
import io.github.bktlib.world.Locations;

public class TestyPlugin extends JavaPlugin
{
	@Override
	public void onEnable()
	{
		CommandManager cmdManager = CommandManager.newInstance( this );
		cmdManager.registerAll();
	}
}
