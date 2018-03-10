/**
 * ����:���̷�
 * ����:̹����Ϸ3.0��
 * ʱ�䣺2017/05/15
 * ���Է����ӵ����Ϊ5��
 * ���ҵ�̹�˻��е��˵�̹��ʱ,���˵�̹����ʧ,(���һᱬը)
 * ���˵�̹�˻�����߶�,������5���ӵ�
 */
package com.tank4;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MyTankGame4 extends JFrame
{

	/**
	 * @param args
	 */
	MyPanel mp = null;

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		MyTankGame4 mtg = new MyTankGame4();
	}

	// ���캯��
	public MyTankGame4()
	{
		mp = new MyPanel();
		Thread t = new Thread(mp);
		t.start();
		this.addKeyListener(mp);
		this.add(mp);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setLocation(0,0);
		this.setSize(420, 350);
		this.setVisible(true);
	}

}

// �ҵ����
class MyPanel extends JPanel implements KeyListener, Runnable
{

	// ����һ���ҵ�̹��
	Hero hero = null;

	// ������˵�̹����
	Vector<EnemyTank> ets = new Vector<EnemyTank>();

	// ����ը��
	Shot enemyShot = null;

	// ����ը��
	Vector<Bomb> bombs = new Vector<Bomb>();

	// ��ʼ����������
	int enSize = 3;

	// ����ըЧ����3��ͼƬ
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;

	// ���캯��
	public MyPanel()
	{
		hero = new Hero(100, 100);
		hero.setSpeed(10);
		this.setBackground(Color.BLACK);
		//ȥ����һ���ӵ����ᱬը�����
//		Bomb b1 = new Bomb(400, 300);
//		bombs.add(b1);
		// ��ʼ�����˵�̹��
		for (int i = 0; i < enSize; i++)
		{
			// ����һ�����˵�̹�˶���
			EnemyTank et = new EnemyTank((i + 1) * 50, 0);
			// ��ʼ����ɫ������(����)
			et.setColor(0);
			et.setDirect(2);

			// ��ӵ��˵��׸��ӵ�
			enemyShot = new Shot(et.x + 10, et.y + 30, 2);

			// �������̹�˵�Vector��
			et.ss.add(enemyShot);

			// �����ӵ��߳�
			Thread t1 = new Thread(enemyShot);
			t1.start();

			// ��������̹���߳�
			Thread t2 = new Thread(et);
			t2.start();
			// ����
			ets.add(et);
		}

		// ��ʼ��3��ͼƬ
//		//�˷���,��һ��ը��������ʾ(������)
//		image1 = Toolkit.getDefaultToolkit().getImage(
//				Panel.class.getResource("/bomb_1.gif"));
//		image2 = Toolkit.getDefaultToolkit().getImage(
//				Panel.class.getResource("/bomb_2.gif"));
//		image3 = Toolkit.getDefaultToolkit().getImage(
//				Panel.class.getResource("/bomb_3.gif"));
		
//		//�޸ĺ�
		try
		{
			image1 = ImageIO.read(new File("bomb_1.gif"));
			image2 = ImageIO.read(new File("bomb_2.gif"));
			image3 = ImageIO.read(new File("bomb_3.gif"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// �жϵ��˵��ӵ��Ƿ�����Լ�
	public void hitMe()
	{
		// �ж����еĵ���̹�˵��ӵ�,2��ѭ��
		for (int i = 0; i < this.ets.size(); i++)
		{
			EnemyTank et = ets.get(i);
			// ȡ�����е��˵��ӵ�
			for (int j = 0; j < et.ss.size(); j++)
			{
				Shot s = et.ss.get(j);
				if (s.isLive)
				{
					hitTank(s, hero);
				}
			}
		}
	}

	// �ж��ҵ��ӵ��Ƿ���е��˵�̹��
	public void hitEnemyTank()
	{
		// �ж������Լ��������ӵ���û�д��е���
		for (int i = 0; i < hero.ss.size(); i++)
		{
			// ȡ���ӵ�
			Shot s = hero.ss.get(i);
			// �ж��ӵ��Ƿ���Ч
			if (s.isLive)
			{
				// &&s.isLive�Ƿ�ֹ����,��������2��̹���ص�ʱ,һ���ӵ�������2��
				for (int j = 0; j < ets.size() && s.isLive; j++)
				{
					// �ж�̹���Ƿ񻹻���
					if (ets.get(j).isLive)
					{
						this.hitTank(s, ets.get(j));
					}
					// �������̹������,��ȥ��
					if (!ets.get(j).isLive)
					{
						ets.remove(j);
					}
				}
			}
		}
	}

	// �ж��ӵ��Ƿ���е���̹��
	public void hitTank(Shot s, Tank et)
	{
		if (et.isLive && s.isLive)
		{
			switch (et.direct)
			{
			// ����,����
			case 0:
			case 2:
				if (s.x >= et.x && s.x <= et.x + 20 && s.y >= et.y
						&& s.y <= et.y + 30)
				{
					// ����
					// �ӵ�����
					s.isLive = false;
					// ����̹������
					et.isLive = false;
					// ̹������, �������ӵ����
					// et.ss.clear();
					// ����һ��ը��,����Vector��
					Bomb b = new Bomb(et.x, et.y);
					bombs.add(b);

				}
				break;
			// ����,����
			case 1:
			case 3:
				if (s.x >= et.x && s.x <= et.x + 30 && s.y >= et.y
						&& s.y <= et.y + 20)
				{
					s.isLive = false;
					et.isLive = false;
					// ̹������, �������ӵ����
					// et.ss.clear();
					// ����һ��ը��,����Vector��
					Bomb b = new Bomb(et.x, et.y);
					bombs.add(b);
				}
				break;
			}
		}
	}

	// ����paint
	public void paint(Graphics g)
	{
		super.paint(g);
		//g.fillRect(0, 0, 400, 300);
		// �����Լ�
		if (hero.isLive)
		{
			drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		}

		// System.out.println("�ӵ��� " + hero.ss.size() + "��");
		for (int i = 0; i < hero.ss.size(); i++)
		{
			Shot s = hero.ss.get(i);

			// �����ӵ�
			if (s.isLive == true)
			{
				g.draw3DRect(s.x, s.y, 1, 1, false);
			} else
			{
				// ������i?
				hero.ss.remove(s);
			}

		}

		// ����ը��
		for (int i = 0; i < bombs.size(); i++)
		{
			// System.out.println("��ը?" + bombs.size());
			Bomb b = bombs.get(i);
			if (b.life > 6)
			{
				// System.out.println("��ը��һ��");
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			} else if (b.life > 3)
			{
				// System.out.println("��ը�ڶ���");
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			} else
			{
				// System.out.println("��ը������");
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			}

			// ��b������ֵ��С
			b.lifeDown();

			if (b.life == 0)
			{
				bombs.remove(b);
			}
		}
		// �����ִ�ĵ���̹��
		for (int i = 0; i < ets.size(); i++)
		{
			EnemyTank et = ets.get(i);
			/*
			 * ���˺�,���˲���̹�˻���? if(et.isLive==false) { ets.remove(et); }
			 */
			// �����Ҫ����̹������, �ӵ�������Ч�Ļ�, ��if(...)ȥ��, �Լ�������ӵ���ղ���ȥ��
			if (et.isLive)
			{
				drawTank(et.getX(), et.getY(), g, et.getDirect(), et.getColor());

				// ����ÿ������̹�˷�����ӵ�
				for (int j = 0; j < et.ss.size(); j++)
				{
					Shot s = et.ss.get(j);
					if (s.isLive)
						g.draw3DRect(s.x, s.y, 1, 1, false);
					else
					{
						et.ss.remove(s);
					}
				}
			}
		}
	}

	public void drawTank(int x, int y, Graphics g, int direct, int type)
	{
		switch (type)
		{
		case 0:
			g.setColor(Color.CYAN);
			break;
		case 1:
			g.setColor(Color.yellow);
			break;
		}

		switch (direct)
		{
		/*
		 * ��̹�� case 0: //1.������ߵľ��� g.fill3DRect(x, y, 15, 90, false);
		 * //2.�����ұߵľ��� g.fill3DRect(x+45, y, 15, 90, false);
		 * 
		 * //3.�����м�ľ��� g.fill3DRect(x+15, y+15, 30, 60, false);
		 * 
		 * //4.�����м��Բ g.fillOval(x+15, y+30, 30, 30);
		 * 
		 * //5.����ֱ�� g.drawLine(x+30, y+30, x+30, y); break;
		 * 
		 * //���� case 1: g.fill3DRect(x, y, 15, 90, false); g.fill3DRect(x+45, y,
		 * 15, 90, false); g.fill3DRect(x+15, y+15, 30, 60, false);
		 * g.fillOval(x+15, y+30, 30, 30); g.drawLine(x+30, y+30, x+30, y+90);
		 * break;
		 * 
		 * //���� case 2: g.fill3DRect(x, y, 90, 15, false); g.fill3DRect(x, y+45,
		 * 90, 15, false); g.fill3DRect(x+15, y+15, 60, 30, false);
		 * g.fillOval(x+30, y+15, 30, 30); g.drawLine(x+30, y+30, x+90, y+30);
		 * break;
		 * 
		 * //���� case 3: g.fill3DRect(x, y, 90, 15, false); g.fill3DRect(x, y+45,
		 * 90, 15, false); g.fill3DRect(x+15, y+15, 60, 30, false);
		 * g.fillOval(x+30, y+15, 30, 30); g.drawLine(x+30, y+30, x, y+30);
		 * break;
		 */

		// С̹��
		// ����
		case 0:
			// 1.������ߵľ���
			g.fill3DRect(x, y, 5, 30, false);
			// 2.�����ұߵľ���
			g.fill3DRect(x + 15, y, 5, 30, false);
			// 3.�����м�ľ���
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			// 4.�����м��Բ
			g.fillOval(x + 5, y + 10, 10, 10);
			// 5.����ֱ��
			g.drawLine(x + 10, y + 10, x + 10, y);
			break;

		// ����
		case 1:
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y + 15, 30, 5, false);
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			g.fillOval(x + 10, y + 5, 10, 10);
			g.drawLine(x + 10, y + 10, x + 30, y + 10);
			break;
		// ����
		case 2:
			g.fill3DRect(x, y, 5, 30, false);
			g.fill3DRect(x + 15, y, 5, 30, false);
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			g.fillOval(x + 5, y + 10, 10, 10);
			g.drawLine(x + 10, y + 10, x + 10, y + 30);
			break;

		// ����
		case 3:
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y + 15, 30, 5, false);
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			g.fillOval(x + 10, y + 5, 10, 10);
			g.drawLine(x + 10, y + 10, x, y + 10);
			break;
		}
	}

	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub
		// �����������
		if (e.getKeyCode() == KeyEvent.VK_DOWN
				|| e.getKeyCode() == KeyEvent.VK_S)
		{
			hero.moveDown();
			this.hero.setDirect(2);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
		{
			hero.moveUp();
			this.hero.setDirect(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A)
		{
			hero.moveLeft();
			this.hero.setDirect(3);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D)
		{
			hero.moveRight();
			this.hero.setDirect(1);
		}

		if (e.getKeyCode() == KeyEvent.VK_J)
		{
			// �����Լ����ӵ���,���5��
			if (this.hero.ss.size() < 5)
			{
				this.hero.shotEnemy();
			}
		}
		// �ػ�
		this.repaint();
	}

	public void keyReleased(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	public void run()
	{
		// TODO Auto-generated method stub
		// ˢ�����
		while (true)
		{
			try
			{
				Thread.sleep(100);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			// �����
			this.hitEnemyTank();

			// ���˴��Լ�
			//hitMe();

			this.repaint();
		}
	}
}
