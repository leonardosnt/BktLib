package io.github.bktlib.command.tabcompleter;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class TabCompleterPlaceholders {
  public static final Supplier<List<String>> PLAYERS = () -> (
    Bukkit.getOnlinePlayers().stream().map(Player::getName)
        .collect(Collectors.toList())
  );

  @Nullable
  public static List<String> fromName(String placeHolder) {
    switch (placeHolder) {
      case "$players$":
        return PLAYERS.get();

      default:
        return null;
    }
  }
}
