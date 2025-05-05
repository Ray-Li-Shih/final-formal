import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class EnemyBullet {
    private int x, y; // �l�u����m
    private static final int WIDTH = 5; // �l�u�e��
    private static final int HEIGHT = 10; // �l�u����
    private static final int SPEED = 3; // �l�u���ʳt��
    private BufferedImage image; //�l�u�Ϥ�
	
	public EnemyBullet(int x, int y) {
		this.x = x;
		this.y = y;

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/enemybullet.png")); // �Υ� getResourceAsStream ���J�귽
		} catch (IOException e) {
			System.err.println("���J�Ϥ�����: " + e.getMessage());
		}
	}

    public void move() {
        y += SPEED;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /** ���o�l�u����ɯx�ΡA�Ω�I���˴� */
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    /** �l�u�O�_�w�g���X�ù� */
    public boolean isOutOfScreen() {
        return y > 600;
    }
    
    // ø�s�l�u 
    public void drawShape(Graphics g) {
		if (image != null) {
			g.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
		} else {
			// �Y�Ϥ����J���ѫh��ܶ���x��
			g.setColor(Color.yellow);
	        g.fillRect(getX(), getY(), WIDTH, HEIGHT);
		}
	}
}