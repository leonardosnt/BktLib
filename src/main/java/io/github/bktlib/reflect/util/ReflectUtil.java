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

package io.github.bktlib.reflect.util;

import com.google.gson.internal.Primitives;
import io.github.bktlib.misc.BukkitUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

public final class ReflectUtil {
  /**
   * Verifica se {@code klass} tem pelo menos 1 construtor publico.
   *
   * @param klass Classe que deseja verificar.
   * @return se {@code klass} tem pelo menos 1 construtor publico.
   */
  public static boolean hasPublicConstructor(final Class<?> klass) {
    return klass.getConstructors().length != 0;
  }

  /**
   * Verifica se {@code klass} é uma classe concreta. (não é um enum,
   * anotação, interface ou é abstrata.)
   *
   * @param klass Classe que deseja verificar.
   * @return se {@code klass} é uma classe concreta
   */
  public static boolean isConcreteClass(final Class<?> klass) {
    final int INVALID_MODIFIER_FLAGS =
                    0x4000  /* Enum */       |
                    0x2000  /* Annotation */ |
                    0x400   /* Abstract */   |
                    0x200   /* Interface */  ;

    return (klass.getModifiers() & INVALID_MODIFIER_FLAGS) == 0;
  }

  /**
   * @param method Método que deseja pegar os modificadores.
   * @return Os modificadores de um método ou -1 caso ocorra algum erro.
   * @see java.lang.reflect.Modifier
   */
  public static int getModifiers(final Method method) {
    try {
      final Field modifiers = Method.class.getDeclaredField("modifiers");
      modifiers.setAccessible(true);
      return (int) modifiers.get(method);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1;
  }

  /**
   * @param field Field que deseja pegar os modificadores.
   * @return Os modificadores de um field ou -1 caso ocorra algum erro.
   * @see java.lang.reflect.Modifier
   */
  public static int getModifiers(final Field field) {
    try {
      final Field modifiers = Field.class.getDeclaredField("modifiers");
      modifiers.setAccessible(true);
      return (int) modifiers.get(field);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1;
  }

  /**
   * Define os modificadores de um método.
   *
   * @param method Método que deseja pegar os modificadores.
   * @see java.lang.reflect.Modifier
   */
  public static void setModifiers(final Method method, final int mods) {
    try {
      final Field modifiers = Method.class.getDeclaredField("modifiers");
      modifiers.setAccessible(true);
      modifiers.set(method, mods);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Define os modificadores de um Field.
   *
   * @param field Field que deseja pegar os modificadores.
   * @see java.lang.reflect.Modifier
   */
  public static void setModifiers(final Field field, final int mods) {
    try {
      final Field modifiers = Field.class.getDeclaredField("modifiers");
      modifiers.setAccessible(true);
      modifiers.set(field, mods);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static Class<?> getClass(String clazz) {
    clazz = resolveName(clazz);
    try {
      return Class.forName(clazz);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String resolveName(String clazz) {
    final String version = BukkitUtil.CB_VERSION;
    clazz = clazz.replaceAll("\\{o?cb\\}", "org.bukkit.craftbukkit." + version);
    clazz = clazz.replaceAll("\\{nms\\}", "net.minecraft.server." + version);
    return clazz;
  }

  public static Object instantiate(final String clazz, Object ... params) {
    try {
      Class<?> targetClass = ReflectUtil.getClass(clazz);
      if (targetClass == null) {
        return null;
      }
      if (!isConcreteClass(targetClass)) {
        throw new IllegalArgumentException(targetClass + " is not a concrete class.");
      }
      if (params == null || params.length == 0) {
        return targetClass.newInstance();
      }
      Class<?>[] classes = Stream.of(params)
        .map(Object::getClass)
        .map(Primitives::unwrap)
        .toArray(Class[]::new);
      Constructor<?> ctor = targetClass.getConstructor(classes);
      ctor.setAccessible(true);
      return ctor.newInstance(params);
    } catch (InstantiationException | IllegalAccessException |
             NoSuchMethodException | InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

  private ReflectUtil() {
    throw new UnsupportedOperationException();
  }
}
