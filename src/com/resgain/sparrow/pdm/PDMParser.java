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
package com.resgain.sparrow.pdm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.resgain.sparrow.pdm.bean.Category;
import com.resgain.sparrow.pdm.bean.Column;
import com.resgain.sparrow.pdm.bean.Domain;
import com.resgain.sparrow.pdm.bean.Project;
import com.resgain.sparrow.pdm.bean.Table;


/**
 * PDM文件解析
 * @author gyl
 */
public class PDMParser
{
    private PDMParser(){}
    
    /**
     * 解析指定的pdm文件
     * @param pdmFile
     * @return
     * @throws IOException
     */
    public static Project parse(File pdmFile) throws IOException
    {
    	return parse(new FileInputStream(pdmFile));
    }
    
    public static Project parse(InputStream is) throws IOException
    {
    	 String pdmContent = readFile(is, "UTF-8");
    	 return parse(pdmContent);
    }

    public static Project parse(String pdmContent)
    {
        Project ret = new Project();   
        
        ret.setAuthor(getString(pdmContent, "<a:Author>(.*?)</a:Author>", 1, null));
        ret.setVersion(getString(pdmContent, "<a:Version>(.*?)</a:Version>", 1, null));
        ret.setName(getString(pdmContent, "<a:Name>(.*?)</a:Name>", 1, null));
        ret.setCode(getString(pdmContent, "<a:Code>(.*?)</a:Code>", 1, null));
        
        Pattern lPattern = Pattern.compile("<o:PhysicalDomain Id=\\\"([a-zA-Z0-9_]+)\\\">\\s+<a:ObjectID>(.*?)</a:ObjectID>\\s+<a:Name>(.*?)</a:Name>\\s+<a:Code>([a-zA-Z0-9_]+)</a:Code>(?s)(.*?)</o:PhysicalDomain>", Pattern.CASE_INSENSITIVE);
        Matcher lMatcher = lPattern.matcher(pdmContent);
        while(lMatcher.find())
        {
        	Domain domain = new Domain(lMatcher.group(1), lMatcher.group(3), lMatcher.group(4));
        	domain.setType(getString(lMatcher.group(5), "<a:DataType>(.*?)</a:DataType>", 1, null));
        	domain.setComment(getString(lMatcher.group(5), "<a:Comment>(?s)(.*?)</a:Comment>", 1, null));
        	ret.getDomains().put(domain.getId(), domain);
        }        
        
        lPattern = Pattern.compile("<(o:Table) Id=\"(.*?)\">(?s)(.*?)<(a:Name)>(.*?)</(\\4)>(?s)(.*?)<(a:Code)>(.*?)</(\\8)>(?s)(.*?)<(c:Columns)>(?s)(.*?)</(\\12)>(?s)(.*?)</(\\1)>", Pattern.CASE_INSENSITIVE);
        lMatcher.reset();
        lMatcher = lPattern.matcher(getString(pdmContent, "<(c:Tables)>(?s)(.*?)</(\\1)>", 2, ""));
        while (lMatcher.find()) {
        	System.out.println( lMatcher.group(9)+"\t"+lMatcher.group(5));
            Table table = new Table(ret.getCode(), lMatcher.group(2), lMatcher.group(9), lMatcher.group(5));
            Pattern pattern = Pattern.compile("<(o:Column) Id=\"(.*?)\">(?s)(.*?)<(a:Name)>(.*?)</(\\4)>(?s)(.*?)<(a:Code)>(.*?)</(\\8)>(?s)(.*?)<(a:DataType)>(.*?)</(\\12)>(?s)(.*?)</(\\1)>", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(lMatcher.group(13));
            while (matcher.find()){
            	int length = Integer.valueOf(getString(matcher.group(15), "<a:Length>([\\d]+)</a:Length>", 1, "0"));
            	int precision = Integer.valueOf(getString(matcher.group(15), "<a:Precision>([\\d]+)</a:Precision>", 1, "0"));
            	Column column = new Column(matcher.group(2), matcher.group(9), matcher.group(5), matcher.group(13), length, precision);
            	String nul = getString(matcher.group(15), "<(a:Mandatory)>(.*?)</(\\1)>(.*?)", 2, "0");
        		if(nul!=null && (nul.equalsIgnoreCase("1") || nul.trim().equalsIgnoreCase("yes")) )
        			column.setNullFlag(true);
            	column.setDomainId(getString(matcher.group(15), "<(c:Domain)>\\s*<o:PhysicalDomain[ ]+Ref=\\\"([a-zA-Z0-9_]+)\\\"/>\\s*</(\\1)>", 2, null));
            	column.setComment(getString(matcher.group(11), "<a:Comment>(?s)(.*?)</a:Comment>", 1, null));
            	if(column.getDomainId()!=null)
            		column.setDomain(ret.getDomains().get(column.getDomainId()));
            	
        		table.getColumns().add(column);
            }
            
            //FIXME 这里实际上是有问题的，应该先查c:PrimaryKey的值，然后再向上找才准确。
            pattern = Pattern.compile("<(c:Keys)>(?s)(.*?)<o:Key Id=\"(.*?)\">(?s)(.*?)<o:Column Ref=\"(.*?)\"/>(.*?)</(\\1)>(?s)(.*?)<c:PrimaryKey>(?s)(.*?)<o:Key Ref=\"(.*?)\"/>(?s)(.*?)</c:PrimaryKey>", Pattern.CASE_INSENSITIVE);
            matcher = pattern.matcher(lMatcher.group(15));
            if(matcher.find())
                table.setPK(matcher.group(5));
            ret.getTableList().add(table);
        }         
        lPattern = Pattern.compile("<(o:Reference)(.*?)>(?s)(.*?)<a:Cardinality>[0,1]\\.\\.\\*</a:Cardinality>(?s)(.*?)<o:Table Ref=\"(.*?)\"/>(?s)(.*?)<o:Table Ref=\"(.*?)\"/>(?s)(.*?)<o:Column Ref=\"(.*?)\"/>(?s)(.*?)<o:Column Ref=\"(.*?)\"/>(?s)(.*?)(\\1)", Pattern.CASE_INSENSITIVE);
        lMatcher.reset();
        lMatcher = lPattern.matcher(pdmContent);
        while(lMatcher.find())
            ret.setFK(lMatcher.group(7), lMatcher.group(5), lMatcher.group(11), lMatcher.group(9));
        
        lPattern = Pattern.compile("<o:PhysicalDiagram Id=\\\"([a-zA-Z0-9_]+)\\\">(?s)(.*?)</o:PhysicalDiagram>", Pattern.CASE_INSENSITIVE);
        lMatcher.reset();
        lMatcher = lPattern.matcher(pdmContent);
        while(lMatcher.find())
        {
        	String name = getString(lMatcher.group(2), "<a:Name>(.*?)</a:Name>", 1, null);
        	String code = getString(lMatcher.group(2), "<a:Code>(.*?)</a:Code>", 1, null);
        	String author = getString(lMatcher.group(2), "<a:Creator>(.*?)</a:Creator>", 1, null);
        	Category category = new Category(name, code, author);
        	category.setId(lMatcher.group(1));

        	Pattern p1 = Pattern.compile("<o:Table Ref=\\\"([a-zA-Z0-9_]+)\\\"/>", Pattern.CASE_INSENSITIVE);
        	Matcher m1 = p1.matcher(lMatcher.group(2));
        	while(m1.find()){
        		category.getTableList().add(ret.getTable(m1.group(1)));
        	}
        	ret.getCategoryList().add(category);
        }
        return ret;
    }
    
	private static String getString(String str, String reg, int group, String def)
	{
		Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		if(m.find() && m.groupCount()>=group)
			return m.group(group);
		return def;
	}    
	
    private static String readFile(InputStream fis, String charset) throws IOException
    {
    	StringBuffer ret = new StringBuffer();
    	BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(fis, charset));
        char[] buf = new char[512];
        int size = bufferedreader.read(buf);
        while (size != -1) {
        	ret.append(buf, 0, size);
        	size = bufferedreader.read(buf);
        }
        bufferedreader.close();
        return ret.toString(); 	
    }	
}
