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

import java.util.function.Supplier;

public class MemoizedSupplier<T> implements Supplier<T> {
  private volatile boolean initialized;
  private T value;
  private Supplier<T> backing;

  public static <T> MemoizedSupplier<T> of(Supplier<T> backing) {
    return new MemoizedSupplier<T>(backing);
  }

  MemoizedSupplier(Supplier<T> backing) {
    this.backing = backing;
  }

  @Override
  public T get() {
    if (!initialized) {
      synchronized (this) {
        if (!initialized) {
          value = backing.get();
          initialized = true;
        }
      }
    }
    return value;
  }
}
