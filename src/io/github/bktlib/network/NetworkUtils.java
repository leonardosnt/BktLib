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
