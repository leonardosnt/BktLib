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

package io.github.bktlib.inventory.builders;

import io.github.bktlib.inventory.builders.impl.BookBuilderImpl;

public interface BookBuilder extends ItemBuilder
{
	/**
	 * Define o nome do autor do livro.
	 * 
	 * @param author O nome do autor desejado
	 */
	BookBuilder author( String author );
	
	/**
	 * Define o titulo do livro.
	 * 
	 * @param title Titulo desejado
	 */
	BookBuilder title( String title );
	
	/**
	 * Cria uma nova página.
	 */
	BookBuilder newPage();
	
	/**
	 * Adiciona uma linha a pagina atual.
	 * 
	 * @param line Linha a ser adicionada.
	 */
	BookBuilder line( String line );
	
	/**
	 * Adiciona N linhas a pagina atual.
	 * 
	 * @param lines Lista de linhas a ser adicionada.
	 */
	BookBuilder lines( String ... lines );
	
	/**
	 * Retorna uma nova instancia da implementação dessa classe.
	 */
	static BookBuilder newBuilder()
	{
		return new BookBuilderImpl();
	}
}
