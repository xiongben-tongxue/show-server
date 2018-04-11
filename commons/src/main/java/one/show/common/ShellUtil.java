package one.show.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellUtil {

    public static final Logger logger = LoggerFactory.getLogger(ShellUtil.class);

    public static String exec(String[] cmds) throws Exception {
        logger.info("exec cmds: " + Arrays.asList(cmds));

        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (os.startsWith("win") || os.startsWith("Win")) {
            logger.warn("System is Windows, ignore exec");
            return "99999";
        }

        Runtime runtime = Runtime.getRuntime();
        final Process process = runtime.exec(cmds);
        
        if (process != null){
        	new Thread(new Runnable(){
                @Override
                public void run() {
                	try {
						Thread.sleep(1000* 30);
						if (process != null){
							process.destroy();
						}
					} catch (InterruptedException e) {
					}
                }
            }).start();
        }
        
        StringBuffer content = new StringBuffer();
        // 取得命令结果的输出流
        InputStream is = process.getInputStream();
        // 用一个读输出流类去读
        InputStreamReader isr = new InputStreamReader(is);
        // 用缓冲器读行
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null) {
            content.append(line+"\n");
        }
        //
        //
        is.close();
        isr.close();
        br.close();
        return content.toString();
    }
    
    public static String execError(String[] cmds) throws Exception {
    	logger.info("exec cmds: " + Arrays.asList(cmds));
    	
    	Properties prop = System.getProperties();
    	String os = prop.getProperty("os.name");
    	if (os.startsWith("win") || os.startsWith("Win")) {
    		logger.warn("System is Windows, ignore exec");
    		return "99999";
    	}
    	
    	Runtime runtime = Runtime.getRuntime();
    	Process process = runtime.exec(cmds);
    	// 取得命令结果的输出流
    	InputStream is = process.getErrorStream();
    	// 用一个读输出流类去读
    	InputStreamReader isr = new InputStreamReader(is);
    	// 用缓冲器读行
    	BufferedReader br = new BufferedReader(isr);
    	StringBuffer content = new StringBuffer();
    	String line = null;
    	while ((line = br.readLine()) != null) {
    		content.append(line);
    	}
    	is.close();
    	isr.close();
    	br.close();
    	return content.toString();
    }

    public static String exec(String cmd) throws Exception {
        String[] cmds = new String[] {cmd};
        return exec(cmds);
    }
    
    public static void main(String[] args) throws Exception {
    	String cmdStr = "ls -all";
    	System.out.println(exec(cmdStr.split(" ")));
    	String target = "/Users/hank/Desktop/test.webp";
    	System.out.println(new File(target).getPath());
    	/*
    	while(!new File(target).exists()){
//    		String cmdStr = "/usr/local/bin/ffmpeg -ss 1 -t 3 -i http://play.xiubi.come/594e207b3e57dcc21850b75e.mp4 -c:a copy -c:v libx264 -b:v 200k -s 275*480 -y -f webp "+target;
//    		String cmdStr = "/usr/local/bin/ffmpeg -ss 1 -t 3 -i http://play.xiubi.come/594e207b3e57dcc21850b75e.mp4 -c:a copy -c:v libx264 -b:v 200k -s 160*200 -f mp4 -y "+target;
//    		String cmdStr = "/usr/local/bin/ffmpeg -ss 1 -t 3 -i http://play.xiubi.come/594782fb7f0adcc2e63f1a95.mp4 -s 187*239 -f gif "+target;
    		String cmdStr = "ls -all";
    		try {
    			System.out.println(exec(cmdStr.split(" ")));
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		System.out.println(new File(target).exists());
    	}
    	*/
	}

}
