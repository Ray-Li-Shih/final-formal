import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StarShip_panel extends JPanel implements ActionListener,KeyListener {
	private int shipX = 400;
	private int shipY = 500;
	
	int SHIP_WIDTH = 20;
	int SHIP_HEIGHT = 20;
	// �l�u
    private List<Point> bullets = new ArrayList<Point>();

    // �ĤH
    private List<Rectangle> enemies = new ArrayList<Rectangle>();
    private int enemySpeed = 2;           // ��������t��
    private static final int DROP_DIST = 10; // �C���U���q

    // ����
    private int score = 0;
	
    private Timer timer, hold_timer;
	
	
//	private ArrayList<Bullet> bullets = new ArrayList<>();
	
	public StarShip_panel() {
		setFocusable(true);
		addKeyListener(this); 
		// ��l�ƼĤH�G4 �� 8 �C�A�C�� 40��20�A���j 10 px
        int rows = 4, cols = 8;
        int w = 40, h = 20, paddingX = 10, paddingY = 10;
        int startX = 30, startY = 30;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * (w + paddingX);
                int y = startY + r * (h + paddingY);
                enemies.add(new Rectangle(x, y, w, h));
            }
        }
		timer = new Timer(20, this); // �C20ms��s�@��
		timer.start();
		hold_timer = new Timer(80, e -> spawnBullet());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);

		// �e����
		int[] shipOutLineX = new int[3], shipOutLineY = new int[3];
		for (int i = 0; i < 3; i++) {
			shipOutLineX[i] = (shipX - 10) + 10 * i;
			if (i == 0 || i == 2) {
				shipOutLineY[i] = shipY + 9;
			} else {
				shipOutLineY[i] = shipY - 9;
			}
		}
		
		g.setColor(Color.CYAN);
		g.fillPolygon(shipOutLineX, shipOutLineY, 3);

		// �e�l�u
		g.setColor(Color.YELLOW);
		for (Point b : bullets) {
			g.fillRect(b.x, b.y, 5, 10);
		}
		// �e�ĤH
        g.setColor(Color.RED);
        for (Rectangle e : enemies) {
            g.fillRect(e.x, e.y, e.width, e.height);
        }

        // �e����
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// �l�u����
		for (Point b : bullets) {
			b.y -= 10;
		}
		// �R���W�X�e�����l�u
		bullets.removeIf(bullet -> bullet.y < 0);
		// 2. ��s�ĤH��m�G�������ʡA�I��N�ϦV�äU��
        boolean hitEdge = false;
        for (Rectangle enemy : enemies) {
            enemy.x += enemySpeed;
            if (enemy.x < 0 || enemy.x + enemy.width > getWidth()) {
                hitEdge = true;
            }
        }
        if (hitEdge) {
            enemySpeed = -enemySpeed;
            for (Rectangle enemy : enemies) {
                enemy.y += DROP_DIST;
            }
        }
     // 3. �I���˴��G�l�u vs �ĤH
        Iterator<Rectangle> eit = enemies.iterator();
        while (eit.hasNext()) {
            Rectangle enemy = eit.next();
            boolean removed = false;
            Iterator<Point> bit2 = bullets.iterator();
            while (bit2.hasNext()) {
                Point b = bit2.next();
                Rectangle shot = new Rectangle(b.x, b.y, 4, 10);
                if (shot.intersects(enemy)) {
                    // �I���G�����ĤH�P�l�u�A+1 ��
                    eit.remove();
                    bit2.remove();
                    score += 1;
                    removed = true;
                    break;
                }
            }
            if (removed) {
                // �p�G�o�ӼĤH�w�Q�����A���ΦA�ˬd��
                continue;
            }
        }

        repaint();
	}
	
	public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // ���k����
        if (code == KeyEvent.VK_LEFT && shipX > 10) {
            shipX -= 10;
        } else if (code == KeyEvent.VK_RIGHT && shipX < getWidth() - SHIP_WIDTH) {
            shipX += 10;
        }else if (code == KeyEvent.VK_UP && shipY > 10) {
            shipY -= 10;
        } else if (code == KeyEvent.VK_DOWN && shipY < getHeight() - SHIP_HEIGHT) {
            shipY += 10;
        }
        // �ť���o�g
        else if (code == KeyEvent.VK_SPACE) {
        	hold_timer.start();
        	if (code == KeyEvent.VK_LEFT && shipX > 10) {
                shipX -= 10;
            } else if (code == KeyEvent.VK_RIGHT && shipX < getWidth() - SHIP_WIDTH) {
                shipX += 10;
            }else if (code == KeyEvent.VK_UP && shipY > 10) {
                shipY -= 10;
            } else if (code == KeyEvent.VK_DOWN && shipY < getHeight() - SHIP_HEIGHT) {
                shipY += 10;
            }
        }
        repaint();
    }
	
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_SPACE) {
			hold_timer.stop();
        }
	}
	public void spawnBullet() {
		bullets.add(new Point(shipX, shipY)); 
	}
	


	class Bullet {
		int x, y;

		Bullet(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}	

}