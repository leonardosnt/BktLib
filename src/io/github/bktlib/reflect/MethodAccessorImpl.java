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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

class MethodAccessorImpl<T> extends AbstractAccessor implements MethodAccessor<T>
{
	private Method method;
	
	MethodAccessorImpl( final Object owner, final Method method )
	{
		super( owner );
		
		this.method = method;
	}

	@Override
	public Optional<T> invoke( Object... params )
	{
		if ( !method.isAccessible() )
			method.setAccessible( true );
		
		try
		{
			return (Optional<T>) Optional.ofNullable( method.invoke( owner, params ) );
		}
		catch ( IllegalAccessException | IllegalArgumentException | InvocationTargetException e )
		{
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public Method getMethod()
	{
		return method;
	}
}
