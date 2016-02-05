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

package io.github.bktlib.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import static com.google.common.base.Preconditions.*;

public final class Locations
{
	private Locations() {}
	
	public static Location of( final String world, final double x, final double y, 
								final double z )
	{
		checkNotNull( world, "world cannot be null" );
		
		return new Location( Bukkit.getWorld( world ), x, y, z );
	}
	
	public static Location of( final double x, final double y, final double z )
	{
		return of( "world", x, y, z );
	}
	
	public static Location of( final String world, final Vector vec )
	{
		checkNotNull( world, "world cannot be null" );
		
		return new Location( 
				Bukkit.getWorld( world ), 
				vec.getX(), 
				vec.getY(),
				vec.getZ() 
		);
	}

	/**
	 * Retorna uma nova {@link Location} com o ponto máximo entre as duas.
	 * 
	 * @param l1 Localização 1
	 * @param l2 Localização 2
	 * 
	 * @return uma nova {@link Location} com o ponto máximo entre as duas.
	 * 
	 * @see #min(Location, Location)
	 */
	public static Location max( final Location l1, final Location l2 )
	{
		checkNotNull( l1, "l1 cannot be null" );
		checkNotNull( l2, "l2 cannot be null" );
		
		return new Location( 
				l1.getWorld(), 
				Math.max( l1.getX(), l2.getX() ),
				Math.max( l1.getY(), l2.getY() ),
				Math.max( l1.getZ(), l2.getZ() )
		);
	}
	
	/**
	 * Retorna uma nova {@link Location} com o ponto mínimo entre as duas.
	 * 
	 * @param l1 Localização 1
	 * @param l2 Localização 2
	 * 
	 * @return uma nova {@link Location} com o ponto mínimo entre as duas.
	 * 
	 * @see #max(Location, Location)
	 */
	public static Location min( final Location l1, final Location l2 )
	{
		checkNotNull( l1, "l1 cannot be null" );
		checkNotNull( l2, "l2 cannot be null" );
		
		return new Location( 
				l1.getWorld(), 
				Math.min( l1.getX(), l2.getX() ),
				Math.min( l1.getY(), l2.getY() ),
				Math.min( l1.getZ(), l2.getZ() )
		);
	}
}
