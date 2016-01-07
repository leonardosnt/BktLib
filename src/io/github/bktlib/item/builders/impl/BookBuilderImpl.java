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

package io.github.bktlib.item.builders.impl;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import io.github.bktlib.item.builders.BookBuilder;

public class BookBuilderImpl extends ItemBuilderImpl implements BookBuilder
{

	public BookMeta bookMeta;

	public BookBuilderImpl()
	{
		super();
		super.type( Material.WRITTEN_BOOK ).build();
		bookMeta = (BookMeta) item.getItemMeta();
	}

	@Override
	public BookBuilder author( String author )
	{
		bookMeta.setAuthor( author );
		
		return this;
	}

	@Override
	public BookBuilder title( String title )
	{
		bookMeta.setTitle( title );
		
		return this;
	}

	@Override
	public PageBuilder newPage()
	{
		return new PageBuilderImpl();
	}

	@Override
	public ItemStack build()
	{
		item.setItemMeta( bookMeta );
		
		return super.build();
	}
	
	public class PageBuilderImpl implements PageBuilder
	{
		protected StringBuilder pageBuilder;
		
		public PageBuilderImpl()
		{
			pageBuilder = new StringBuilder();
		}
		
		@Override
		public PageBuilder line( String line )
		{	
			pageBuilder.append( line ).append( "\n" );
			
			return this;
		}

		@Override
		public PageBuilder lines( String... lines )
		{
			for ( String line : lines ) 
				line( line );
			
			return this;
		}

		@Override
		public BookBuilder endPage()
		{
			if ( pageBuilder.length() != 0 )
				bookMeta.addPage( pageBuilder.toString() );
			
			return BookBuilderImpl.this;
		}
	}
}
