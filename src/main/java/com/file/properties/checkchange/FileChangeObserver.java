package com.file.properties.checkchange;

/**
 * 观察者接口
 * @author tony
 *
 */
public interface FileChangeObserver {

	/**
	 * 文件变化后进行相应的处理
	 * @param filename
	 */
	public void fileChanged(String filename);
}
