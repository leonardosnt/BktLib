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

package io.github.bktlib.inventory.builders.impl;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.google.common.base.Preconditions;

import io.github.bktlib.inventory.builders.BookBuilder;
import io.github.bktlib.inventory.builders.ItemBuilder;

public class BookBuilderImpl extends ItemBuilderImpl implements BookBuilder
{
	protected BookMeta bookMeta;
	protected StringBuilder pageBuilder;

	public BookBuilderImpl()
	{
		item = new ItemStack( Material.WRITTEN_BOOK );
		bookMeta = (BookMeta) item.getItemMeta();
	}

	@Override
	public ItemBuilder type( Material mat )
	{
		throw new UnsupportedOperationException( "Cannot change type of Book" );
	}
	
	@Override
	public BookBuilder author( String author )
	{
		bookMeta.setAuthor( TRANSLATE_COLOR_CHARS.apply( author ) );
		
		return this;
	}

	@Override
	public BookBuilder title( String title )
	{
		bookMeta.setTitle( TRANSLATE_COLOR_CHARS.apply( title ) );
		
		return this;
	}

	@Override
	public BookBuilder newPage()
	{
		if ( pageBuilder == null )
		{
			pageBuilder = new StringBuilder();
		}
		else
		{
			writePage();
			pageBuilder.setLength( 0 );	
		}
		
		return this;
	}
	
	/*
	 *  Eu quero que seja necessario criar uma nova pagina 
	 *  antes de escrever uma ou mais linhas, questao de boa pratica :D
	 */
	@Override
	public BookBuilder line( String line )
	{
		Preconditions.checkState( pageBuilder != null, 
				"You must create new page before write an line" );
		
		pageBuilder.append( TRANSLATE_COLOR_CHARS.apply( line ) )
		.append( "\n" );
		
		return this;
	}

	@Override
	public BookBuilder lines( String... lines )
	{
		for ( String line : lines )
			line( line );
		
		return this;
	}

	@Override
	public ItemStack build()
	{
		writePage();
		item.setItemMeta( bookMeta );
		
		return super.build();
	}
	
	private void writePage()
	{
		if ( pageBuilder != null )
			bookMeta.addPage( pageBuilder.toString() );
	}
}
