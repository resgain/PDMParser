package com.resgain.sparrow.pdm;

import java.io.File;

public class PDMParserTest
{
	public static void main(String[] args) throws Exception
	{
		System.out.println(PDMParser.parse(new File("/home/gyl/share/x.pdm")));
	}
}
