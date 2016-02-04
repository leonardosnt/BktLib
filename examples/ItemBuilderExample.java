import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.bktlib.item.builders.ItemBuilder;

public class ItemBuilderExample
{
	public void example()
	{
		Player anPlayer = /* ... */ null;
		
		ItemStack coolSword = ItemBuilder.newBuilder()
            				 .type( Material.STONE_SWORD )
            				 .name( "&3Xablau's sword!" )
            				 .lore( "&AUm lore legal" )
            				 .lore( "&6Que da pra colocar" )
            				 .lore( "&6Varias linhas" )
            				 .enchant( Enchantment.DAMAGE_ALL, 10 )
            				 .enchant( Enchantment.FIRE_ASPECT, 10 )
            				 .amount( 24 )
            				 .durability( 123 )
            				 .build();
		
		ItemStack is = ItemBuilder.of( Material.LEATHER_CHESTPLATE )
        				.meta((LeatherArmorMeta meta) -> {
        					meta.setColor( Color.AQUA );
        				}).build();
		
		anPlayer.getInventory().addItem( coolSword );
	}
}
