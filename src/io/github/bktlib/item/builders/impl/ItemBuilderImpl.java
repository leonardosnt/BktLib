package io.github.bktlib.item.builders.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
	public ItemBuilder durability( short durability )
	{
		item.setDurability( (short) 
				(item.getType().getMaxDurability() - durability) );
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
		item.setAmount( amount );
		return this;
	}

	@Override
	public ItemBuilder name( String displayName )
	{
		modifyMeta( meta -> meta.setDisplayName( displayName ) );
		return this;
	}

	@Override
	public ItemBuilder lore( String... lines )
	{
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
		item.addUnsafeEnchantment( ench, level );
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
