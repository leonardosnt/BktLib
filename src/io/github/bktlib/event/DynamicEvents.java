package io.github.bktlib.event;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.function.Consumer;

public final class DynamicEvents {
  private static Map<Plugin, DynamicEvents> cache = Maps.newHashMap();
  private Map<String, Listener> registered;
  private Plugin owner;

  public static DynamicEvents from(Plugin plugin) {
    Preconditions.checkNotNull(plugin, "plugin");

    return cache.putIfAbsent(plugin, new DynamicEvents(plugin));
  }

  private DynamicEvents(Plugin plugin) {
    owner = plugin;
    registered = Maps.newHashMap();
  }

  @SuppressWarnings("unchecked")
  public <E extends Event> void register(
          String id, Class<E> eventClass,  Consumer<E> consumer) {
    register0(eventClass, id, (l, e) -> consumer.accept((E) e));
  }

  @SuppressWarnings("unchecked")
  public <E extends Event> void registerForPlayer(
      Player player, String id, Class<E> eventClass, Consumer<E> consumer) {
    Preconditions.checkArgument(isPlayerEvent(eventClass),
            eventClass + " is not a player event.");
    register0(eventClass, id, (l, e) -> {
      if (getPlayerFromEvent(e) == player) {
        consumer.accept((E) e);
      }
    });
  }

  public <E extends Event> void registerForBlock(
      Block block, String id, Class<E> eventClass, Consumer<E> consumer) {
    throw new NotImplementedException();
  }

  public <E extends Event> void registerForEntity(
      Entity entity, String id, Class<E> eventClass, Consumer<E> consumer) {
    throw new NotImplementedException();
  }

  public void unregister(String id) {
    final Listener listener = registered.get(id);
    if (listener == null) {
      return;
    }
    HandlerList.unregisterAll(listener);
  }

  private <E extends Event> void register0(
      Class<E> eventClass, String id, EventExecutor executor) {
    if (registered.containsKey(id)) {
      throw new IllegalArgumentException(String.format("The id '%s' is already in use.", id));
    }

    final Listener listener = new Listener() {};
    registered.put(id, listener);

    Bukkit.getPluginManager().registerEvent(
        eventClass, listener, EventPriority.NORMAL, executor, owner);
  }

  private Player getPlayerFromEvent(Event e) {
    final Class<?> clazz = e.getClass();
    if (clazz.isAssignableFrom(PlayerEvent.class)) {
      return ((PlayerEvent) e).getPlayer();
    }
    if (clazz == PlayerLeashEntityEvent.class) {
      return ((PlayerLeashEntityEvent) e).getPlayer();
    }
    if (clazz == PlayerUnleashEntityEvent.class) {
      return ((PlayerUnleashEntityEvent) e).getPlayer();
    }
    if (clazz == PlayerDeathEvent.class) {
      return ((PlayerDeathEvent) e).getEntity();
    }
    if (clazz == BlockBreakEvent.class) {
      return ((BlockBreakEvent) e).getPlayer();
    }
    if (clazz == BlockPlaceEvent.class) {
      return ((BlockPlaceEvent) e).getPlayer();
    }
    if (clazz == SignChangeEvent.class) {
      return ((SignChangeEvent) e).getPlayer();
    }
    return null;
  }

  private boolean isPlayerEvent(Class<?> clazz) {
    return clazz.isAssignableFrom(PlayerEvent.class)
            || clazz == PlayerLeashEntityEvent.class
            || clazz == PlayerUnleashEntityEvent.class
            || clazz == PlayerDeathEvent.class
            || clazz == BlockBreakEvent.class
            || clazz == BlockPlaceEvent.class
            || clazz == SignChangeEvent.class;
  }
}
