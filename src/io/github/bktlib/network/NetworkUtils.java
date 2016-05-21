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

import io.github.bktlib.reflect.Fields;
import io.github.bktlib.reflect.LazyInitMethod;
import io.github.bktlib.reflect.util.ReflectUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public final class NetworkUtils {

  private static final LazyInitMethod sendPacket = new LazyInitMethod(
      ReflectUtil.resolveClassName("{nms}.PlayerConnection"), "sendPacket",
      ReflectUtil.resolveClassName("{nms}.Packet"));

  /**
   * Pega a o {@link Channel canal} da conexão do jogador
   *
   * @param player Jogador
   * @return {@link Channel canal} da conexão do jogador
   */
  public static Channel getPlayerConnectionChannel(Player player) {
    return Fields.from(ReflectUtil.getNmsHandle(player))
            .find("playerConnection").getAsFields()
            .find("networkManager").getAsFields()
            .find("channel").get();
  }

  /**
   * Adicionar um {@link ChannelHandler} no {@link io.netty.channel.ChannelPipeline}
   * da conexão do jogador.
   *
   * @param player Jogador
   * @param id Id do handler
   * @param handler O handler
   */
  public static void addChannelToPlayerConnection(Player player, String id,
                                                  ChannelHandler handler) {
    getPlayerConnectionChannel(player).pipeline()
        .addBefore("packet_handler", id, handler);
  }

  /**
   * Remove um {@link ChannelHandler} da {@link io.netty.channel.ChannelPipeline}
   * da conexão do jogador.
   *
   * @param player Jogador
   * @param id Id do handler
   */
  public static void removeChannelFromPlayerConnection(Player player, String id) {
    getPlayerConnectionChannel(player).pipeline().remove(id);
  }

  /**
   * Remove um {@link ChannelHandler} da {@link io.netty.channel.ChannelPipeline}
   * da conexão do jogador.
   *
   * @param player Jogador
   * @param handler Instancia do handler
   */
  public static void removeChannelFromPlayerConnection(Player player,
                                                       ChannelHandler handler) {
    getPlayerConnectionChannel(player).pipeline().remove(handler);
  }

  /**
   * Envia um pacote ao jogador.
   *
   * @param player Jogador
   * @param packet Pacote
   */
  public static void sendPacket(Player player, Object packet) {
    Object playerCon = Fields.from(ReflectUtil.getNmsHandle(player))
        .find("playerConnection").get();
    try {
      sendPacket.get().invoke(playerCon, packet);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }
}
