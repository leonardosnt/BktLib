package io.github.bktlib.item.builders;

import org.bukkit.Effect;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public interface PotionBuilder extends ItemBuilder
{
	EffectBuilder newEffect( PotionEffectType eff );
	
	public interface EffectBuilder
	{
		EffectBuilder duration( int secs );
		
		EffectBuilder level( int lvl );
		
		PotionBuilder endEffect();
	}
}
