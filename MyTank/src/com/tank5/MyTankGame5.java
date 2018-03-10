/**
 * 作者:赖程锋
 * 功能:坦克游戏5.0版
 * 时间：2017/05/15
 * (*)号表示还未完成
 * 1.画出坦克
 * 2.我的坦克可以上下左右移动,敌人的随机走动
 * 3.可以发射子弹,子弹连发(最多5颗)
 * 4.当我的坦克击中敌人坦克时,敌人消失(爆炸的效果)
 * 5.当我的坦克被击中(与敌人碰撞也算(*))时,显示爆炸效果
 * 6.防止敌人坦克重叠运动
 * 		6.1 决定把判断是否碰撞的函数写道EnemyTank类
 * 7.游戏可以存盘退出游戏,下次打开游戏时,恢复到上次退出的状态,接着继续玩游戏
 * 8.记录玩家一共消灭了多少辆敌人的坦克
 * 9.可以分关
 * 		9.1 做一个开始的Panel, 它是一个提示信息
 * 		9.2 做一个闪烁效果
 * 10.可以在玩游戏的时候暂停和继续
 * 		10.1 当用户点击空格时,游戏暂停或者继续(实现方法,将子弹的速度和坦克的速度设为0, 并让线程在暂停时停止)
 * 11.可以记录玩家的成绩
 * 		11.1 用文件流
 * 		11.2 单写一个记录类,完成对玩家记录
 * 		11.3 先完成保存共击毁了多少辆敌人坦克的功能
 * 12.java如何操作声音文件
 * 		12.1 对声音文件的操作
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
	//作出我的菜单
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

	// 构造函数
	public MyTankGame5()
	{
	
		jmb = new JMenuBar();
		jml = new JMenu("游戏(G)");
		//设置快捷方式
		jml.setMnemonic('G');
		jmi1 = new JMenuItem("开始新游戏(N)");
		jmi1.setMnemonic('N');
		jmi2 = new JMenuItem("退出游戏(E)");
		jmi2.setMnemonic('E');
		jmi3 = new JMenuItem("存盘退出(C)");
		jmi3.setMnemonic('C');
		jmi4 = new JMenuItem("继续上一盘(J)");
		jmi4.setMnemonic('J');
		
		//设置监听
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newGame");
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exit");
		jmi3.addActionListener(this);
		jmi3.setActionCommand("cunpan");
		jmi4.addActionListener(this);
		jmi4.setActionCommand("jixu");
		
		//添加组件
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
			//显示,刷新JFrame
			this.setVisible(true);
		}
		else if(e.getActionCommand().equals("exit")){
			Recoder.keepRecording();
			System.exit(0);
		}
		else if(e.getActionCommand().equals("cunpan"))
		{
			//存盘退出
			Recoder.setEts(mp.ets);
			Recoder.keepRecAndEnemyTank();
			System.exit(0);
		}
		else if(e.getActionCommand().equals("jixu"))
		{
			//继续上一局
			mp = new MyPanel("con");
			Thread t = new Thread(mp);
			t.start();
			this.remove(msp);
			this.add(mp);
			this.addKeyListener(mp);
			//this.setLocation(0,0);
			//显示,刷新JFrame
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
		
		//提示信息
		g.fillRect(0, 0, 400, 300);
		
		if(times%2==0)
		{
			g.setColor(Color.yellow);
			Font font = new Font("华文新魏", Font.BOLD, 30);
			g.setFont(font); 
			g.drawString("Stage: 1", 160, 140);
			if(times >= 10000)
				times = 0;    //防止溢出
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

// 我的面板
class MyPanel extends JPanel implements KeyListener, Runnable
{

	//保存按了几下空格
	public static boolean isRunning = true;
	
	// 定义一个我的坦克
	Hero hero = null;

	// 定义敌人的坦克组
	Vector<EnemyTank> ets = new Vector<EnemyTank>();

	// 敌人炸弹
	Shot enemyShot = null;

	// 定义炸弹
	Vector<Bomb> bombs = new Vector<Bomb>();

	// 初始化敌人数量
	int enSize = 7;

	// 供爆炸效果的3张图片
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;

	// 构造函数
	public MyPanel(String flag)
	{
		//将总成绩交给Mypanel,恢复记录
		Recoder.getRecording();
		hero = new Hero(100, 100);
		hero.setSpeed(10);
		//去除第一颗子弹不会爆炸的情况
//		Bomb b1 = new Bomb(400, 300);
//		bombs.add(b1);
		// 初始化敌人的坦克
		if(flag.equals("newGame"))
		{
			for (int i = 0; i < enSize; i++)
			{
				// 创建一辆敌人的坦克对象
				EnemyTank et = new EnemyTank((i + 1) * 50, 0);
				// 初始化颜色跟方向(向下)
				et.setColor(0);
				et.setDirect(2);
				et.setEts(ets);
				// 添加敌人的首个子弹
				enemyShot = new Shot(et.x + 10, et.y + 30, 2);
	
				// 加入敌人坦克的Vector中
				et.ss.add(enemyShot);
	
				// 启动子弹线程
				Thread t1 = new Thread(enemyShot);
				t1.start();
	
				// 启动敌人坦克线程
				Thread t2 = new Thread(et);
				t2.start();
				// 加入
				ets.add(et);
			}
		}
		//继续上一局
		else {
			Vector<Node> nodes = Recoder.getNodesAndEnNums();
			for (int i = 0; i < nodes.size(); i++)
			{
				Node node = nodes.get(i);
				// 创建一辆敌人的坦克对象
				EnemyTank et = new EnemyTank(node.x, node.y);
				// 初始化颜色跟方向(向下)
				et.setColor(0);
				et.setDirect(node.direct);
				et.setEts(ets);
				// 添加敌人的首个子弹
				enemyShot = new Shot(et.x + 10, et.y + 30, 2);
	
				// 加入敌人坦克的Vector中
				et.ss.add(enemyShot);
	
				// 启动子弹线程
				Thread t1 = new Thread(enemyShot);
				t1.start();
	
				// 启动敌人坦克线程
				Thread t2 = new Thread(et);
				t2.start();
				// 加入
				ets.add(et);
			}
		}
		// 初始化3张图片
//		//此方法,第一颗炸弹不会显示(不明显)
//		image1 = Toolkit.getDefaultToolkit().getImage(
//				Panel.class.getResource("/bomb_1.gif"));
//		image2 = Toolkit.getDefaultToolkit().getImage(
//				Panel.class.getResource("/bomb_2.gif"));
//		image3 = Toolkit.getDefaultToolkit().getImage(
//				Panel.class.getResource("/bomb_3.gif"));
		
//		//修改后
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

	// 判断敌人的子弹是否打中自己
	public void hitMe()
	{
		// 判断所有的敌人坦克的子弹,2层循环
		for (int i = 0; i < this.ets.size(); i++)
		{
			EnemyTank et = ets.get(i);
			// 取出所有敌人的子弹
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

	// 判断我的子弹是否击中敌人的坦克
	public void hitEnemyTank()
	{
		// 判断所有自己发出的子弹有没有打中敌人
		for (int i = 0; i < hero.ss.size(); i++)
		{
			// 取出子弹
			Shot s = hero.ss.get(i);
			// 判断子弹是否有效
			if (s.isLive)
			{
				// &&s.isLive是防止出现,当敌人有2个坦克重叠时,一个子弹打死了2个
				for (int j = 0; j < ets.size() && s.isLive; j++)
				{
					// 判断坦克是否还活着
					if (ets.get(j).isLive)
					{
						if(this.hitTank(s, ets.get(j)))
						{
							Recoder.reduceEnNum();
							Recoder.addEnNum();
						}
					}
					// 如果敌人坦克已死,则去除
					if (!ets.get(j).isLive)
					{
						ets.remove(j);
					}
				}
			}
		}
	}

	// 判断子弹是否打中敌人坦克
	public boolean hitTank(Shot s, Tank et)
	{
		boolean bs = false;
		if (et.isLive && s.isLive)
		{
			switch (et.direct)
			{
			// 向上,向下
			case 0:
			case 2:
				if (s.x >= et.x && s.x <= et.x + 20 && s.y >= et.y
						&& s.y <= et.y + 30)
				{
					// 击中
					// 子弹死亡
					s.isLive = false;
					// 敌人坦克死亡
					et.isLive = false;
					bs = true;
					// 坦克已死, 让它的子弹清空
					// et.ss.clear();
					// 创建一颗炸弹,放入Vector中
					Bomb b = new Bomb(et.x, et.y);
					bombs.add(b);
					

				}
				break;
			// 向左,向右
			case 1:
			case 3:
				if (s.x >= et.x && s.x <= et.x + 30 && s.y >= et.y
						&& s.y <= et.y + 20)
				{
					s.isLive = false;
					et.isLive = false;
					// 坦克已死, 让它的子弹清空
					bs = true;
					// et.ss.clear();
					// 创建一颗炸弹,放入Vector中
					Bomb b = new Bomb(et.x, et.y);
					bombs.add(b);
				}
				break;
			}
		}
		return bs;
	}
	
	//画出提示信息
	public void showInfo(Graphics g)
	{
		//画出提示信息坦克, 该坦克不参与战斗
		this.drawTank(80, 330, g, 0, 0);
		g.setColor(Color.black);
		g.drawString("X"+Recoder.getEnNum(), 100, 350);
		
		//画出自己的坦克信息
		this.drawTank(130, 330, g, 0, 1);
		g.setColor(Color.black);
		g.drawString("X"+Recoder.getMyLife(), 150, 350);
		
		//画出玩家的总成绩
		g.setColor(Color.black);
		Font font = new Font("宋体", Font.BOLD, 20);
		g.setFont(font);
		g.drawString("您的总成绩", 420, 30);
		this.drawTank(420, 60, g, 0, 0);
		
		g.setColor(Color.black);
		g.drawString("X"+Recoder.getAllEnNum(), 450, 80);
	}
	
	// 重新paint
	public void paint(Graphics g)
	{
		super.paint(g);
		g.fillRect(0, 0, 400, 300);
		
		//画出提示信息
		this.showInfo(g);
		
		// 画出自己
		if (hero.isLive)
		{
			drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
		}

		// System.out.println("子弹有 " + hero.ss.size() + "颗");
		for (int i = 0; i < hero.ss.size(); i++)
		{
			Shot s = hero.ss.get(i);

			// 画出子弹
			if (s.isLive == true)
			{
				g.draw3DRect(s.x, s.y, 1, 1, false);
			} else
			{
				// 不能用i?
				hero.ss.remove(s);
			}

		}

		// 画出炸弹
		for (int i = 0; i < bombs.size(); i++)
		{
			// System.out.println("爆炸?" + bombs.size());
			Bomb b = bombs.get(i);
			if (b.life > 6)
			{
				// System.out.println("爆炸第一张");
				g.drawImage(image1, b.x, b.y, 30, 30, this);
			} else if (b.life > 3)
			{
				// System.out.println("爆炸第二张");
				g.drawImage(image2, b.x, b.y, 30, 30, this);
			} else
			{
				// System.out.println("爆炸第三张");
				g.drawImage(image3, b.x, b.y, 30, 30, this);
			}

			// 让b的生命值减小
			b.lifeDown();

			if (b.life == 0)
			{
				bombs.remove(b);
			}
		}
		// 画出现存的敌人坦克
		for (int i = 0; i < ets.size(); i++)
		{
			EnemyTank et = ets.get(i);
			/*
			 * 加了后,敌人部分坦克会闪? if(et.isLive==false) { ets.remove(et); }
			 */
			// 如果想要敌人坦克死后, 子弹还会起效的话, 把if(...)去掉, 以及下面的子弹清空操作去除
			if (et.isLive)
			{
				drawTank(et.getX(), et.getY(), g, et.getDirect(), et.getColor());

				// 画出每个敌人坦克发射的子弹
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
		 * 大坦克 case 0: //1.画出左边的矩形 g.fill3DRect(x, y, 15, 90, false);
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

	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub
		// 按各个方向键
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
			// 控制自己的子弹数,最多5颗
			if (this.hero.ss.size() < 5)
			{
				this.hero.shotEnemy();
			}
		}
		}
		//按下空格
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
		// 重画
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
		// 刷新面板
		while (true)
		{
			try
			{
				Thread.sleep(100);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			// 打敌人
			this.hitEnemyTank();

			// 敌人打自己
			//if(hero.isLive)
			//hitMe();

			this.repaint();
		}
	}
}
