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

/**
 * 数据类型域类
 * @author gyl
 */
public class Domain
{
	private String id;
	private String name; //名称
	private String code; //代码
	private String type; //数据类型
	private String comment; //注释

	public Domain(String id, String name, String code) 
	{
		this.id = id;
		this.name = name;
		this.code = code;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer().append(id);
		sb.append("\t").append(name).append("(").append(code).append(")");
		sb.append("\t").append(type);
		if(comment!=null)
			sb.append("\t//").append(comment);
		return sb.append("\r\n").toString();
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

	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}

	public String getComment()
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
}