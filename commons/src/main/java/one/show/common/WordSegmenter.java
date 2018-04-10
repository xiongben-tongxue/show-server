package one.show.common;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class WordSegmenter {
	
	public static void main(String[] args) throws IOException {
		
		String text="亲欧巴星币男神互关呗谢谢哒烦死了妹儿刷单tks开心蟹蟹爱心小心心早妹呦嗨哥哥，美女，妹子，哥没漏胸，差评";
        StringReader sr=new StringReader(text);
        //true代表不是细颗粒的分词
        IKSegmenter ik=new IKSegmenter(sr, true);
        Lexeme lex=null;
        while((lex=ik.next())!=null){
            System.out.print(lex.getLexemeText()+"|");
        }
	
	}
	
	public static Set<String> seg(String text) throws IOException{
		Set<String> segs = new HashSet<String>();
		
		StringReader sr=new StringReader(text);
        //true代表不是细颗粒的分词
        IKSegmenter ik=new IKSegmenter(sr, true);
        Lexeme lex=null;
        while((lex=ik.next())!=null){
            segs.add(lex.getLexemeText());
        }
        
        return segs;
	}

}
