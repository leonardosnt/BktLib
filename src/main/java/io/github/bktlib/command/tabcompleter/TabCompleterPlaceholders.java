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

package io.github.bktlib.command.tabcompleter;

import io.github.bktlib.collect.CustomCollectors;
import io.github.bktlib.misc.MemoizedSupplier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TabCompleterPlaceholders {
  public static final Supplier<List<String>> PLAYERS = () -> (
    Bukkit.getOnlinePlayers().stream().map(Player::getName)
          .collect(Collectors.toList())
  );

  public static final Supplier<List<String>> ITEMS = MemoizedSupplier.of(() -> (
    Stream.of(Material.values())
          .map(Material::name)
          .map(String::toLowerCase)
          .collect(CustomCollectors.toImmutableList()))
  );

  @Nullable
  public static Supplier<List<String>> fromName(String placeHolder) {
    switch (placeHolder.toLowerCase()) {
      case "$players$":
        return PLAYERS;

      case "$items$":
        return ITEMS;

      default:
        return null;
    }
  }
}
