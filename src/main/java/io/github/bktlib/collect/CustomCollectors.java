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

package io.github.bktlib.collect;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public final class CustomCollectors {

  private static final Set<Collector.Characteristics> EMPTY_CHARACTERISTICS = Collections.emptySet();

  public static<T> Collector<T, ImmutableList.Builder<T>, ImmutableList<T>> toImmutableList() {
    return new ImmutableListCollector<>();
  }

  static class ImmutableListCollector<T> implements Collector<T, ImmutableList.Builder<T>, ImmutableList<T>> {

    @Override
    public Supplier<ImmutableList.Builder<T>> supplier() {
      return ImmutableList.Builder::new;
    }

    @Override
    public BiConsumer<ImmutableList.Builder<T>, T> accumulator() {
      return ImmutableList.Builder::add;
    }

    @Override
    public BinaryOperator<ImmutableList.Builder<T>> combiner() {
      return (l, r) -> {
        l.addAll(r.build());
        return l;
      };
    }

    @Override
    public Function<ImmutableList.Builder<T>, ImmutableList<T>> finisher() {
      return ImmutableList.Builder::build;
    }

    @Override
    public Set<Characteristics> characteristics() {
      return EMPTY_CHARACTERISTICS;
    }
  }
}
