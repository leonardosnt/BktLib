package io.github.bktlib;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Preconditions;


/**
 * Classe utilitaria para com metodos frequentemente 
 * usados para  manipulacao de inventarios
 * 
 * @author leonardosc
 */
public class Invs
{
	private Invs() {}

	/**
	 * @param inv
	 * @param item
	 */
	public static void fill( Inventory inv, ItemStack item )
	{
		Preconditions.checkNotNull( inv, "inv cannot be null" );
		
		for ( int i = 0; i < inv.getSize(); i++ )
		{
		 	inv.setItem( i, item );
		}
	}

	/**
	 * @param inv
	 * @param material
	 */
	public static  void fill( Inventory inv, Material material )
	{
		Preconditions.checkNotNull( inv, "inv cannot be null" );
		
		if ( material == Material.AIR )
			return;
		
		fill( inv, new ItemStack( material ) );
	}

	/**
	 * @param inv
	 * @return
	 */
	public static boolean isEmpty( Inventory inv )
	{
		Preconditions.checkNotNull( inv, "inv cannot be null" );
		
		ItemStack[] contents = inv.getContents();
		
		for ( int i = 0; i < contents.length; i++ )
		{
			if ( contents[i] != null )
				return false;
		}
		
		return true;
	}
    
    /**
     * @param inv
     */
    public static void clear( Inventory inv )
    {
        fill( inv, (ItemStack) null );
    }
}
