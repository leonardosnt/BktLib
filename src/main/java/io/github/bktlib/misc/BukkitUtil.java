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

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public final class BukkitUtil {

  /**
   * Versão do CraftBukkit. Tipo (v1_8_R3)
   */
  public static final String CB_VERSION;

  static {
    final String craftServerPkg = Bukkit.getServer().getClass().getPackage().getName();
    CB_VERSION = craftServerPkg.substring(craftServerPkg.lastIndexOf('.') + 1);
  }

  /**
   * Pega o 'nms handle' do objeto.

   * @param obj Objeto
   * @return 'nms handle' do objeto.
   */
  public static Object unwrap(Object obj) {
    if (obj instanceof ItemStack) {
      try {
        Field handle = obj.getClass().getDeclaredField("handle");
        handle.setAccessible(true);
        return handle.get(obj);
      } catch (NoSuchFieldException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    try {
      Method getHandle = obj.getClass().getMethod("getHandle");
      return getHandle.invoke(obj);
    } catch (NoSuchMethodException | InvocationTargetException |
            IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }
}
