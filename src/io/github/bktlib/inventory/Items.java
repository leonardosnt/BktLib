/*
 *  Copyright (C) 2016 Leonardosc
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*/

package io.github.bktlib.inventory;

import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import static org.bukkit.ChatColor.*;

public final class Items
{
	/**
	 * Define o nome de exibição do item.
	 * 
	 * @param item Item que deseja modificar.
	 * @param name Nome desejado.
	 */
	public static void setDisplayName( final ItemStack item, final String name )
	{
		consumeMeta( item, meta -> meta.setDisplayName( 
				translateAlternateColorCodes( '&', name)
		) );
	}
	
	/**
	 * Define o lore do item.
	 * 
	 * @param item Item que deseja modificar.
	 * @param lore Lore desejado.
	 */
	public static void setLore( final ItemStack item, final String ... lore )
	{
		consumeMeta( item, meta -> meta.setLore( 
				Stream.of( lore )
    				.map( line -> translateAlternateColorCodes( '&', line ) )
    				.collect( Collectors.toList() ) 
		));
	}

    /**
     * Verifica se o item tem um displayName definido.
     *
     * @param item Item a ser verificado.
     * @return se o item tem um displayName definido.
     */
	public static boolean hasDisplayName( final ItemStack item )
    {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName();
    }

    /**
     * Verifica se o item tem um lore definido.
     *
     * @param item Item a ser verificado.
     * @return se o item tem um lore definido.
     */
    public static boolean hasLore( final ItemStack item )
    {
        return item != null && item.hasItemMeta() && item.getItemMeta().hasLore();
    }
	
	private static void consumeMeta( final ItemStack item, final Consumer<ItemMeta> metaConsumer )
	{
		ItemMeta meta = item.getItemMeta();
		metaConsumer.accept( meta );
		item.setItemMeta( meta );
	}

    private Items()
    {
        throw new UnsupportedOperationException();
    }
}
