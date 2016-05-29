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

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public abstract class PacketHandler extends ChannelHandlerAdapter {

  /**
   * Chamando quando um packet é recebido.
   *
   * @param packet Pacote recebido
   * @return {@code true} para cancelar o recebimento do pacote.
   */
  public abstract boolean onPacketReceived(Object packet);

  /**
   * Chamando quando um packet é enviado.
   *
   * @param packet Pacote enviado
   * @return {@code true} para cancelar o envio do pacote.
   */
  public abstract boolean onPacketSent(Object packet);

  @Override
  public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
    if (onPacketReceived(msg)) {
      return;
    }
    super.write(ctx, msg, promise);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (onPacketSent(msg)) {
      return;
    }
    super.channelRead(ctx, msg);
  }
}