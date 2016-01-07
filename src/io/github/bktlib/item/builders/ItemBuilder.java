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

package io.github.bktlib.item.builders;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import io.github.bktlib.item.builders.impl.ItemBuilderImpl;

/**
 * Classe utilitaria para "construir" items.
 * 
 * @author Leonardosc
 */
public interface ItemBuilder 
{
	/**
	 * Define o tipo do item.
	 * 
	 * @param mat Material desejado
	 */
	ItemBuilder type( Material mat );
	
	/**
	 * Define a durabilidade do item
	 * 
	 * @param durability Durabilidade desejada
	 */
	ItemBuilder durability( int durability );
	
	/**
	 * Define a durabilidade maxima para o item.
	 */
	ItemBuilder maxDurability();
	
	/**
	 * Define a quantidade do item
	 * 
	 * @param amount Quantidade desejada
	 */
	ItemBuilder amount( int amount );
	
	/**
	 * Define o nome do item.
	 * 
	 * @param displayName Nome desejado
	 */
	ItemBuilder name( String displayName );
	
	/**
	 * Adiciona linhas ao lore do item
	 * 
	 * @param lines Linhas para adicionar
	 */
	ItemBuilder lore( String ... lines );
	
	/**
	 * Adiciona um encantamento ao item.
	 * 
	 * @param ench Tipo do encantamento
	 * @param level Level do encantamento
	 */
	ItemBuilder enchant( Enchantment ench, int level );
	
	/**
	 * @return O ItemStack "construido"
	 */
	ItemStack build();
	
	/**
	 * @return Uma nova instancia da implementacao dessa classe.
	 */
	static ItemBuilder newBuilder()
	{
		return new ItemBuilderImpl();
	}
}
