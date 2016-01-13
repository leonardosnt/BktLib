package io.github.bktlib.ui;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * @author Leonardosc
 */
public class MenuHolder implements InventoryHolder
{
	private Menu menu;

	MenuHolder( final Menu menu )
	{
		this.menu = menu;
	}

	/**
	 * @return
	 */
	public Menu getMenu()
	{
		return menu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.bukkit.inventory.InventoryHolder#getInventory()
	 */
	@Override
	public Inventory getInventory()
	{
		return menu.getInventory();
	}
}
