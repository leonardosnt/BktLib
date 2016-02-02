package io.github.bktlib.ui.events;

import io.github.bktlib.ui.Menu;
import io.github.bktlib.ui.MenuItem;
import org.bukkit.entity.Player;

/**
 * Representa o evento de quando o jogador clica em um item no menu.
 * 
 * @author Leonardosc
 */
public class ItemClickedEvent
{
	private MouseButton button;
	private Menu menu;
	private MenuItem item;
	private Player player;
	private int slot;

	/**
	 * @param button Botão que foi clicado
	 * @param player Jogador que clicou.
	 * @param menu Menu que está o item que foi clicado.
	 * @param item Item que foi clicado.
	 */
	public ItemClickedEvent( MouseButton button, 
							 Player player, 
							 Menu menu, 
							 MenuItem item,
							 int slot )
	{
		this.button = button;
		this.player = player;
		this.menu = menu;
		this.item = item;
		this.slot = slot;
	}

	/**
	 * @return O botão que foi clicado.
	 */
	public MouseButton getButton()
	{
		return button;
	}

	/**
	 * @return O menu que o item clicado está.
	 * @see MenuItem
	 */
	public Menu getMenu()
	{
		return menu;
	}

	/**
	 * @return
	 */
	public int getSlot()
	{
		return slot;
	}
	
	/**
	 * @return Item que foi clicado.
	 * @see MenuItem
	 */
	public MenuItem getItem()
	{
		return item;
	}

	/**
	 * @return Jogador que clicou no item.
	 */
	public Player getWhoClicked()
	{
		return player;
	}

	public enum MouseButton
	{
		/**
		 * Representa o botão esquerdo do mouse
		 */
		LEFT,

		/**
		 * Representa o botão direito do mouse
		 */
		RIGHT
	}
}
