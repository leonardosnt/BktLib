package io.github.bktlib.item.builders.impl;

import io.github.bktlib.item.builders.BookBuilder;

public class BookBuilderImpl extends ItemBuilderImpl implements BookBuilder
{

	@Override
	public BookBuilder author(String author) 
	{
		return null;
	}

	@Override
	public BookBuilder title(String title) 
	{
		return null;
	}

	@Override
	public PageBuilder newPage() 
	{
		return null;
	}
	

	public class PageBuilderImpl implements PageBuilder
	{

		@Override
		public PageBuilder line(String line)
		{
			return null;
		}

		@Override
		public PageBuilder lines(String... lines)
		{
			return null;
		}

		@Override
		public BookBuilder endPage() 
		{
			return null;
		}
		
	}
}
