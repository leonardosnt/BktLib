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

package io.github.bktlib.item.builders.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import io.github.bktlib.item.builders.ItemBuilder;

public class ItemBuilderImpl implements ItemBuilder
{
	protected ItemStack item;
	
	public ItemBuilderImpl()
	{
		item = new ItemStack( Material.AIR );
	}

	@Override
	public ItemBuilder type( Material mat )
	{
		item.setType( mat );
		
		return this;
	}

	@Override
	public ItemBuilder durability( int durability )
	{
		Preconditions.checkArgument( durability <= Short.MAX_VALUE,
				"durability must less or equals than %s (Short.MAX_VALUE)", Short.MAX_VALUE );
		
		item.setDurability( (short) (item.getType().getMaxDurability() - durability) );
		
		return this;
	}

	@Override
	public ItemBuilder maxDurability() 
	{
		return durability( item.getType().getMaxDurability() );
	}

	@Override
	public ItemBuilder amount( int amount )
	{
		Preconditions.checkArgument( amount > 0, "amount must be positive");
		
		item.setAmount( amount );
		
		return this;
	}

	@Override
	public ItemBuilder name( String displayName )
	{
		Preconditions.checkNotNull( displayName, "displayName cannot be null");
		
		modifyMeta( meta -> meta.setDisplayName( displayName ) );
		
		return this;
	}

	@Override
	public ItemBuilder lore( String... lines )
	{
		Preconditions.checkNotNull( lines, "lines cannot be null");
		
		if ( lines.length == 0 ) 
			return this;
		
		modifyMeta( meta -> 
		{
			ArrayList<String> lore = Lists.newArrayList();
			List<String> currentLore = meta.getLore();
			
			if ( currentLore != null && !currentLore.isEmpty() )
				lore.addAll( currentLore );
			
			lore.addAll( Arrays.asList( lines ) );

			meta.setLore( lore );
		});
		
		return this;
	}

	@Override
	public ItemBuilder enchant( Enchantment ench, int level )
	{
		Preconditions.checkArgument( level < 0, "level must be positive");
		
		item.addUnsafeEnchantment( ench, level );
		
		return this;
	}
	
	@Override
	public ItemBuilder data( byte data )
	{
		//TODO: implementar
		return this;
	}

	@Override
	public ItemStack build()
	{
		return item;
	}

	private void modifyMeta( Consumer<ItemMeta> consumer )
	{
		ItemMeta meta = item.getItemMeta();
		consumer.accept( meta );
		item.setItemMeta( meta );
	}
}
