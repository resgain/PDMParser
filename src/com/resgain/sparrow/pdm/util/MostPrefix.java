package com.resgain.sparrow.pdm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MostPrefix
{
	private Map<String, Integer> prefixs = new HashMap<String, Integer>();
	
	private MostPrefix(){}
	
	public static MostPrefix getInstance(){
		return new MostPrefix();
	}
	
	public void addPrefix(String prefix){
		if(prefixs.containsKey(prefix))
			prefixs.put(prefix, prefixs.get(prefix)+1);
		else
			prefixs.put(prefix, 1);
	}
	
	public String getMostPrefix(){
		String ret = "_";
		int count = 0;
		for (Entry<String, Integer> entry : prefixs.entrySet()) {
			if(entry.getValue()>count && entry.getValue()>1){
				ret = entry.getKey();
				count = entry.getValue();
			}
		}
		return ret;
	}
}
