/**
 * ����:���̷�
 * ����:̹����Ϸ5.0��
 * ʱ�䣺2017/05/15
 * (*)�ű�ʾ��δ���
 * 1.����̹��
 * 2.�ҵ�̹�˿������������ƶ�,���˵�����߶�
 * 3.���Է����ӵ�,�ӵ�����(���5��)
 * 4.���ҵ�̹�˻��е���̹��ʱ,������ʧ(��ը��Ч��)
 * 5.���ҵ�̹�˱�����(�������ײҲ��(*))ʱ,��ʾ��ըЧ��
 * 6.��ֹ����̹���ص��˶�
 * 		6.1 �������ж��Ƿ���ײ�ĺ���д��EnemyTank��
 * 7.��Ϸ���Դ����˳���Ϸ,�´δ���Ϸʱ,�ָ����ϴ��˳���״̬,���ż�������Ϸ
 * 8.��¼���һ�������˶��������˵�̹��
 * 9.���Էֹ�
 * 		9.1 ��һ����ʼ��Panel, ����һ����ʾ��Ϣ
 * 		9.2 ��һ����˸Ч��
 * 10.����������Ϸ��ʱ����ͣ�ͼ���
 * 		10.1 ���û�����ո�ʱ,��Ϸ��ͣ���߼���(ʵ�ַ���,���ӵ����ٶȺ�̹�˵��ٶ���Ϊ0, �����߳�����ͣʱֹͣ)
 * 11.���Լ�¼��ҵĳɼ�
 * 		11.1 ���ļ���
 * 		11.2 ��дһ����¼��,��ɶ���Ҽ�¼
 * 		11.3 ����ɱ��湲�����˶���������̹�˵Ĺ���
 * 12.java��β��������ļ�
 * 		12.1 �������ļ��Ĳ���
 */
package com.tank5;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MyTankGame5 extends JFrame implements ActionListener
{

	/**
	 * @param args
	 */
	MyPanel mp = null;
	MyStartPanel msp;
	//�����ҵĲ˵�
	JMenuBar jmb;
	JMenu jml;
	JMenuItem jmi1;
	JMenuItem jmi2;
	JMenuItem jmi3;
	JMenuItem jmi4;
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		MyTankGame5 mtg = new MyTankGame5();
	}

	// ���캯��
	public MyTankGame5()
	{
	
		jmb = new JMenuBar();
		jml = new JMenu("��Ϸ(G)");
		//���ÿ�ݷ�ʽ
		jml.setMnemonic('G');
		jmi1 = new JMenuItem("��ʼ����Ϸ(N)");
		jmi1.setMnemonic('N');
		jmi2 = new JMenuItem("�˳���Ϸ(E)");
		jmi2.setMnemonic('E');
		jmi3 = new JMenuItem("�����˳�(C)");
		jmi3.setMnemonic('C');
		jmi4 = new JMenuItem("������һ��(J)");
		jmi4.setMnemonic('J');
		
		//���ü���
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newGame");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("cunpan");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("jixu");
		
		//������
		jml.add(jmi1);
		jml.add(jmi2);
		jml.add(jmi3);
		jml.add(jmi4);
		
		jmb.add(jml);
		
		msp = new MyStartPanel();
		Thread t = new Thread(msp);
		t.start();
		
		this.setJMenuBar(jmb);
		this.add(msp);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600, 500);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("newGame"))
		{
			mp = new MyPanel("newGame");
			Thread t = new Thread(mp);
			t.start();
			this.remove(msp);
			this.add(mp);
			this.addKeyListener(mp);
			//this.setLocation(0,0);
			//��ʾ,ˢ��JFrame
			this.setVisible(true);
		}
		else if(e.getActionCommand().equals("exit")){
			Recoder.keepRecording();
			System.exit(0);
		}
		else if(e.getActionCommand().equals("cunpan"))
		{
			//�����˳�
			Recoder.setEts(mp.ets);
			Recoder.keepRecAndEnemyTank();
			System.exit(0);
		}
		else if(e.getActionCommand().equals("jixu"))
		{
			//������һ��
			mp = new MyPanel("con");
			Thread t = new Thread(mp);
			t.start();
			this.remove(msp);
			this.add(mp);
			this.addKeyListener(mp);
			//this.setLocation(0,0);
			//��ʾ,ˢ��JFrame
			this.setVisible(true);
		}
	}
}

class MyStartPanel extends JPanel implements Runnable
{
	int times = 0;
	public void paint(Graphics g)
	{
		super.paint(g);
		
		//��ʾ��Ϣ
		g.fillRect(0, 0, 400, 300);
		
		if(times%2==0)
		{
			g.setColor(Color.yellow);
			Font font = new Font("������κ", Font.BOLD, 30);
			g.setFont(font); 
			g.drawString("Stage: 1", 160, 140);
			if(times >= 10000)
				times = 0;    //��ֹ���
		}
	}
	
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			times++;
			this.repaint();
		}
	}
}

// �ҵ����
class MyPanel extends JPanel implements KeyListener, Runnable
{

	//���水�˼��¿ո�
	public static boolean isRunning = true;
	
	// ����һ���ҵ�̹��
	Hero hero = null;

	// ������˵�̹����
	Vector<EnemyTank> ets = new Vector<EnemyTank>();

	// ����ը��
	Shot enemyShot = null;

	// ����ը��
	Vector<Bomb> bombs = new Vector<Bomb>();

	// ��ʼ����������
	int enSize = 7;

	// ����ըЧ����3��ͼƬ
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;

	// ���캯��
	public MyPanel(String flag)
	{
		//���ܳɼ�����Mypanel,�ָ���¼
		Recoder.getRecording();
		hero = new Hero(100, 100);
		hero.setSpeed(10);
		//ȥ����һ���ӵ����ᱬը�����
//		Bomb b1 = new Bomb(400, 300);
//		bombs.add(b1);
		// ��ʼ�����˵�̹��
		if(flag.equals("newGame"))
		{
			for (int i = 0; i < enSize; i++)
			{
				// ����һ�����˵�̹�˶���
				EnemyTank et = new EnemyTank((i + 1) * 50, 0);
				// ��ʼ����ɫ������(����)
				et.setColor(0);
				et.setDirect(2);
				et.setEts(ets);
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
		}
		//������һ��
		else {
			Vector<Node> nodes = Recoder.getNodesAndEnNums();
			for (int i = 0; i < nodes.size(); i++)
			{
				Node node = nodes.get(i);
				// ����һ�����˵�̹�˶���
				EnemyTank et = new EnemyTank(node.x, node.y);
				// ��ʼ����ɫ������(����)
				et.setColor(0);
				et.setDirect(node.direct);
				et.setEts(ets);
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
		AePlayWave apw = new AePlayWave("E:\\workspace/MyTank/111.wav");
		apw.start();
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
					if(hitTank(s, hero))
						{
							Recoder.reduceMyLife();
						}
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
						if(this.hitTank(s, ets.get(j)))
						{
							Recoder.reduceEnNum();
							Recoder.addEnNum();
						}
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
	public boolean hitTank(Shot s, Tank et)
	{
		boolean bs = false;
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
					bs = true;
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
					bs = true;
					// et.ss.clear();
					// ����һ��ը��,����Vector��
					Bomb b = new Bomb(et.x, et.y);
					bombs.add(b);
				}
				break;
			}
		}
		return bs;
	}
	
	//������ʾ��Ϣ
	public void showInfo(Graphics g)
	{
		//������ʾ��Ϣ̹��, ��̹�˲�����ս��
		this.drawTank(80, 330, g, 0, 0);
		g.setColor(Color.black);
		g.drawString("X"+Recoder.getEnNum(), 100, 350);
		
		//�����Լ���̹����Ϣ
		this.drawTank(130, 330, g, 0, 1);
		g.setColor(Color.black);
		g.drawString("X"+Recoder.getMyLife(), 150, 350);
		
		//������ҵ��ܳɼ�
		g.setColor(Color.black);
		Font font = new Font("����", Font.BOLD, 20);
		g.setFont(font);
		g.drawString("�����ܳɼ�", 420, 30);
		this.drawTank(420, 60, g, 0, 0);
		
		g.setColor(Color.black);
		g.drawString("X"+Recoder.getAllEnNum(), 450, 80);
	}
	
	// ����paint
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		//������ʾ��Ϣ
		this.showInfo(g);
		
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
		if(isRunning)
		{
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
		}
		//���¿ո�
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			isRunning = !isRunning;
			if(!isRunning)
			{
				for(int i=0; i<ets.size(); i++)
				{
					EnemyTank et = ets.get(i);
					et.setSpeed(0);
					for(int j=0; j<et.ss.size(); j++)
					{
						et.ss.get(j).setSpeed(0);
					}
				}
				for(int j=0; j<hero.ss.size(); j++)
				{
					hero.ss.get(j).setSpeed(0);
				}
			}
			else {
				for(int i=0; i<ets.size(); i++)
				{
					EnemyTank et = ets.get(i);
					et.setSpeed(1);
					for(int j=0; j<et.ss.size(); j++)
					{
						et.ss.get(j).setSpeed(4);
					}
				}
				for(int j=0; j<hero.ss.size(); j++)
				{
					hero.ss.get(j).setSpeed(4);
				}
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
			//if(hero.isLive)
			//hitMe();

			this.repaint();
		}
	}
}
