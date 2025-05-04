import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class StarShip {
	private int x, y; // �����m
	private static final int WIDTH = 20; // ����e��
	private static final int HEIGHT = 20; // �����
	private BufferedImage image; //����Ϥ�
	
	public StarShip(int x, int y) {
		this.x = x;
		this.y = y;

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/starship.png")); // �Υ� getResourceAsStream ���J�귽
		} catch (IOException e) {
			System.err.println("���J�Ϥ�����: " + e.getMessage());
		}
	}
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public void drawShape(Graphics g) {
		if (image != null) {
			g.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
		} else {
			// �Y�Ϥ����J���ѫh��ܦǦ�T����
			g.setColor(Color.GRAY);
			int[] x_outline = { getX() - WIDTH / 2, getX(), getX() + WIDTH / 2 };
			int[] y_outline = { getY() + HEIGHT / 2, getY() - HEIGHT / 2, getY() + HEIGHT / 2 };
			g.fillPolygon(x_outline, y_outline, 3);
		}
	}
}