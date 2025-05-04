import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class SpaceInvader_panel extends JPanel
		implements ActionListener, MouseMotionListener, MouseListener {
	// �]�w�C�����O�j�p
	private static final int PANEL_WIDTH = 800;
	private static final int PANEL_HEIGHT = 600;

	private BufferedImage myImage;
	private Graphics myBuffer;
	private StarShip starShip; // ����
	private List<Bullet> bullets; // �l�u���X
	private List<EnemyBullet> enemyBullets;// �ĤH�l�u���X
	private Iterator<Bullet> bulletIterator;
	private Iterator<EnemyBullet> enemyBulletsIterator;
	private List<Enemy> enemies; // �ĤH���X
	private Iterator<Enemy> enemyIterator;
	private Timer timer, timer_hold; // �C���p�ɾ��A�Ω��s�e��
	private int score; // �o��
	public SpaceInvader_panel() {
		
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(Color.BLACK);
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		initSetting();
		myImage = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		myBuffer = myImage.getGraphics();
		// �򥻪���
		starShip = new StarShip(PANEL_WIDTH / 2, PANEL_HEIGHT - 30); // �_�l��m�]�w
		enemyBullets = new ArrayList<>();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		Iterator<EnemyBullet> enemyBulletIterator = enemyBullets.iterator();
		// ��l�ƼĤH�A�ƦC�b�W��
		int startX = 20;
		int startY = 50;
		int numEnemies = 8;
		int spacing = 40;
		for (int i = 0; i < numEnemies; i++) {
			enemies.add(new Enemy(startX + i * spacing, startY));
		}
		score = 0;
		// �]�m�p�ɾ��A�C15�@�����@���ʧ@ (�j��66 FPS)
		timer = new Timer(10, this);
		timer_hold = new Timer(80, e -> spawnBullet());
		timer.start();
		
	}

	public void initSetting() {
		Setting.panelWidth = PANEL_WIDTH;
		Setting.panelHeight = PANEL_HEIGHT;
	}
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(myImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
	}

	public void mouseDragged(MouseEvent e) {
		starShip.setX(e.getX());
		starShip.setY(e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		starShip.setX(e.getX());
		starShip.setY(e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		spawnBullet();
		timer_hold.start();
	}

	public void spawnBullet() {
		bullets.add(new Bullet(starShip.getX(), starShip.getY()));
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		timer_hold.stop();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Enemy enemy : enemies) {
	        if (Math.random() < 0.01) { // �C�@�V�� 1% ���v�o�g
	        	enemyBullets.add(new EnemyBullet(enemy.getX(), enemy.getY())); 
	        }
	    }

	    // ���ʼĤH�l�u
	    Iterator<EnemyBullet> enemyBulletIterator = enemyBullets.iterator();
	    while (enemyBulletIterator.hasNext()) {
	        EnemyBullet bullet = enemyBulletIterator.next();
	        bullet.move();
	        if (bullet.isOutOfScreen()) {
	            enemyBulletIterator.remove();
	        }
	    }
		posRefresh(); // ��s�ĤH��m
		collision(); // �I���˴�
		drawImage(); // �e��
		repaint();

	}

	public void posRefresh() {
		// ��s�l�u��m�A�ò����W�X�ù��~���l�u
		bulletIterator = bullets.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			bullet.move();
			if (bullet.isOutOfScreen()) {
				bulletIterator.remove();
			}
		}
		// ��s�ĤH��m
		for (Enemy enemy : enemies) {			
	        enemy.randomMove();
	        }
			
			
        }   	


	public void collision() {
		// �ˬd�l�u�P�ĤH�I��
		bulletIterator = bullets.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			enemyIterator = enemies.iterator();
			while (enemyIterator.hasNext()) {
				Enemy enemy = enemyIterator.next();
				if (bullet.getBounds().intersects(enemy.getBounds())) {
					bulletIterator.remove();
					enemyIterator.remove();
					score += 10;
					break;
				}
			}
		}
	}

	public void drawImage() {
		myBuffer.setColor(Color.black);
		myBuffer.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		// ø�s�o��
		myBuffer.setColor(Color.WHITE);
		myBuffer.setFont(new Font("Arial", Font.BOLD, 20));
		myBuffer.drawString("Score: " + score, 10, 20);

		for (Bullet b : bullets) {
			b.drawShape(myBuffer);
		}
		for (Enemy en : enemies) {
			en.drawShape(myBuffer);
		}
		for (EnemyBullet  eb : enemyBullets) {
		    eb.drawShape(myBuffer); // �e�ĤH�l�u
		}
		starShip.drawShape(myBuffer);
	}
}