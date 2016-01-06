package io.github.bktlib.item.builders;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import io.github.bktlib.item.builders.impl.ItemBuilderImpl;

public interface ItemBuilder 
{
	ItemBuilder type( Material mat );
	
	ItemBuilder durability( int durability );
	
	ItemBuilder amount( int amount );
	
	ItemBuilder name( String displayName );
	
	ItemBuilder lore( String ... lines );
	
	ItemBuilder enchant( Enchantment ench, int level );
	
	ItemStack build();
	
	static ItemBuilder newBuilder()
	{
		return new ItemBuilderImpl();
	}
}
