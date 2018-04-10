package one.show.common.test;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import one.show.common.TypeUtil;
import one.show.common.Utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class SlowLogTest {

	public static void main(String[] args) {
		Set<String> sql = new HashSet<String>();
		LineIterator it=null;
		try {
			it = FileUtils.lineIterator(new File("/Users/hank/Documents/slow.log"), "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                if(line.startsWith("select") && line.indexOf("information_schema.")==-1&& line.indexOf("INFORMATION_SCHEMA.")==-1 && line.indexOf("where")!=-1){
                	String str = line.replaceAll("\\d", "");
                	sql.add(str);
                }
            }
        } finally {
            it.close();
        }
        System.out.println(TypeUtil.typeToString("all", sql));
	}

}
