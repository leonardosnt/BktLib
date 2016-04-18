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

import static com.google.common.base.Preconditions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;

public class ItemBuilder
{
    protected static final Function<String, String> TRANSLATE_COLOR_CHARS = text ->
            text == null ? null : CharMatcher.anyOf( "&" ).collapseFrom( text, '\u00a7' );

    protected ItemStack item = new ItemStack( Material.AIR );

    /**
     * Define o tipo do item.
     *
     * @param mat
     *            Material desejado
     */
    public ItemBuilder type(Material mat )
    {
        item.setType( mat );
        return this;
    }

    /**
     * Define a durabilidade do item
     *
     * @param durability
     *            Durabilidade desejada
     */
    public ItemBuilder durability(int durability )
    {
        checkArgument( durability <= Short.MAX_VALUE, "withDurability must less or " +
                        "equals than %s (Short.MAX_VALUE)",
                        Short.MAX_VALUE );

        item.setDurability( (short) (item.getType().getMaxDurability() - durability) );
        return this;
    }

    /**
     * Define a durabilidade maxima para o item.
     */
    public ItemBuilder maxDurability()
    {
        return durability( item.getType().getMaxDurability() );
    }

    /**
     * Define a quantidade do item
     *
     * @param amount
     *            Quantidade desejada
     */
    public ItemBuilder amount( int amount )
    {
        checkArgument( amount > 0, "withAmount must be positive" );
        item.setAmount( amount );
        return this;
    }

    /**
     * Define a quantidade maxima para o item.
     */
    public ItemBuilder maxAmount()
    {
        return amount( item.getMaxStackSize() );
    }

    /**
     * Define o nome do item.
     *
     * @param displayName
     *            Nome desejado
     */
    public ItemBuilder displayName( String displayName )
    {
        if ( displayName == null ) return this;
        consumeMeta( meta ->
            meta.setDisplayName( TRANSLATE_COLOR_CHARS.apply( displayName ) )
        );
        return this;
    }

    /**
     * Adiciona linhas ao lore do item
     *
     * @param lines
     *            Linhas para adicionar
     */
    public ItemBuilder lore( String... lines )
    {
        if ( lines == null || lines.length == 0 )
            return this;

        consumeMeta( meta -> {
            ArrayList<String> lore = Lists.newArrayList();
            List<String> currentLore = meta.getLore();

            if ( currentLore != null && !currentLore.isEmpty() )
                lore.addAll( currentLore );

            lore.addAll( Arrays.asList( lines ) );
            meta.setLore( lore.stream()
                    .map( TRANSLATE_COLOR_CHARS )
                    .collect( Collectors.toList() ) );
        } );

        return this;
    }

    /**
     * Adiciona um encantamento ao item.
     *
     * @param enchantment
     *            Tipo do encantamento
     * @param level
     *            Level do encantamento
     */
    public ItemBuilder enchantment( Enchantment enchantment, int level )
    {
        checkArgument( level > 0, "level must be positive" );

        item.addUnsafeEnchantment( enchantment, level );
        return this;
    }

    /**
     * Modifica o {@link ItemMeta} do item. O ItemMeta pode ser {@code null}, cuidado.
     *
     * @param metaConsumer
     *            Função de modifica o {@link ItemMeta}
     */
    @SuppressWarnings("unchecked")
    public <T extends ItemMeta> ItemBuilder meta( Consumer<T> metaConsumer )
    {
        checkNotNull( metaConsumer, "metaMapper cannot be null." );

        final ItemMeta meta = item.getItemMeta();
        metaConsumer.accept((T) meta);
        item.setItemMeta(meta);
        return this;
    }

    /**
     * Constroi o item.
     *
     * @return O ItemStack "construido"
     */
    public ItemStack build()
    {
        return item;
    }

    /**
     * Constroi o item e adiciona ao invetario do {@code player}.
     *
     * @return O ItemStack "construido"
     */
    public ItemStack buildAndGive( Player player )
    {
        checkNotNull(player, "player cannot be null");
        return buildAndAdd( player.getInventory() );
    }

    /**
     * Constroi o item e adiciona ao invetario {@code inv}.
     *
     * @return O ItemStack "construido"
     */
    public ItemStack buildAndAdd( Inventory inv )
    {
        checkNotNull(inv, "inv cannot be null");

        ItemStack item = build();
        inv.addItem( item );
        return item;
    }

    /**
     * @return Uma nova instancia de {@link ItemBuilder}
     */
    public static ItemBuilder newBuilder()
    {
        return new ItemBuilder();
    }

    /**
     * @return Uma nova instancia de {@link ItemBuilder}
     */
    public static ItemBuilder of( Material material )
    {
        return newBuilder().type( material );
    }

    private void consumeMeta( Consumer<ItemMeta> consumer )
    {
        final ItemMeta meta = item.getItemMeta();
        consumer.accept( meta );
        item.setItemMeta( meta );
    }
}
