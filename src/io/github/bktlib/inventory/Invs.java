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

package io.github.bktlib.inventory;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
		
		IntStream.rangeClosed( 
				0, inv.getSize() - 1 
		).forEach( idx -> inv.setItem( idx, item ) );
	}

	/**
	 * @param supplier
	 * @param item
	 */
	public static void fill( Supplier<? extends Inventory> supplier, ItemStack item )
	{
		fill( supplier.get(), item );
	}
	
	/**
	 * @param inv
	 * @param material
	 */
	public static void fill( Inventory inv, Material material )
	{
		Preconditions.checkNotNull( inv, "inv cannot be null" );
		
		if ( material == Material.AIR )
			return;
		
		fill( inv, new ItemStack( material ) );
	}
	
	/**
	 * @param supplier
	 * @param mat
	 */
	public static void fill( Supplier<? extends Inventory> supplier, Material mat )
	{
		fill( supplier.get(), mat );
	}
	
	/**
	 * @param inv
	 * @return
	 */
	public static boolean isEmpty( Inventory inv )
	{
		Preconditions.checkNotNull( inv, "inv cannot be null" );
		
		return !stream( inv )
				.filter( Objects::nonNull )
				.findAny()
				.isPresent();
	}
	
	/**
	 * @param supplier
	 * @return
	 */
	public static boolean isEmpty( Supplier<? extends Inventory> supplier )
	{
		return isEmpty( supplier.get() );
	}
    
    /**
     * @param inv
     * @return
     */
    public static Stream<ItemStack> stream( Inventory inv )
    {
    	Preconditions.checkNotNull( inv, "inv cannot be null" );
    	
    	return Stream.of( inv.getContents() );
    }
    
    /**
     * @param supplier
     * @return
     */
    public static Stream<ItemStack> stream( Supplier<? extends Inventory> supplier )
    {
    	return stream( supplier.get() );
    }
    
	/**
	 * Verifica se todos os slots estão com
	 * pelo menos 1 item.
	 * 
	 * @param inv Inventario a ser checado
	 * @return Se o inventario está cheio
	 */
	public static boolean isFull( Inventory inv )
	{
    	Preconditions.checkNotNull( inv, "inv cannot be null" );
    	
		return inv.firstEmpty() == -1;
	}
	
	/**
	 * @see {@link #isFull(Inventory) isFull(Inventory)}
	 */
	public static boolean isFull( Supplier<? extends Inventory> inv )
	{
		return isFull( inv.get() );
	}
	
	/**
	 * Verifica se TODOS os slots do inventario
	 * estão com stacks cheias.
	 * 
	 * @param inv Inventario a ser checado
	 * @return Se o inventario está completamente cheio
	 */
	public static boolean isCompletelyFull( Inventory inv )
	{
		if ( !isFull( inv ) ) return false;
		
		ItemStack[] contents = inv.getContents();
		
		for ( int i = 0 ; i < inv.getSize() ; i++ )
		{
			ItemStack item = contents[i];
			
			if ( item == null || item.getAmount() != item.getMaxStackSize() )
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * @see {@link #isCompletelyFull(Inventory) isCompletelyFull(Inventory)}
	 */
	public static boolean isCompletelyFull( Supplier<? extends Inventory> inv )
	{
		return isCompletelyFull( inv.get() );
	}
}
