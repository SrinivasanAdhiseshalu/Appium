package com.amazon.Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

	static Properties property;
	public PropertyReader()throws IOException{
		InputStream configFilepath;
		try
		{
			property=new Properties();
			configFilepath=new FileInputStream(FilePaths.PropertyFilePath);
			property.load(configFilepath);
		}
		catch(IOException e5)
		{
			System.out.println("OR got IOException please look into it");
			throw e5;
		}
		
	}
	//;This method is used to get the property from the OR File
	public static String getProperty(String propertyvalue) throws Exception
	{
		if(property.getProperty(propertyvalue)!=null)
		{
			return property.getProperty(propertyvalue);
		}
		else
		{
			throw new Exception("The object property is not found in the OR file for the object");
		}
	}
	
	
	
	
	
	
	
	
}
