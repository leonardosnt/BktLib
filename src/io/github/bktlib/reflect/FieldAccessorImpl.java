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
import java.lang.reflect.Modifier;
import java.util.Optional;

class FieldAccessorImpl<T> extends AbstractAccessor implements FieldAccessor<T>
{
	private Field field;
	
	FieldAccessorImpl( final Object obj, Field field )
	{
		super( obj );
		
		this.field = field;
	}

	public Optional<T> getValue()
	{
		if ( !field.isAccessible() )
			field.setAccessible( true );
		
		try
		{
			checkStatic();
			
			return (Optional<T>) Optional.ofNullable(  field.get( owner ) );
		}
		catch ( IllegalArgumentException | IllegalAccessException e )
		{
			e.printStackTrace();
			
			return Optional.empty();
		}
	}
	
	public void setValue( T newValue )
	{
		if ( !field.isAccessible() )
			field.setAccessible( true );
		
		try
		{
			checkStatic();
			
			field.set( owner, newValue );
		}
		catch ( IllegalArgumentException | IllegalAccessException e )
		{
			e.printStackTrace();
		}
	}
	
	public Field getField()
	{
		return field;
	}
	
	private void checkStatic()
	{
		if ( owner instanceof Class && ( field.getModifiers() & Modifier.STATIC ) == 0 )
		{
			throw new IllegalStateException( "non-static field requires an instance." );
		}
	}
}
