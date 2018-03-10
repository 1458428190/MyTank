/**
 * ����:���̷�
 * ����:̹����Ϸ1.0��
 * ʱ�䣺2017/05/14
 */
package com.tank2;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.*;

public class MyTankGame2 extends JFrame {

	/**
	 * @param args
	 */
	MyPanel mp = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame2 mtg = new MyTankGame2();
	}

	// ���캯��
	public MyTankGame2() {
		mp = new MyPanel();
		Thread t = new Thread(mp);
		t.start();
		this.addKeyListener(mp);
		this.add(mp);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setVisible(true);
	}

}

// �ҵ����
class MyPanel extends JPanel implements KeyListener,Runnable {

	// ����һ���ҵ�̹��
	Hero hero = null;
	
	//������˵�̹����
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	
	//��ʼ����������
	int enSize = 3;
	// ���캯��
	public MyPanel() {
		hero = new Hero(100, 100);
		hero.setSpeed(10);
		
		//��ʼ�����˵�̹��
		for(int i=0; i<enSize; i++)
		{
			//����һ�����˵�̹�˶���
			EnemyTank et = new EnemyTank((i+1)*50 ,0);
			//��ʼ����ɫ������
			et.setColor(0);
			et.setDirect(2);
			//����
			ets.add(et);
		}
	}

	// ����paint
	public void paint(Graphics g) {
		super.paint(g);
		//��䱳��
		g.fillRect(0, 0, 400, 300);
		//�����Լ�
		drawTank(hero.getX(), hero.getY(), g, this.hero.getDirect(), 1);
		
		//�����ӵ�
		if(hero.s!=null && hero.s.isLive==true)
		{
			g.draw3DRect(hero.s.x, hero.s.y, 1, 1, false);
		}
		
		//�����ִ�ĵ���̹��
		for(int i=0; i<ets.size(); i++)
		{
			EnemyTank et = ets.get(i);
			drawTank(et.getX(), et.getY(), g, et.getDirect(), et.getColor());
		}
	}

	public void drawTank(int x, int y, Graphics g, int direct, int type) {
		switch (type) {
		case 0:
			g.setColor(Color.CYAN);
			break;
		case 1:
			g.setColor(Color.yellow);
			break;
		}

		switch (direct) {
		/*
		 * ��̹��
		 *  case 0: //1.������ߵľ��� g.fill3DRect(x, y, 15, 90, false);
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

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//�����������
		if (e.getKeyCode() == KeyEvent.VK_DOWN
				|| e.getKeyCode() == KeyEvent.VK_S) {
			hero.moveDown();
			this.hero.setDirect(2);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			hero.moveUp();
			this.hero.setDirect(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_A) {
			hero.moveLeft();
			this.hero.setDirect(3);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_D) {
			hero.moveRight();
			this.hero.setDirect(1);
		}
		
		if(e.getKeyCode()==KeyEvent.VK_J)
		{
			this.hero.shotEnemy();
		}
		this.repaint();
	}

	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void run() {
		// TODO Auto-generated method stub
		//ˢ�����
		while(true)
		{
			try{
				Thread.sleep(100);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			this.repaint();
		}
	}
}
