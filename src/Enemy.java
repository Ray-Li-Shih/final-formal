import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;


public class Enemy {
	private int x, y; // �ĤH����m
	private int dx = 2; // �������ʳt��
	private static final int WIDTH = 20; // �ĤH�e��
	private static final int HEIGHT = 20; // �ĤH����
	private static final int DROP = 20; // �C��������ɱ������Z��
	private static final int FIRE_CHANCE = 20; // �o�g���v�]�ʤ���^
	private Random random = new Random();
	private Timer timer;
	private int d;
	int moveCounter = 0;
	private int moveInterval; // �C���ĤH�����P���j
	private int direction;
    
	public Enemy(int x, int y) {
	    this.x = x;
	    this.y = y;
	    this.direction = (int)(Math.random() * 4); // 0~3 �H����V
	    this.moveInterval = 300 + (int)(Math.random() * 100); // �C�X�V����
	    this.moveCounter = 0;
	}

	static {
		
	}
	public void move_up() {
		 y -= dx;
		 if (y  < 0) {
				dx = -dx;
			}
		 if (y + HEIGHT > Setting.panelHeight) {
				dx = -dx;
			}
	}
	public void move_down() {
		y += dx;
		 if (y  < 0) {
				dx = -dx;
			}
		 if (y + HEIGHT > Setting.panelHeight) {
				dx = -dx;
			}
	}
	public void move_left() {
		x -= dx;
		if (x < 0) {
			dx = -dx;
		}
		if (x + WIDTH > Setting.panelWidth) {
			dx = -dx;
		}
	}
	public void move_right() {
		x += dx;
		if (x < 0) {
			dx = -dx;
		}
		if (x + WIDTH > Setting.panelWidth) {
			dx = -dx;
		}
	}
	
	public void randomMove() {
	    moveCounter++;
//	    System.out.println("--------------");
//	    System.out.println(moveCounter);
//	    System.out.println(moveInterval);
//	    System.out.println("--------------");
	    if (moveCounter >= moveInterval) {
	        direction = (int)(Math.random() * 4); // �C����s��V
	        moveCounter = 0;
	    }
	    if (direction == 0)
	    	move_up();
	    if (direction == 1)
	    	move_down();
	    if (direction == 2)
	    	move_left();
	    if (direction == 3)
	    	move_right();
	}
	


	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	// ø�s�ĤH
	public void drawShape(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(getX(), getY(), WIDTH, HEIGHT);
	}
}