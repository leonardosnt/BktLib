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

package io.github.bktlib.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public interface FieldAccessor<T>
{
	/**
	 * Retorna o valor do campo.
	 * 
	 * @return O valor do campo.
	 */
	Optional<T> getValue();

	/**
	 * @param newValue
	 * @throws IllegalAccessException
	 *             Se o {@code field} for estatico e o objeto passado no
	 *             {@link #access(Object, String)} nao é uma instancia de um
	 *             objeto.
	 */
	void setValue( T newValue );

	/**
	 * @return
	 */
	Field getField();

	/**
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	static <T> FieldAccessor<T> access( final Object obj, final String fieldName )
	{
		Preconditions.checkNotNull( obj, "obj cannot be null" );
		Preconditions.checkArgument( !Strings.isNullOrEmpty( fieldName ), 
				"fieldName cannot be null or empty" );

		Class<?> klass = obj instanceof Class ? (Class<? extends Object>) obj : obj.getClass();

		Field ret = findFieldRecursive( klass, fieldName );

		if ( ret == null )
			throw new RuntimeException( String.format( "could not find field %s.%s", klass, fieldName ) );

		return new FieldAccessorImpl<>( obj, ret );
	}

	static Field findFieldRecursive( final Class<?> klass, final String fieldName )
	{
		if ( klass == null )
			return null;

		try
		{
			return klass.getDeclaredField( fieldName );
		}
		catch ( NoSuchFieldException e )
		{
			return findFieldRecursive( klass.getSuperclass(), fieldName );
		}
		catch ( SecurityException e )
		{
			e.printStackTrace();
		}

		return null;
	}
}
