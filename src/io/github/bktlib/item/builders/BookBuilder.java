package io.github.bktlib.item.builders;

import org.bukkit.inventory.ItemStack;

import io.github.bktlib.item.builders.impl.BookBuilderImpl;

public interface BookBuilder extends ItemBuilder
{
	BookBuilder author( String author );
	
	BookBuilder title( String title );
	
	PageBuilder newPage();
	
	static BookBuilder newBuilder()
	{
		return new BookBuilderImpl();
	}
	
	public interface PageBuilder
	{
		PageBuilder line( String line );
		
		PageBuilder lines( String ... lines );
		
		BookBuilder endPage();
	}
}
