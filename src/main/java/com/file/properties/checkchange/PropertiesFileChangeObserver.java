package com.file.properties.checkchange;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 具体的文件观察者
 * @author tony
 *
 */
public class PropertiesFileChangeObserver implements FileChangeObserver {

	protected static final Logger log = LoggerFactory.getLogger(FileChangeObserver.class);
	
	@SuppressWarnings("unused")
	private final String filePath = FileChangeMonitor.class.getResource("test.properties").getPath();
	
	@SuppressWarnings("unused")
	private Properties properties;
	
	private static PropertiesFileChangeObserver instance;
	
	private PropertiesFileChangeObserver() {
		
	}
	
	
	public static PropertiesFileChangeObserver getInstance() {
		if(instance == null) {
			instance = new PropertiesFileChangeObserver();
			instance.reload();
		}
		
		return instance;
	}
	
	
	/**
	 * 重新加载文件
	 */
	private void reload() {
		
		try {   
			Properties prop = new Properties();  
			InputStream in = FileChangeMonitor.class.getResourceAsStream("test.properties");  
            prop.load(in);   
            properties = prop;
            for(Object key : prop.keySet()) {
            	log.info("key:{}, value:{}", key, prop.get(key));
            }
        } catch (IOException e) {   
            e.printStackTrace();   
            log.error("init properties error, msg:{}", e.getMessage());
        }   
	}
	
	
	
	public synchronized void fileChanged(String fileName) {
		
		reload();
	}

}
