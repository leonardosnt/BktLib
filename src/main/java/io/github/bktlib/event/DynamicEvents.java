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

package io.github.bktlib.event;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.apache.commons.lang.RandomStringUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class DynamicEvents {
  private static final Map<Plugin, DynamicEvents> CACHE = Maps.newHashMap();
  private static final Supplier<String> ID_GENERATOR = () -> RandomStringUtils.randomAscii(15);
  private Map<String, Listener> registered;
  private Plugin owner;

  /**
   * Cria um uma nova instancia do {@link DynamicEvents} ou
   * pega a existente no cache.
   *
   * @param plugin Plugin
   * @return uma nova instancia do {@link DynamicEvents} ou
   * pega a existente no cache.
   */
  public static DynamicEvents of(@Nonnull Plugin plugin) {
    Preconditions.checkNotNull(plugin, "plugin");

    CACHE.putIfAbsent(plugin, new DynamicEvents(plugin));
    return CACHE.get(plugin);
  }

  /**
   * Cria um uma nova instancia do {@link DynamicEvents} ou
   * pega a existente no cache.
   *
   * @param pluginClass Classe do plugin
   * @return uma nova instancia do {@link DynamicEvents} ou
   * pega a existente no cache.
   */
  public static DynamicEvents of(@Nonnull  Class<? extends JavaPlugin> pluginClass) {
    Preconditions.checkNotNull(pluginClass, "pluginClass");
    return of(JavaPlugin.getProvidingPlugin(pluginClass));
  }

  private DynamicEvents(Plugin plugin) {
    owner = plugin;
    registered = Maps.newHashMap();
  }

  /**
   * Registra a ação({@code action}) que sera executada quando o
   * determinado evento({@code eventClass}) for executado.
   *
   * @param eventClass Classe do evento
   * @param callback Ação que será executada.
   * @param <E> Tipo do evento
   */
  public <E extends Event> void register(Class<E> eventClass, Consumer<E> callback) {
    register(ID_GENERATOR.get(), eventClass, callback);
  }

  /**
   * Registra a ação({@code action}) que sera executada quando o
   * determinado evento({@code eventClass}) for executado.
   *
   * @param id Um id unico, que pode ou não ser usado no {@link #unregister(String)}
   * @param eventClass Classe do evento
   * @param callback Ação que será executada.
   * @param <E> Tipo do evento
   */
  @SuppressWarnings("unchecked")
  public <E extends Event> void register(String id, Class<E> eventClass,
                                         Consumer<E> callback) {
    register0(eventClass, id, (l, e) -> {
      if (eventClass.isAssignableFrom(e.getClass())) {
        callback.accept((E) e);
      }
    });
  }

  /**
   * Registra a ação({@code action}) que sera executada quando o
   * jogador executar determinado evento({@code eventClass}).
   *
   * @param player Jogador desejado.
   * @param id Um id unico, que pode ou não ser usado no {@link #unregister(String)}
   * @param eventClass Classe do evento
   * @param callback Ação que será executada.
   * @param <E> Tipo do evento
   */
  @SuppressWarnings("unchecked")
  public <E extends Event> void registerFor(Player player, String id,
                                            Class<E> eventClass,
                                            Consumer<E> callback) {
    Preconditions.checkNotNull(player, "player cannot be null");
    Preconditions.checkNotNull(callback, "callback cannot be null");
    Preconditions.checkArgument(isPlayerEvent(eventClass), eventClass +
        " is not a player event.");

    register0(eventClass, id, new PlayerEventExecutor(player, id,
        (Consumer<Event>) callback));
  }

  public <E extends Event> void registerFor(Player player,
                                            Class<E> eventClass,
                                            Consumer<E> callback) {
    registerFor(player, ID_GENERATOR.get(), eventClass, callback);
  }

  /**
   * Registra a ação({@code action}) que sera executada quando a
   * entidade executar determinado evento({@code eventClass}).
   *
   * @param entity Jogador desejado.
   * @param id Um id unico, que pode ou não ser usado no {@link #unregister(String)}
   * @param eventClass Classe do evento
   * @param callback Ação que será executada.
   * @param <E> Tipo do evento
   */
  @SuppressWarnings("unchecked")
  public <E extends Event> void registerFor(Entity entity, String id,
                                            Class<E> eventClass,
                                            Consumer<E> callback) {
    Preconditions.checkArgument(isEntityEvent(eventClass), eventClass +
        " is not a entity event.");

    register0(eventClass, id, new EntityEventExecutor(entity, id,
        (Consumer<Event>) callback));
  }


  public <E extends Event> void registerFor(Entity entity,
                                            Class<E> eventClass,
                                            Consumer<E> callback) {
    registerFor(entity, ID_GENERATOR.get(), eventClass, callback);
  }
  /**
   * Desregistra uma determinada 'ação' registrada por
   * {@link #register(String, Class, Consumer)}
   *
   * @param id Id da 'ação'
   */
  public synchronized void unregister(String id) {
    final Listener listener = registered.get(id);
    if (listener == null) {
      return;
    }
    HandlerList.unregisterAll(listener);
    registered.remove(id);
  }

  /**
   * Desregistra todas as 'ações' registradas.
   */
  public synchronized void unregisterAll() {
    registered.values().forEach(HandlerList::unregisterAll);
    registered.clear();
  }


  private <E extends Event> void register0(Class<E> eventClass, String id,
                                           EventExecutor executor) {
    if (registered.containsKey(id)) {
      throw new IllegalArgumentException(String.format(
          "The id '%s' is already in use.", id));
    }

    final Listener listener = new Listener() {};
    registered.put(id, listener);

    Bukkit.getPluginManager().registerEvent(
        eventClass, listener, EventPriority.NORMAL, executor, owner);
  }

  private Player getPlayerFromEvent(Event e) {
    final Class<?> clazz = e.getClass();
    if (PlayerEvent.class.isAssignableFrom(clazz)) {
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

  private Entity getEntityFromEvent(Event e) {
    return ((EntityEvent) e).getEntity();
  }

  private boolean isPlayerEvent(Class<?> clazz) {
    return clazz != null && PlayerEvent.class.isAssignableFrom(clazz)
            || clazz == PlayerLeashEntityEvent.class
            || clazz == PlayerUnleashEntityEvent.class
            || clazz == PlayerDeathEvent.class
            || clazz == BlockBreakEvent.class
            || clazz == BlockPlaceEvent.class
            || clazz == SignChangeEvent.class;
  }

  private boolean isEntityEvent(Class<?> clazz) {
    return EntityEvent.class.isAssignableFrom(clazz);
  }


  private abstract class WeakEventExecutor<T> implements EventExecutor {
    protected WeakReference<T> ownerRef;
    protected Consumer<Event> callback;
    protected String id;

    private WeakEventExecutor(T ownerRef, String id, Consumer<Event> callback) {
      this.ownerRef = new WeakReference<>(ownerRef);
      this.id = id;
      this.callback = callback;
    }

    @Override
    public void execute(Listener listener, Event event) throws EventException {
      final T owner = ownerRef.get();
      if (owner == null) {
        unregister(id);
      } else {
        execute(event);
      }
    }

    public abstract void execute(Event event) throws EventException;
  }

  private class EntityEventExecutor extends WeakEventExecutor<Entity> {

    private EntityEventExecutor(Entity ownerRef, String id, Consumer<Event> callback) {
      super(ownerRef, id, callback);
    }

    @Override
    public void execute(Event event) throws EventException {
      if (getEntityFromEvent(event) == ownerRef.get()) {
        callback.accept(event);
      }
    }
  }

  private class PlayerEventExecutor extends WeakEventExecutor<Player> {

    private PlayerEventExecutor(Player ownerRef, String id, Consumer<Event> callback) {
      super(ownerRef, id, callback);
    }

    @Override
    public void execute(Event event) throws EventException {
      if (getPlayerFromEvent(event) == ownerRef.get()) {
        callback.accept(event);
      }
    }
  }
}
