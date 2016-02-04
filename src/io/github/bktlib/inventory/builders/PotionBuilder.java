package io.github.bktlib.inventory.builders;

import org.bukkit.potion.PotionEffectType;

import io.github.bktlib.inventory.builders.impl.PotionBuilderImpl;

public interface PotionBuilder extends ItemBuilder
{
	PotionBuilder effect( PotionEffectType eff );

	PotionBuilder duration( int secs );

	PotionBuilder level( int lvl );

	static PotionBuilder newBuilder()
	{
		return new PotionBuilderImpl();
	}
}
