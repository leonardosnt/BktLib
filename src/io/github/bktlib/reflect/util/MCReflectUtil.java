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

package io.github.bktlib.reflect.util;

import java.util.Optional;

import io.github.bktlib.common.Strings;
import org.bukkit.Bukkit;

public final class MCReflectUtil
{
	/**
	 * Pega uma classe do CRAFTBUKKIT 
	 * {@code org.bukkit.craftbukkit.(versao).(*)}
	 * 
	 * @param className
	 *            Nome da classe.
	 * @return Um {@link Optional} contendo a classe caso encontre, senão
	 *         retorna um {@link Optional#empty()}
	 */
	public static Optional<Class<?>> getCBClass( final String className )
	{
        try
        {
            Class<?> klass = Class.forName( Strings.of( "org.bukkit.craftukkit." ,
                                                         getCBVersion()          ,
                                                         '.'                     ,
                                                         className               ));

            return Optional.of(klass);
        }
        catch ( ClassNotFoundException e )
        {
            return Optional.empty();
        }
	}

    /**
     * Pega uma classe do MINECRAFTSERVER
     * {@code net.minecraft.server.(versao).{@code className}}
     *
     * @param className
     *            Nome da classe.
     * @return Um {@link Optional} contendo a classe caso encontre, senão
     *         retorna um {@link Optional#empty()}
     */
	public static Optional<Class<?>> getNMSClass( final String className )
	{
		try
		{
			Class<?> klass = Class.forName( Strings.of( "net.minecraft.server." ,
														getCBVersion()          ,
														'.'                     ,
														className               ));

			return Optional.of(klass);
		}
		catch ( ClassNotFoundException e )
		{
			return Optional.empty();
		}
	}

	/**
	 * Pega a versao do CRAFTBUKKIT baseado no pacote da classe CraftServer, por
	 * exemplo, {@code org.bukkit.craftbukkit.v1_5_R3.CraftServer}, vai retornar
	 * {@code V1_5_R3}.
	 * 
	 * @return versão do craftbukkit
	 */
	public static String getCBVersion()
	{
		final String craftServerPkg = Bukkit.getServer().getClass().getPackage().getName();

		return craftServerPkg.substring( craftServerPkg.lastIndexOf( '.' ) + 1 );
	}
	
	private MCReflectUtil() 
	{
		throw new UnsupportedOperationException();
	}
}
