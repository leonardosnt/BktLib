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

import io.github.bktlib.misc.LazyInitVar;
import io.github.bktlib.reflect.util.ReflectUtil;
import io.github.bktlib.wrappers.NBTTagCompoundWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

  private static void consumeMeta(final ItemStack item, final Consumer<ItemMeta> metaConsumer) {
    ItemMeta meta = item.getItemMeta();
    metaConsumer.accept(meta);
    item.setItemMeta(meta);
  }

  private Items() {
    throw new UnsupportedOperationException();
  }

  public static class Meta {
    private static LazyInitVar<Method> nmsItemGetTag = new LazyInitVar<Method>() {
      @Override
      public Method init() {
        try {
          Class<?> nmsItemStackClass = ReflectUtil.getClass("{nms}.ItemStack");
          return nmsItemStackClass.getDeclaredMethod("getTag");
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        }
        return null;
      }
    };
    private static LazyInitVar<Method> nmsItemSetTag = new LazyInitVar<Method>() {
      @Override
      public Method init() {
        try {
          Class<?> nmsItemStackClass = ReflectUtil.getClass("{nms}.ItemStack");
          Class<?> cls = ReflectUtil.getClass("{nms}.NBTTagCompound");
          return nmsItemStackClass.getDeclaredMethod("setTag", cls);
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        }
        return null;
      }
    };

    private static Object getTag(ItemStack item) {
      try {
        final Object itemHandle = ReflectUtil.getNmsHandle(item);
        final Object nbtCompound;
        if (nmsItemGetTag.get().invoke(itemHandle) == null) {
          nbtCompound = ReflectUtil.instantiate("{nms}.NBTTagCompound");
          nmsItemSetTag.get().invoke(itemHandle, nbtCompound);
        } else {
          nbtCompound = nmsItemGetTag.get().invoke(itemHandle);
        }
        return nbtCompound;
      } catch (IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
      return null;
    }

    public static void removeKey(ItemStack item, String key) {
      NBTTagCompoundWrapper.of(getTag(item)).remove(key);
    }

    public static void setString(ItemStack item, String key, String value) {
      NBTTagCompoundWrapper.of(getTag(item)).setString(key, value);
    }

    public static void setBoolean(ItemStack item, String key, boolean value) {
      NBTTagCompoundWrapper.of(getTag(item)).setBoolean(key, value);
    }

    public static void setByte(ItemStack item, String key, byte value) {
      NBTTagCompoundWrapper.of(getTag(item)).setByte(key, value);
    }

    public static void setFloat(ItemStack item, String key, float value) {
      NBTTagCompoundWrapper.of(getTag(item)).setFloat(key, value);
    }

    public static void setInt(ItemStack item, String key, int value) {
      NBTTagCompoundWrapper.of(getTag(item)).setInt(key, value);
    }

    public static void setShort(ItemStack item, String key, short value) {
      NBTTagCompoundWrapper.of(getTag(item)).setShort(key, value);
    }

    public static void setByteArray(ItemStack item, String key, byte[] value) {
      NBTTagCompoundWrapper.of(getTag(item)).setByteArray(key, value);
    }

    public static void setIntArray(ItemStack item, String key, int[] value) {
      NBTTagCompoundWrapper.of(getTag(item)).setIntArray(key, value);
    }

    public static String getString(ItemStack item, String key) {
      return NBTTagCompoundWrapper.of(getTag(item)).getString(key);
    }

    public static boolean getBoolean(ItemStack item, String key) {
      return NBTTagCompoundWrapper.of(getTag(item)).getBoolean(key);
    }

    public static byte getByte(ItemStack item, String key) {
      return NBTTagCompoundWrapper.of(getTag(item)).getByte(key);
    }

    public static float getFloat(ItemStack item, String key) {
      return NBTTagCompoundWrapper.of(getTag(item)).getFloat(key);
    }

    public static int getInteger(ItemStack item, String key) {
      return NBTTagCompoundWrapper.of(getTag(item)).getInt(key);
    }

    public static short getShort(ItemStack item, String key) {
      return NBTTagCompoundWrapper.of(getTag(item)).getShort(key);
    }

    public static byte[] getByteArray(ItemStack item, String key) {
      return NBTTagCompoundWrapper.of(getTag(item)).getByteArray(key);
    }

    public static int[] getIntArray(ItemStack item, String key) {
      return NBTTagCompoundWrapper.of(getTag(item)).getIntArray(key);
    }
  }
}
