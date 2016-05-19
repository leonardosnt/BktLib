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
import io.github.bktlib.reflect.util.ReflectUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.bukkit.entity.Player;

public final class NetworkUtils {

  public static Channel getPlayerConnectionChannel(Player player) {
    final Object playerHandle = ReflectUtil.getNmsHandle(player);
    return Fields.from(playerHandle)
            .find("playerConnection").getAsFields()
            .find("networkManager").getAsFields()
            .find("channel").get();
  }

  public static void attachChannelToPlayerConnection(Player player, String id,
                                                     ChannelHandler handler) {
    getPlayerConnectionChannel(player).pipeline()
        .addBefore("packet_handler", id, handler);
  }

  public static void detachChannelFromPlayerConnection(Player player, String id) {
    getPlayerConnectionChannel(player).pipeline().remove(id);
  }

  public static void detachChannelFromPlayerConnection(Player player,
                                                       ChannelHandler handler) {
    getPlayerConnectionChannel(player).pipeline().remove(handler);
  }
}
