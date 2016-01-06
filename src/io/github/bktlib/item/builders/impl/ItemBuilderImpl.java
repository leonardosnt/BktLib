package io.github.bktlib.item.builders.impl;

import java.util.Arrays;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.base.Preconditions;

import io.github.bktlib.item.builders.BookBuilder;
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
		Preconditions.checkArgument( durability > Short.MAX_VALUE, 
				"durability must be less than %s", Short.MAX_VALUE );
		
		item.setDurability( (short) durability );
		return this;
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
		modifyMeta( meta -> meta.setLore( Arrays.asList( lines ) ) );
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
