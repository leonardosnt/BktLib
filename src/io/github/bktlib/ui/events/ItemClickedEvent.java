package io.github.bktlib.ui.events;

/**
 * Representa o evento de quando o jogador
 * clica em um item no menu.
 * 
 * @author Leonardosc
 */
public class ItemClickedEvent
{	
	private MouseButton button;
	
	/**
	 * @param button Botão com que foi clicado
	 */
	public ItemClickedEvent( MouseButton button )
	{
		this.button = button;
	}
	
	/**
	 * @return O botão que acionou o evento.
	 */
	public MouseButton getButton()
	{
		return button;
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
