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
 * 表信息
 * @author gyl
 */
public class Table
{
	private String id;
	private String prjId; //项目ID
    private String code;  //表代码
    private String name;  //表含义
    private String scode; //简化后的表代码， 主要是去调前缀,比如BASE_USER-->USER

    private List<Column> columns = new ArrayList<Column>(); //列信息
    
    public Table() { }
    
    public Table(String prjId, String id, String code, String name)
    {
    	this.prjId = prjId;
    	this.id = id;
        this.code = code;
        this.name = name;  
    }

    /**
     * 取得指定id的字段信息
     * @param oid
     * @return
     */
    public Column getColumnInfo(String oid)
    {
        Column col = null;
        for(int i=0; i<columns.size(); i++)
        {
            col = (Column)columns.get(i);
            if(col.getId().equalsIgnoreCase(oid))
                break;
        }        
        return col;
    }
    
    /**
     * 设置主键
     * @param oid
     */
    public void setPK(String oid)
    {
        Column col = getColumnInfo(oid);
        if(col!=null)
            col.setPkFlag(true);
    }
    
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer().append(id);
		sb.append("\t").append(name).append("(").append(code).append("--").append(scode).append(")\r\n");
		
		if(columns.size()>0)
		{
			for (Column column : columns) {
				sb.append("\t\t").append(column);
			}
		}
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

	public String getPrjId()
	{
		return prjId;
	}
	public void setPrjId(String prjId)
	{
		this.prjId = prjId;
	}

	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}

	public List<Column> getColumns()
	{
		return columns;
	}
	public void setColumns(List<Column> columns)
	{
		this.columns = columns;
	}

	public String getScode()
	{
		return scode;
	}
	public void setScode(String scode)
	{
		this.scode = scode;
	}
}