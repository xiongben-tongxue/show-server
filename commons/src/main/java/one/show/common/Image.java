package one.show.common;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	
	
	public static void createWatermarkPng4Pid(Long pid, File file) throws IOException{
		int width = 180;
		int height = 50;
		// 创建BufferedImage对象
		BufferedImage image = new BufferedImage(width, height,     BufferedImage.TYPE_INT_RGB);
		// 获取Graphics2D
		Graphics2D g2d = image.createGraphics();

		// ----------  增加下面的代码使得背景透明  -----------------
		image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g2d.dispose();
		g2d = image.createGraphics();
		// ----------  背景透明代码结束  -----------------


		// 画图
		float alpha = 0.85f;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		g2d.setColor(Color.white);
		g2d.setFont(new Font("PingFang SC", Font.BOLD, 23));
//		g2d.setFont(new Font("PingFang", Font.BOLD, 23));
		g2d.setStroke(new BasicStroke(1));
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); 
		
		g2d.drawString("ID:"+pid, 8, 25);
		
		g2d.create();
		
		//释放对象
		g2d.dispose();
		// 保存文件    
		ImageIO.write(image, "png", file);
	}
	
	public static void main(String[] args) throws IOException {
		
		createWatermarkPng4Pid(12345777l, new File("/Users/zw/Documents/id.png"));
		
	}

}
