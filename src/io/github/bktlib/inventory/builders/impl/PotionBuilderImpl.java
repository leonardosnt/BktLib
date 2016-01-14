package io.github.bktlib.inventory.builders.impl;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import io.github.bktlib.inventory.builders.PotionBuilder;

// !!!!!! AINDA NÃO FUNCIONAL !!!!!!
public class PotionBuilderImpl extends ItemBuilderImpl implements PotionBuilder
{
	public PotionMeta potionMeta;
	public int effDuration, effLevel;
	public PotionEffectType effType;
	
	public PotionBuilderImpl()
	{
		item = new ItemStack( Material.POTION );
		potionMeta = (PotionMeta) item.getItemMeta();
	}
	
	@Override
	public PotionBuilder effect( PotionEffectType eff )
	{
		return this;
	}

	@Override
	public PotionBuilder duration( int secs )
	{
		effDuration = secs;
		return this;
	}

	@Override
	public PotionBuilder level( int lvl )
	{
		effLevel = lvl;
		return this;
	}
	
	@Override
	public ItemStack build()
	{
		item.setItemMeta( potionMeta );
		return super.build();
	}
}
