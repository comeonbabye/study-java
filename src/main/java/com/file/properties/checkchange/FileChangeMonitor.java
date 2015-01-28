package com.file.properties.checkchange;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 文件变化管理
 * 1. 可以为每个文件注册多个观察者
 * 2. 通过Timer来进行任务调度
 * 3. 
 * @author tony
 *
 */
public class FileChangeMonitor {

	protected static final Logger log = LoggerFactory.getLogger(FileChangeMonitor.class);
	
	
	public static final long DELAY_TIME = 1000;

	private static FileChangeMonitor monitor;

	private Map<String, List<TimerTask>> fileObservers;
	
	private Timer timer;
	

	private FileChangeMonitor() {
		fileObservers = Collections.synchronizedMap(new Hashtable<String, List<TimerTask>>());
		timer = new Timer(FileChangeMonitor.class.getName());
	}

	public synchronized static FileChangeMonitor getInstance() {
		if (monitor == null) {
			monitor = new FileChangeMonitor();
		}
		return monitor;
	}

	/**
	 * 为某个文件注册观察者，观察文件是否发生变化，
	 * @param observer
	 * @param fileName
	 * @param delay
	 * @throws FileNotFoundException
	 */
	public void addObserver(FileChangeObserver observer, String fileName, long delay) throws FileNotFoundException {
		
		//1. 创建具体的文件观察任务
		TimerTask task = new FileChangeTask(observer, fileName);
		//2. 每个文件可以有多个观察任务
		List<TimerTask> tasks = fileObservers.get(fileName);
		if (tasks == null) {
			tasks = new ArrayList<TimerTask>();
		}
		tasks.add(task);
		fileObservers.put(fileName, tasks);
		//3. 向timer注册任务调度方式，timer会通过TaskQueue来存储需要调度的任务
		
		timer.schedule(task, delay, delay);
	}
	
	/**
	 * 文件变成检查具体类
	 * 1. 当文件发生变化后，会通知观察者某个文件发生了变化
	 * 2. 根据文件最后更新时间来判断文件是否发生了变化
	 * @author tony
	 *
	 */
	private static class FileChangeTask extends TimerTask {

		private long lastModified;
		private File file;
		private FileChangeObserver observer;
		
		public FileChangeTask(FileChangeObserver observer, String fileName) throws FileNotFoundException {
			super();
			this.observer = observer;
			this.file = new File(fileName);
			if (!this.file.exists())
				throw new FileNotFoundException("FileChangeMonitor.FileChangeTask() Can't locate:" + fileName);
			this.lastModified = file.lastModified();
		}
		
		@Override
		public void run() {
			
			try {
				long newLastModified = file.lastModified();
				if (newLastModified > lastModified) {
					lastModified = newLastModified;
					observer.fileChanged(file.getPath());
					log.info("file changed for {}", file.getName());
				} else {
					log.info("nothing changed for {}", file.getName());
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("check file error, file:{}, msg:{}", file.getPath(), e.getMessage());
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		String filePath = FileChangeMonitor.class.getResource("test.properties").getPath();
		
		FileChangeObserver fileChangeObserver = PropertiesFileChangeObserver.getInstance();
		FileChangeMonitor monitory = FileChangeMonitor.getInstance();
		monitory.addObserver(fileChangeObserver, filePath, FileChangeMonitor.DELAY_TIME);
	}

}
