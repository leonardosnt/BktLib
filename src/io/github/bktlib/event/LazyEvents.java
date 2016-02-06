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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.function.Consumer;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * <pre>
 * LazyEvents.on( PlayerDeathEvent.class ).doIt( e -> {
 * 	e.getEntity().sendMessage( "Você morreu D:" );
 * });
 * </pre>
 */
public class LazyEvents
{
	private static Plugin owner;

	public static void init( @Nonnull Plugin plugin )
	{
		checkNotNull( plugin, "plugin cannot be null.");
		checkState( owner == null, "LazyEvents already initalized." );
		
		owner = plugin;
	}

	public static <T extends Event> LazyEvent<T> on( @Nonnull Class<T> eventClass )
	{
		checkNotNull( eventClass, "eventClass cannot be null" );
		
		return new LazyEvent<T>( eventClass );
	}
	
	public static class LazyEvent<T extends Event>
	{
		public Class<T> eventClass;

		public LazyEvent( Class<T> cls )
		{
			this.eventClass = cls;
		}

		public void doIt( @Nonnull Consumer<T> eventConsumer )
		{
			checkNotNull( eventConsumer, "eventConsumer cannot be null" );
			
			Bukkit.getPluginManager().registerEvent( 
					eventClass, 
					new Listener() {}, 
					EventPriority.LOW, 
					(l, e) -> {
						if ( e.getClass() == eventClass ) 
							eventConsumer.accept((T) e);
					}, 
					owner 
			);
		}
	}
}