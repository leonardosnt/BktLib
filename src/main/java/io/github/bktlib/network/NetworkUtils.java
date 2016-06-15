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

package io.github.bktlib.network;

import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.github.bktlib.lazy.LazyInitField;
import io.github.bktlib.misc.BukkitUnwrapper;
import io.github.bktlib.lazy.LazyInitMethod;
import io.github.bktlib.reflect.util.ReflectUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

public final class NetworkUtils {

  private static final String PACKET_HANDLER_ID = "packet_handler";
  private static final String PLAYER_CONNECTION_CLS_NAME = ReflectUtil.resolveName("{nms}.PlayerConnection");

  private static final LazyInitField PLAYER_CONNECTION = new LazyInitField(
      ReflectUtil.resolveName("{nms}.EntityPlayer"), "playerConnection");

  private static final LazyInitMethod SEND_PACKET = new LazyInitMethod(
      PLAYER_CONNECTION_CLS_NAME, "sendPacket", ReflectUtil.resolveName("{nms}.Packet"));

  private static final LoadingCache<Player, Channel> CHANNEL_CACHE =
      CacheBuilder.newBuilder()
                  .weakKeys()
                  .weakValues()
                  .expireAfterAccess(5, TimeUnit.MINUTES)
                  .build(new ChannelCacheLoader());

  /**
   * Pega a o {@link Channel canal} da conexão do jogador
   *
   * @param player Jogador
   * @return {@link Channel canal} da conexão do jogador
   */
  public static Channel getPlayerConnectionChannel(Player player) {
    Preconditions.checkNotNull(player, "player cannot be null");
    return CHANNEL_CACHE.getUnchecked(player);
  }

  /**
   * Adicionar um {@link ChannelHandler} no {@link io.netty.channel.ChannelPipeline}
   * da conexão do jogador.
   *
   * @param player Jogador
   * @param id Id do handler
   * @param handler O handler
   */
  public static void addChannelToPlayerConnection(Player player, String id, ChannelHandler handler) {
    Preconditions.checkNotNull(player, "player cannot be null");
    Preconditions.checkNotNull(handler, "handler cannot be null");

    getPlayerConnectionChannel(player).pipeline().addBefore(PACKET_HANDLER_ID, id, handler);
  }

  /**
   * Remove um {@link ChannelHandler} da {@link io.netty.channel.ChannelPipeline}
   * da conexão do jogador.
   *
   * @param player Jogador
   * @param id Id do handler
   */
  public static void removeChannelFromPlayerConnection(Player player, String id) {
    Preconditions.checkNotNull(player, "player cannot be null");
    Preconditions.checkNotNull(id, "id cannot be null");

    Channel channel = getPlayerConnectionChannel(player);
    if (channel.pipeline().get(id) != null) {
      channel.pipeline().remove(id);
    }
  }

  /**
   * Remove um {@link ChannelHandler} da {@link io.netty.channel.ChannelPipeline}
   * da conexão do jogador.
   *
   * @param player Jogador
   * @param handler Instancia do handler
   */
  public static void removeChannelFromPlayerConnection(Player player, ChannelHandler handler) {
    Preconditions.checkNotNull(player, "player cannot be null");
    Preconditions.checkNotNull(handler, "handler cannot be null");

    getPlayerConnectionChannel(player).pipeline().remove(handler);
  }

  /**
   * Envia um pacote ao jogador.
   *
   * @param player Jogador
   * @param packet Pacote
   */
  public static void sendPacket(Player player, Object packet) {
    Preconditions.checkNotNull(player, "player cannot be null");
    Preconditions.checkNotNull(packet, "packet cannot be null");

    try {
      Object playerCon = PLAYER_CONNECTION.get().get(BukkitUnwrapper.unwrap(player));
      SEND_PACKET.get().invoke(playerCon, packet);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  private static class ChannelCacheLoader extends CacheLoader<Player, Channel> {

    private static final LazyInitField NETWOK_MANAGER = new LazyInitField(
        PLAYER_CONNECTION_CLS_NAME, "networkManager");

    private static final LazyInitField CHANNEL = new LazyInitField(
        ReflectUtil.resolveName("{nms}.NetworkManager"), "channel");

    @Override
    public Channel load(Player player) throws Exception {
      Object unwrapped = BukkitUnwrapper.unwrap(player);
      Object playerCon = PLAYER_CONNECTION.get().get(unwrapped);
      Object netManager = NETWOK_MANAGER.get().get(playerCon);
      Object channel = CHANNEL.get().get(netManager);
      return (Channel) channel;
    }
  }
}
