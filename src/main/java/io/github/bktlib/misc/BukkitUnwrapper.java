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

package io.github.bktlib.misc;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import io.github.bktlib.lazy.LazyInitField;
import io.github.bktlib.reflect.util.ReflectUtil;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public final class BukkitUnwrapper {
  private static final Map<Class<?>, Method> getHandleMethods = Maps.newHashMap();
  private static final LazyInitField itemStackHandle =
      new LazyInitField(ReflectUtil.resolveName("{cb}.inventory.CraftItemStack"), "handle");

  /**
   * Pega o nms handle do Objeto. Ex:
   * <p>org.bukkit.entity.Player -> net.minecraft.server.EntityPlayer</p>
   *
   * @param bukkitObj Bukkit object.
   * @return nms handle do Objeto.
   */
  @Nullable
  public static Object unwrap(@Nonnull Object bukkitObj) {
    Preconditions.checkNotNull(bukkitObj, "bukkitObj cannot be null");
    try {
      if (bukkitObj instanceof ItemStack) {
          return itemStackHandle.get().get(bukkitObj);
      }
      Class<?> objClass = bukkitObj.getClass();
      Method method;
      if ((method = getHandleMethods.get(objClass)) == null) {
        method = objClass.getDeclaredMethod("getHandle");
        getHandleMethods.put(objClass, method);
      }
      return method.invoke(bukkitObj);
    } catch (InvocationTargetException | NoSuchMethodException |
             IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }
}
