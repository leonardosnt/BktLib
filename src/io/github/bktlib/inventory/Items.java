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

package io.github.bktlib.inventory;

import io.github.bktlib.misc.BukkitUtil;
import io.github.bktlib.nbt.NBTTagCompound;
import io.github.bktlib.lazy.LazyInitMethod;
import io.github.bktlib.reflect.util.ReflectUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.bukkit.ChatColor.translateAlternateColorCodes;

public final class Items {
  /**
   * Define o nome de exibição do item.
   *
   * @param item Item que deseja modificar.
   * @param name Nome desejado.
   */
  public static void setDisplayName(final ItemStack item, final String name) {
    checkNotNull(item, "item cannot be null");
    checkNotNull(name, "name cannot be null");

    consumeMeta(item, meta -> meta.setDisplayName(
            translateAlternateColorCodes('&', name)
    ));
  }

  /**
   * Define o lore do item.
   *
   * @param item Item que deseja modificar.
   * @param lore Lore desejado.
   */
  public static void setLore(final ItemStack item, final String... lore) {
    checkNotNull(item, "item cannot be null");
    checkNotNull(lore, "lore cannot be null");

    consumeMeta(item, meta -> meta.setLore(
            Stream.of(lore)
                    .map(line -> translateAlternateColorCodes('&', line))
                    .collect(Collectors.toList())
    ));
  }

  /**
   * Verifica se o item tem um displayName definido.
   *
   * @param item Item a ser verificado.
   * @return se o item tem um displayName definido.
   */
  public static boolean hasDisplayName(final ItemStack item) {
    return item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName();
  }

  /**
   * Verifica se o item tem um lore definido.
   *
   * @param item Item a ser verificado.
   * @return se o item tem um lore definido.
   */
  public static boolean hasLore(final ItemStack item) {
    return item != null && item.hasItemMeta() && item.getItemMeta().hasLore();
  }

  public static Optional<String> getDisplayName(final ItemStack item) {
    return hasDisplayName(item)
            ? Optional.of(item.getItemMeta().getDisplayName())
            : Optional.empty();
  }

  public static Optional<List<String>> getLore(final ItemStack item) {
    return hasLore(item)
            ? Optional.of(item.getItemMeta().getLore())
            : Optional.empty();
  }

  /**
   * <p>Pega o {@link NBTTagCompound} do item.</p>
   * <p>Obs: caso você mude alguma coisa você precisa
   * usar o {@link #setNbtTag(ItemStack, NBTTagCompound)} para que as
   * alterações sejam salvas no item.</p>
   *
   * @param item ItemStack
   * @return Um novo {@link NBTTagCompound}
   */
  public static NBTTagCompound getNbtTag(ItemStack item) {
    checkNotNull(item, "item");
    if (item.getClass() == ItemStack.class) {
      /**
       * O ItemStack puro não tem o NBTTagCompound.
       */
      throw new UnsupportedOperationException("Cannot get tag from ItemStack, " +
              "the item must be converted as CraftItemStack, use Items.asCraftCopy(item) to do this.");
    }
    try {
      Object nmsTag = nmsItemGetTag.get().invoke(BukkitUtil.unwrap(item));
      if (nmsTag == null) {
        return new NBTTagCompound();
      }
      return NBTTagCompound.fromNMSCompound(nmsTag);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return new NBTTagCompound();
  }

  /**
   * Define o {@link NBTTagCompound} do {@code item}
   *
   * @param item Item
   * @param compound Compound
   */
  public static void setNbtTag(ItemStack item, NBTTagCompound compound) {
    checkNotNull(item, "item");

    try {
      if (item.getClass() == ItemStack.class) {
        /**
         * O ItemStack puro não tem o NBTTagCompound.
         */
        throw new UnsupportedOperationException("Cannot use setTag on ItemStack, " +
                "the item must be converted as CraftItemStack, use Items.asCraftCopy(item) to do this.");
      }
      nmsItemSetTag.get().invoke(BukkitUtil.unwrap(item), compound.asNMSCompound());
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  /**
   * É o equivalente a
   * <pre>{@code
   * NBTTagCompound tag = Items.getNbtTag(item);
   * tag.setString("hello", "world"); // Modifica a tag.
   * Items.setNbtTag(item, tag);
   * }</pre>
   *
   * @param item Item
   * @param consumer Consumer
   */
  public static void modifyTag(ItemStack item, Consumer<NBTTagCompound> consumer) {
    checkNotNull(item);

    final NBTTagCompound itemTag = getNbtTag(item);
    consumer.accept(itemTag);
    setNbtTag(item, itemTag);
  }

  /**
   * Converte o ItemStack {@code item} para CraftItemStack.
   *
   * @param item Item
   * @return O item convertido para CraftItemStack.
   */
  public static ItemStack asCraftCopy(ItemStack item) {
    try {
      return /* CraftItemStack */(ItemStack) asCraftCopyMethod.get().invoke(null, item);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static void consumeMeta(final ItemStack item, final Consumer<ItemMeta> metaConsumer) {
    ItemMeta meta = item.getItemMeta();
    metaConsumer.accept(meta);
    item.setItemMeta(meta);
  }

  private Items() {
    throw new UnsupportedOperationException();
  }

  private static final LazyInitMethod nmsItemGetTag = new LazyInitMethod(
      ReflectUtil.resolveName("{nms}.ItemStack"), "getTag", new Class[0]);

  private static final LazyInitMethod nmsItemSetTag = new LazyInitMethod(
      ReflectUtil.resolveName("{nms}.ItemStack"), "setTag",
      ReflectUtil.resolveName("{nms}.NBTTagCompound"));

  private static final LazyInitMethod asCraftCopyMethod = new LazyInitMethod(
      ReflectUtil.resolveName("{cb}.inventory.CraftItemStack"),
      "asCraftCopy", ItemStack.class);

}
