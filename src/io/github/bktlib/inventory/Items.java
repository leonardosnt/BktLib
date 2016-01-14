package io.github.bktlib.inventory;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

public class Items
{
	private Items() {}
	
	/**
	 * Modifica o displayName do item
	 * 
	 * @param item Item que deseja modificar.
	 * @param name Nome desejado
	 */
	public static void setDisplayName( ItemStack item, String name )
	{
		modifyMeta( item, meta -> meta.setDisplayName( 
				ChatColor.translateAlternateColorCodes( '&', name)
		) );
	}
	
	/**
	 * Modifica o lore do determinado item.
	 * 
	 * @param item Item que deseja modificar.
	 * @param lore Lista de linhas desejadas.
	 */
	public static void setLore( ItemStack item, String ... lore )
	{
		modifyMeta( item, meta -> 
			meta.setLore( Arrays.stream( lore )
                		  .map( line -> ChatColor.translateAlternateColorCodes( '&', line ) )
                		  .collect( Collectors.toList() ) )
		);
	}
	
	
	/**
	 * Não gosto de duplicar código.
	 */
	private static void modifyMeta( ItemStack item, 
			Consumer<ItemMeta> metaConsumer )
	{
		ItemMeta meta = item.getItemMeta();
		metaConsumer.accept( meta );
		item.setItemMeta( meta );
	}
}
