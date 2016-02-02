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
	private ItemStack itemStack;
	private Consumer<ItemClickedEvent> onClicked;

	/**
	 * @param itemStack
	 */
	public MenuItem( final ItemStack itemStack )
	{
		this.itemStack = itemStack;
	}

	/**
	 * @param material
	 */
	public MenuItem( final Material material )
	{
		itemStack = new ItemStack( material );
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
	
	/**
	 * @param itemStack
	 */
	public void setItemStack( ItemStack itemStack )
	{
		this.itemStack = itemStack;
	}

	/*
	 * (non-Javadoc)
	 * 
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
