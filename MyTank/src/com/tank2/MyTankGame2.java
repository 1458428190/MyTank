/**
 * 作者:赖程锋
 * 功能:坦克游戏1.0版
 * 时间：2017/05/14
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

	// 构造函数
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

// 我的面板
class MyPanel extends JPanel implements KeyListener,Runnable {

	// 定义一个我的坦克
	Hero hero = null;
	
	//定义敌人的坦克组
	Vector<EnemyTank> ets = new Vector<EnemyTank>();
	
	//初始化敌人数量
	int enSize = 3;
	// 构造函数
	public MyPanel() {
		hero = new Hero(100, 100);
		hero.setSpeed(10);
		
		//初始化敌人的坦克
		for(int i=0; i<enSize; i++)
		{
			//创建一辆敌人的坦克对象
			EnemyTank et = new EnemyTank((i+1)*50 ,0);
			//初始化颜色跟方向
			et.setColor(0);
			et.setDirect(2);
			//加入
			ets.add(et);
		}
	}

	// 重新paint
	public void paint(Graphics g) {
		super.paint(g);
		//填充背景
		g.fillRect(0, 0, 400, 300);
		//画出自己
		drawTank(hero.getX(), hero.getY(), g, this.hero.getDirect(), 1);
		
		//画出子弹
		if(hero.s!=null && hero.s.isLive==true)
		{
			g.draw3DRect(hero.s.x, hero.s.y, 1, 1, false);
		}
		
		//画出现存的敌人坦克
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
		 * 大坦克
		 *  case 0: //1.画出左边的矩形 g.fill3DRect(x, y, 15, 90, false);
		 * //2.画出右边的矩形 g.fill3DRect(x+45, y, 15, 90, false);
		 * 
		 * //3.画出中间的矩形 g.fill3DRect(x+15, y+15, 30, 60, false);
		 * 
		 * //4.画出中间的圆 g.fillOval(x+15, y+30, 30, 30);
		 * 
		 * //5.画出直线 g.drawLine(x+30, y+30, x+30, y); break;
		 * 
		 * //向下 case 1: g.fill3DRect(x, y, 15, 90, false); g.fill3DRect(x+45, y,
		 * 15, 90, false); g.fill3DRect(x+15, y+15, 30, 60, false);
		 * g.fillOval(x+15, y+30, 30, 30); g.drawLine(x+30, y+30, x+30, y+90);
		 * break;
		 * 
		 * //向左 case 2: g.fill3DRect(x, y, 90, 15, false); g.fill3DRect(x, y+45,
		 * 90, 15, false); g.fill3DRect(x+15, y+15, 60, 30, false);
		 * g.fillOval(x+30, y+15, 30, 30); g.drawLine(x+30, y+30, x+90, y+30);
		 * break;
		 * 
		 * //向右 case 3: g.fill3DRect(x, y, 90, 15, false); g.fill3DRect(x, y+45,
		 * 90, 15, false); g.fill3DRect(x+15, y+15, 60, 30, false);
		 * g.fillOval(x+30, y+15, 30, 30); g.drawLine(x+30, y+30, x, y+30);
		 * break;
		 */

		// 小坦克
		// 向上
		case 0:
			// 1.画出左边的矩形
			g.fill3DRect(x, y, 5, 30, false);
			// 2.画出右边的矩形
			g.fill3DRect(x + 15, y, 5, 30, false);
			// 3.画出中间的矩形
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			// 4.画出中间的圆
			g.fillOval(x + 5, y + 10, 10, 10);
			// 5.画出直线
			g.drawLine(x + 10, y + 10, x + 10, y);
			break;

		// 向右
		case 1:
			g.fill3DRect(x, y, 30, 5, false);
			g.fill3DRect(x, y + 15, 30, 5, false);
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			g.fillOval(x + 10, y + 5, 10, 10);
			g.drawLine(x + 10, y + 10, x + 30, y + 10);
			break;
		// 向下
		case 2:
			g.fill3DRect(x, y, 5, 30, false);
			g.fill3DRect(x + 15, y, 5, 30, false);
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			g.fillOval(x + 5, y + 10, 10, 10);
			g.drawLine(x + 10, y + 10, x + 10, y + 30);
			break;

		// 向左
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
		//按各个方向键
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
		//刷新面板
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
