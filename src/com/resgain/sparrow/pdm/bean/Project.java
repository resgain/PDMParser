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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * PDM项目信息
 * @author gyl
 */
public class Project
{
	private String code; //项目代码
    private String name; //项目名称    
    private String author; //作者
    private String version; //版本
    private Date cdate; //创建日期
    
    private Map<String, Domain> domains = new HashMap<String, Domain>();
    private List<Category> categoryList = new ArrayList<Category>();
    private List<Table> tableList = new ArrayList<Table>(); //表列表

    /**
     * 获取指定ID的表定义信息
     * @param oid
     * @return
     */
    public Table getTable(String oid)
    {
        for(int i=0; i<tableList.size(); i++)
            if(((Table)tableList.get(i)).getId().equals(oid))
                return ((Table)tableList.get(i));
        return null;
    }
    
    /**
     * 设置表关联
     * @param soid
     * @param toid
     * @param sfoid
     * @param tfoid
     */
    public void setFK(String soid, String toid, String sfoid, String tfoid)
    {
        Table t1 = getTable(soid);
        Table t2 = getTable(toid);
        if(t1==null || t2==null)
            return;
        
        Column col1 = t1.getColumnInfo(sfoid);
        Column col2 = t2.getColumnInfo(tfoid);
        if(col1==null || col2==null)
        	return;
        
        col1.setRef(t2); 
        col1.setRefId(tfoid);
    }
    
    /**
     * 获取指定的PDM分类
     * @param cateId
     * @return
     */
	public Category getCategory(String cateId){
		for (Category cate : categoryList) {
			if(cateId.equals(cate.getId()))
				return cate;
		}
		return null;
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer(name);
		sb.append("(").append(code).append(")\tAuthor:").append(author);
		sb.append(" Version:").append(version);
		sb.append(" Create time:").append(cdate);
		sb.append(" ").append(domains.size()).append(" domains");
		sb.append(" ").append(categoryList.size()).append(" pdms");
		sb.append(" ").append(tableList.size()).append(" tables");
		sb.append("\r\n");
		
		if(domains.size()>0)
		{
			sb.append("Domain List:\r\n");
			for (Entry<String, Domain> entry : domains.entrySet()) {
				sb.append("\t").append(entry.getValue());
			}
		}

		if(tableList.size()>0)
		{
			sb.append("Table List:\r\n");
			for (Table table : tableList) {
				sb.append("\t").append(table);
			}
		}		
		return sb.append("\r\n").toString();
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

	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String author)
	{
		this.author = author;
	}

	public String getVersion()
	{
		return version;
	}
	public void setVersion(String version)
	{
		this.version = version;
	}

	public Date getCdate()
	{
		return cdate;
	}
	public void setCdate(Date cdate)
	{
		this.cdate = cdate;
	}

	public List<Category> getCategoryList()
	{
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList)
	{
		this.categoryList = categoryList;
	}

	public List<Table> getTableList()
	{
		return tableList;
	}
	public void setTableList(List<Table> tableList)
	{
		this.tableList = tableList;
	}

	public Map<String, Domain> getDomains()
	{
		return domains;
	}
	public void setDomains(Map<String, Domain> domains)
	{
		this.domains = domains;
	}
}
