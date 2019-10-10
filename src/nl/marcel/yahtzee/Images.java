package nl.marcel.yahtzee;

import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Images extends Canvas {

	public Icon getImage(String color, String number) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("img/" + number + "_" + color + ".png"));
		} catch (IOException e) {
			System.out.println("Picture not found");
			e.printStackTrace();
		}
		Image i1 = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon i = new ImageIcon(i1);
		return i;
	}
}
