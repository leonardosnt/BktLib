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

package io.github.bktlib.lazy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class LazyInitField extends LazyInitValue<Field> {
  private Class<?> clazz;
  private String fieldName;

  /* LazyInit stuffs */
  private String className;

  public LazyInitField(@Nonnull Class<?> clazz, @Nonnull String fieldName) {
    this.clazz = clazz;
    this.fieldName = fieldName;
  }

  public LazyInitField(@Nonnull String className, @Nonnull String fieldName) {
    this.className = className;
    this.fieldName = fieldName;
  }

  @Nullable
  @Override
  protected Field init() {
    if (clazz == null) {
      try {
        clazz = Class.forName(className);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    try {
      Field ret = clazz.getDeclaredField(fieldName);
      ret.setAccessible(true);/* TODO: Abstrair isso ? Usando decorator ou não... */
      return ret;
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }
    return null;
  }
}
