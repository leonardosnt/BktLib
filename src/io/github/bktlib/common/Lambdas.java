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

package io.github.bktlib.common;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Askar Kalykov
 */
public class Lambdas
{
	private Lambdas() {}
	
	public static <T> Predicate<T> as( Predicate<T> predicate )
	{
		return predicate;
	}

	public static <T> Consumer<T> as( Consumer<T> consumer )
	{
		return consumer;
	}

	public static <T> Supplier<T> as( Supplier<T> supplier )
	{
		return supplier;
	}

	public static <T, R> Function<T, R> as( Function<T, R> function )
	{
		return function;
	}
}