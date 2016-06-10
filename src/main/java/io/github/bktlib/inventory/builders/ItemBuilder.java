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

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemBuilder {

  protected static final Function<String, String> TRANSLATE_COLOR_CHARS = text -> (
    text == null ? null : CharMatcher.anyOf("&").collapseFrom(text, '\u00a7')
  );

  protected ItemStack item = new ItemStack(Material.AIR);

  /**
   * Define o tipo do item.
   *
   * @param mat Material desejado
   */
  public ItemBuilder type(Material mat) {
    item.setType(mat);
    return this;
  }

  /**
   * Deixa o item com efeito de encantamento.
   */
  public ItemBuilder glowing() {
    return enchantment(Enchantment.DURABILITY, 1).
           flags(ItemFlag.HIDE_ENCHANTS);
  }

  /**
   * Define o dano do item, o contrario do {@link #durability(int)}.
   *
   * @param damage Material desejado.
   * @throws IllegalArgumentException Se {@code damage} > {@link Short#MAX_VALUE} ||
   *                                  {@code damage} < {@link Short#MIN_VALUE}
   */
  public ItemBuilder damage(int damage) {
    if (damage > Short.MAX_VALUE || damage < Short.MIN_VALUE) {
      throw new IllegalArgumentException("damage must be between " + Short.MIN_VALUE +
                                         " and " + Short.MAX_VALUE);
    }
    item.setDurability((short) damage);
    return this;
  }

  /**
   * Adiciona as {@code flags} ao item.
   *
   * @param flags Flags
   */
  public ItemBuilder flags(ItemFlag ... flags) {
    if (flags == null || flags.length == 0) {
      return this;
    }
    editMeta(i -> {
      if (i == null) {
        throw new UnsupportedOperationException("Cannot define flags for this item " + item);
      }
      i.addItemFlags(flags);
    });
    return this;
  }

  /**
   * Define a durabilidade do item
   *
   * @param durability Durabilidade desejada
   */
  public ItemBuilder durability(int durability) {
    if (durability > Short.MAX_VALUE || durability < Short.MIN_VALUE) {
      throw new IllegalArgumentException("durability must be between " + Short.MIN_VALUE +
                                         " and " + Short.MAX_VALUE);
    }
    item.setDurability((short) (item.getType().getMaxDurability() - durability));
    return this;
  }

  /**
   * Define a durabilidade maxima para o item.
   */
  public ItemBuilder maxDurability() {
    return durability(item.getType().getMaxDurability());
  }

  /**
   * Define a quantidade do item
   *
   * @param amount Quantidade desejada
   */
  public ItemBuilder amount(int amount) {
    Preconditions.checkArgument(amount > 0, "amount must be positive");
    item.setAmount(amount);
    return this;
  }

  /**
   * Define a quantidade maxima para o item.
   */
  public ItemBuilder maxAmount() {
    return amount(item.getMaxStackSize());
  }

  /**
   * Define o nome do item.
   *
   * @param displayName Nome desejado
   */
  public ItemBuilder displayName(String displayName) {
    editMeta(meta ->
      meta.setDisplayName(TRANSLATE_COLOR_CHARS.apply(displayName))
    );
    return this;
  }

  /**
   * Adiciona linhas ao lore do item
   *
   * @param lines Linhas para adicionar
   */
  public ItemBuilder lore(String ... lines) {
    editMeta(meta -> {
      if (lines == null || lines.length == 0) {
        meta.setLore(null);
      } else {
        meta.setLore(Stream.of(lines).map(TRANSLATE_COLOR_CHARS)
            .collect(Collectors.toList()));
      }
    });
    return this;
  }

  /**
   * Adiciona um encantamento ao item.
   *
   * @param enchantment Tipo do encantamento
   * @param level       Level do encantamento
   */
  public ItemBuilder enchantment(Enchantment enchantment, int level) {
    Preconditions.checkArgument(level > 0, "level must be positive");
    item.addUnsafeEnchantment(enchantment, level);
    return this;
  }

  /**
   * Modifica o {@link ItemMeta} do item. O ItemMeta pode ser {@code null}.
   *
   * @param metaConsumer Função de modifica o {@link ItemMeta}
   */
  @SuppressWarnings("unchecked")
  public <T extends ItemMeta> ItemBuilder meta(Consumer<T> metaConsumer) {
    Preconditions.checkNotNull(metaConsumer, "metaConsumer cannot be null.");
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
  public ItemStack build() {
    return item;
  }

  /**
   * Constroi o item e adiciona ao invetario do {@code player}.
   *
   * @return O ItemStack "construido"
   */
  public ItemStack giveTo(Player player) {
    Preconditions.checkNotNull(player, "player cannot be null");
    return addTo(player.getInventory());
  }

  /**
   * Constroi o item e adiciona ao invetario {@code inv}.
   *
   * @return O ItemStack "construido"
   */
  public ItemStack addTo(Inventory inventory) {
    Preconditions.checkNotNull(inventory, "inventory cannot be null");
    final ItemStack item = build();
    inventory.addItem(item);
    return item;
  }

  /**
   * @return Uma nova instancia de {@link ItemBuilder}
   */
  public static ItemBuilder newBuilder() {
    return new ItemBuilder();
  }

  /**
   * @return Uma nova instancia de {@link ItemBuilder}
   */
  public static ItemBuilder of(Material material) {
    return newBuilder().type(material);
  }

  private void editMeta(Consumer<ItemMeta> consumer) {
    final ItemMeta meta = item.getItemMeta();
    consumer.accept(meta);
    item.setItemMeta(meta);
  }
}
