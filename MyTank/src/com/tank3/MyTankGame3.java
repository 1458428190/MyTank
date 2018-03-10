/**
 * 作者:赖程锋
 * 功能:坦克游戏3.0版
 * 时间：2017/05/15
 * 可以发射子弹最多为5颗
 */
package com.tank3;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.*;

public class MyTankGame3 extends JFrame {

	/**
	 * @param args
	 */
	MyPanel mp = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyTankGame3 mtg = new MyTankGame3();
	}

	// 构造函数
	public MyTankGame3() {
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
			//初始化颜色跟方向(向下)
			et.setColor(0);
			et.setDirect(2);
			//加入
			ets.add(et);
		}
	}

	//判断子弹是否打中敌人坦克
	public void hitTank(Shot s, EnemyTank et)
	{
		switch (et.direct)
		{
		//向上,向下
		case 0:
		case 2:
			if(s.x>=et.x && s.x<=et.x+20 && s.y>=et.y && s.y<=et.y+30)
			{
				//击中
				//子弹死亡
				s.isLive = false;
				//敌人坦克死亡
				et.isLive = false;
			}
			break;
		//向左,向右
		case 1:
		case 3:
			if(s.x>=et.x && s.x<=et.x+30 && s.y>=et.y && s.y<=et.y+20)
			{
				s.isLive = false;
				et.isLive = false;
			}
			break;
		}
	}
	// 重新paint
	public void paint(Graphics g) {
		super.paint(g);
		//填充背景
		g.fillRect(0, 0, 400, 300);
		//画出自己
		drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		
		//System.out.println("子弹有 " + hero.ss.size() + "颗");
		for(int i=0; i<hero.ss.size(); i++)
		{
			Shot s = hero.ss.get(i);
			
			//画出子弹
			if(s.isLive==true)
			{
				g.draw3DRect(s.x, s.y, 1, 1, false);
			}
			else
				{
					//不能用i?
					hero.ss.remove(s);
				}
			
		}
		//画出现存的敌人坦克
		for(int i=0; i<ets.size(); i++)
		{
			EnemyTank et = ets.get(i);
			/*加了后,敌人部分坦克会闪?
			 * if(et.isLive==false)
			{
					ets.remove(et);
			}
			*/
			if(et.isLive)
			{
				drawTank(et.getX(), et.getY(), g, et.getDirect(), et.getColor());
			}
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
			//控制自己的子弹数,最多5颗
			if(this.hero.ss.size()<5)
			{
				this.hero.shotEnemy();
			}
		}
		//重画
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
			
			//判断所有自己发出的子弹有没有打中敌人
			for(int i=0; i<hero.ss.size(); i++)
			{
				//取出子弹
				Shot s = hero.ss.get(i);
				//判断子弹是否有效
				if(s.isLive)
				{
					for(int j=0; j<ets.size(); j++)
					{
						//判断坦克是否还活着
						if(ets.get(j).isLive)
							{
								this.hitTank(s, ets.get(j));
							}
						//如果敌人坦克已死,则去除
						if(!ets.get(j).isLive)
							{
								ets.remove(j);
							}
					}
				}
			}
			this.repaint();
		}
	}
}
