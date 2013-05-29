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
 * 表字段信息
 * @author gyl
 */
public class Column
{
	private String id;
	private String code; //字段代码
	private String scode; //简化后的表代码， 主要是去调前缀,比如BASE_USER-->USER
    private String name; //字段含义
    private String type; //原始类型及长度
    private int length;
    private int precision;
    private String domainId; //定义的数据域ID
    private boolean pkFlag; //是否为主键
    private boolean nullFlag; //是否可以为空
    private boolean uniqueFlag; //是否唯一
    private Table ref;//关联表
    private String refId; //关联表字段ID
    private String comment; //注释
    
    private Domain domain;
    
    public Column() { }
    
	public Column(String id, String code, String name, String type, int length, int precision) 
	{
		this.id = id;
		this.code = code;
		this.name = name;
		this.type = type;
		this.length = length;
		this.precision = precision;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer().append(id);
		sb.append("\t").append(name).append("(").append(code).append("--").append(scode).append(")");
		sb.append("\t").append(type);
		if(nullFlag)
			sb.append("\tNot Null");		
		if(pkFlag)
			sb.append("\tPrimary Key");
		if(uniqueFlag)
			sb.append("\tUnique");
		if(ref!=null){
			sb.append("\t-->").append(ref.getId()).append("(").append(ref.getCode()).append(").").append(refId).append("(").append(ref.getColumnInfo(refId).getCode()).append(")");
		}
		if(domain!=null)
			sb.append("\tdomainId:").append(domainId);
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

	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}

	public String getDomainId()
	{
		return domainId;
	}
	public void setDomainId(String domainId)
	{
		this.domainId = domainId;
	}

	public boolean isPkFlag()
	{
		return pkFlag;
	}
	public void setPkFlag(boolean pkFlag)
	{
		this.pkFlag = pkFlag;
	}

	public boolean isNullFlag()
	{
		return nullFlag;
	}
	public void setNullFlag(boolean nullFlag)
	{
		this.nullFlag = nullFlag;
	}

	public boolean isUniqueFlag()
	{
		return uniqueFlag;
	}
	public void setUniqueFlag(boolean uniqueFlag)
	{
		this.uniqueFlag = uniqueFlag;
	}

	public String getRefId()
	{
		return refId;
	}
	public void setRefId(String refId)
	{
		this.refId = refId;
	}

	public Table getRef()
	{
		return ref;
	}
	public void setRef(Table ref)
	{
		this.ref = ref;
	}

	public String getComment()
	{
		return comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}

	public Domain getDomain()
	{
		return domain;
	}
	public void setDomain(Domain domain)
	{
		this.domain = domain;
	}

	public int getLength()
	{
		return length;
	}
	public void setLength(int length)
	{
		this.length = length;
	}

	public int getPrecision()
	{
		return precision;
	}
	public void setPrecision(int precision)
	{
		this.precision = precision;
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
