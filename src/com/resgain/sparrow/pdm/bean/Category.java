/*
 * Copyright (c) 2012 resgain.
 * This file is part of PDMParser.
 *
 * PDMParser is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * PDMParser is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.resgain.sparrow.pdm.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 表分类信息
 * @author gyl
 */
public class Category
{
	private String id;
	private String name; //PDM名称
	private String code; //PDM代码
	private String author; //作者
	
	private List<Table> tableList = new ArrayList<>();

	public Category(String name, String code, String author) {
		super();
		this.name = name;
		this.code = code;
		this.author = author;
	}

	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}

	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}

	public List<Table> getTableList()
	{
		return tableList;
	}
	public void setTableList(List<Table> tableList)
	{
		this.tableList = tableList;
	}
}
