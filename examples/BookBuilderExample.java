import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.bktlib.inventory.builders.BookBuilder;
import io.github.bktlib.inventory.builders.ItemBuilder;

public class BookBuilderExample
{
	public void example()
	{
		Player anPlayer = /* ... */ null;
		
		ItemStack book = BookBuilder.newBuilder()
        				.author( "&3leonardo&csc" )
        				.title( "&3xablaus book" )
        				.newPage()
        				  .line( "&bpagina xablaustica" )
        				  .lines( "&3com varias linhas", "&6coloridas" )
        				.newPage()
        				  .line( "&bE varias paginas" )
        				.build();
		
		anPlayer.getInventory().addItem( book );
	}
}
