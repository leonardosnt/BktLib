package io.github.bktlib.ui.events;

import io.github.bktlib.ui.Menu;
import io.github.bktlib.ui.MenuItem;
import org.bukkit.entity.Player;

/**
 * Representa o evento de quando o jogador 
 * clica em um item no menu.
 * 
 * @author Leonardosc
 */
public class ItemClickedEvent
{
	private MouseButton button;
	private Menu menu;
	private MenuItem item;
	private Player player;

	
	/**
	 * @param button Botão que foi clicado
	 * @param player Jogador que clicou.
	 * @param menu Menu que está o item que foi clicado.
	 * @param item Item que foi clicado.
	 */
	public ItemClickedEvent( MouseButton button, Player player, Menu menu, MenuItem item )
	{
		this.button = button;
		this.player = player;
		this.menu = menu;
		this.item = item;
	}

	/**
	 * @return O botão que acionou o evento.
	 */
	public MouseButton getButton()
	{
		return button;
	}

    public Menu getMenu()
    {
        return menu;
    }

    public MenuItem getItem()
    {
        return item;
    }

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
