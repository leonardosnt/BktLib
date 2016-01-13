package io.github.bktlib.ui;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.google.common.base.Preconditions;

/**
 * TODO: Documentar
 * 
 * @author Leonardosc
 */
public class Menu
{
	private MenuItem[] items;
	private Inventory inventory;

	/**
	 * @param title
	 * @param rows
	 */
	public Menu( final String title, final int rows )
	{
		Preconditions.checkArgument( rows > 0, "rows must be positive!" );
		Preconditions.checkNotNull( title, "title cannot be null" );

		Arrays.fill( items = new MenuItem[rows * 9], null );

		inventory = Bukkit.createInventory( 
				new MenuHolder( this ), 
				rows * 9, 
				title 
		);
	}

	/**
	 * @return
	 */
	public String getTitle()
	{
		return inventory.getTitle();
	}

	/**
	 * @return
	 */
	public Inventory getInventory()
	{
		return inventory;
	}

	/**
	 * @return
	 */
	public List<MenuItem> getItems()
	{
		return Collections.unmodifiableList(
				Lists.newArrayList( items ) 
		);
	}

	/**
	 * @return
	 */
	public int getSize()
	{
		return inventory.getSize();
	}

	/**
	 * @return
	 */
	public int getSizeX()
	{
		return 9;
	}

	/**
	 * @return
	 */
	public int getSizeY()
	{
		return getSize() / 9;
	}

	/**
	 * @param slot
	 * @return
	 */
	public Optional<MenuItem> getItemAt( int slot )
	{
		Preconditions.checkArgument( slot >= 0, "slot must be positive" );
		Preconditions.checkArgument( slot < items.length, 
				"invalid slot %s, must be less than %s", slot, items.length );

		return Optional.ofNullable( items[slot] );
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	public Optional<MenuItem> getItemAt( int x, int y )
	{
		return getItemAt( (y * 9) + x );
	}

	/**
	 * @param slot
	 * @param newItem
	 */
	public void setItem( int slot, MenuItem newItem )
	{
		Preconditions.checkNotNull( newItem, "newItem cannot be null" );

		if ( items[slot] == newItem )
			return;

		items[slot] = newItem;
	}

	/**
	 * @param slot
	 * @param item
	 */
	public void addItem( int slot, MenuItem item )
	{
		Preconditions.checkNotNull( item, "item cannot be null" );

		items[slot] = item;
		inventory.setItem( slot, item.getItemStack() );
	}

	/**
	 * @param x
	 * @param y
	 * @param item
	 */
	public void addItem( int x, int y, MenuItem item )
	{
		addItem( (y * 9) + x, item );
	}

	/**
	 * @param player
	 */
	public void open( Player player )
	{
		Preconditions.checkNotNull( player, "player cannot be null" );

		player.openInventory( inventory );
	}
}
