package io.github.bktlib.ui;

import java.util.Optional;
import java.util.function.Consumer;

import io.github.bktlib.ui.events.ItemClickedEvent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * @author Leonardosc
 */
public class MenuItem
{
	public ItemStack itemStack;
	public Consumer<ItemClickedEvent> onClicked;
	
	/**
	 * @param is
	 */
	public MenuItem( ItemStack is )
	{
		itemStack = is;
	}
	
	/**
	 * @param mat
	 */
	public MenuItem( Material mat )
	{
		itemStack = new ItemStack( mat );
	}
	
	/**
	 * @return
	 */
	public ItemStack getItemStack()
	{
		return itemStack;
	}
	
	/**
	 * @return
	 */
	public Optional<Consumer<ItemClickedEvent>> getOnClicked()
	{
		return Optional.ofNullable( onClicked );
	}
	
	/**
	 * @param consumer
	 */
	public void setOnClicked( Consumer<ItemClickedEvent> consumer )
	{
		consumer = Preconditions.checkNotNull( consumer, 
				"consumer cannot be null" );
		
		onClicked = consumer;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return Objects.hashCode(
				itemStack,
				onClicked
		);
	}
}
