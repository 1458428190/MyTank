package com.tank5;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

//播放声音的类
class AePlayWave extends Thread {

	private String filename;
	public AePlayWave(String wavfile) {
		filename = wavfile;

	}

	public void run() {

		File soundFile = new File(filename);

		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		auline.start();
		int nBytesRead = 0;
		//这是缓冲
		byte[] abData = new byte[512];

		try {
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0)
					auline.write(abData, 0, nBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			auline.drain();
			auline.close();
		}

	}

	
}

//记录类,同时也可以保存玩家的设置
class Node
{
	int x;
	int y;
	int direct;
	public Node(int x, int y, int direct)
	{
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
}

class Recoder {
	// 记录每关有多少敌人
	private static int enNum = 20;
	// 设置我有多少命
	private static int myLife = 3;
	//总共消灭多少个坦克
	private static int allEnNum = 0;
	
	private static FileWriter wf;
	private static BufferedWriter bw;
	private static FileReader fr;
	private static BufferedReader br;
	
	//把敌人的坦克传进来
	private static Vector<EnemyTank> ets = new Vector<EnemyTank>();
	//读取敌人的坦克的坐标和方向
	private static Vector<Node> nodes = new Vector<Node>();
	
	
	public static Vector<EnemyTank> getEts() {
		return ets;
	}

	public static void setEts(Vector<EnemyTank> ets) {
		Recoder.ets = ets;
	}

	//完成读取任务
	public static Vector<Node> getNodesAndEnNums()
	{
		try {
			fr = new FileReader("E:\\workspace/MyTank/myRecoder.txt");
			br = new BufferedReader(fr);
			String n = br.readLine();
			allEnNum = Integer.parseInt(n);
			while((n = br.readLine())!=null)
			{
				String []xyz = n.split(" ");
				Node node =new Node(Integer.parseInt(xyz[0]), Integer.parseInt(xyz[1]), Integer.parseInt(xyz[2]));
				nodes.add(node);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			try {
				br.close();
				fr.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return nodes;
	}
	
	//保存活着的敌人坦克的坐标
	public static void keepRecAndEnemyTank()
	{
		try {
			wf = new FileWriter("E:\\workspace/MyTank/myRecoder.txt");
			bw = new BufferedWriter(wf);
			bw.write(allEnNum+"\r\n");
			for(int i=0; i<ets.size(); i++)
			{
				EnemyTank et = ets.get(i);
				if(et.isLive)
					bw.write(et.x+" "+et.y+" "+et.direct+"\r\n");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			try {
				bw.close();
				wf.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	//从文件中读取, 记录
	public static void getRecording()
	{
		try {
			fr = new FileReader("E:\\workspace/MyTank/myRecoder.txt");
			br = new BufferedReader(fr);
			String n = br.readLine();
			allEnNum = Integer.parseInt(n);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			try {
				br.close();
				fr.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	//把玩家击毁敌人坦克数量保存到文件中, , 与上面的可重用
	public static void keepRecording()
	{
		try {
			wf = new FileWriter("E:\\workspace/MyTank/myRecoder.txt");
			bw = new BufferedWriter(wf);
			bw.write(allEnNum+"\r\n");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			try {
				bw.close();
				wf.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	public static int getAllEnNum() {
		return allEnNum;
	}
	public static void setAllEnNum(int allEnNum) {
		Recoder.allEnNum = allEnNum;
	}
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recoder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recoder.myLife = myLife;
	}
	
	//减少敌人数
	public static void reduceEnNum()
	{
		enNum--;
	}
	
	//减少自己的生命
	public static void reduceMyLife()
	{
		myLife--;
	}
	
	//增加消灭的敌人
	public static void addEnNum()
	{
		allEnNum++;
	}
}


//子弹
class Shot implements Runnable
{
	int x;
	int y;
	int direct;
	int speed = 4;
	boolean isLive = true;

	//修改子弹的速度
	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public Shot(int x, int y, int direct)
	{
		this.x = x;
		this.y = y;
		this.direct = direct;
	}

	public void run()
	{
		// TODO Auto-generated method stub
		while (true)
		{
			try
			{
				Thread.sleep(50);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

			switch (this.direct)
			{
			case 0:
				// 向上
				this.y -= speed;
				break;
			case 1:
				// 向右
				this.x += speed;
				break;
			case 2:
				// 向下
				this.y += speed;
				break;
			case 3:
				// 向左
				this.x -= speed;
				break;
			}

			// System.out.println("子弹在 x=" + x + " y="+y);
			// 判断子弹什么时候死亡
			if (x < 0 || x > 400 || y < 0 || y > 300)
			{
				isLive = false;
				break;
			}
		}
	}
}

class Bomb
{
	// 定义炸弹的坐标
	int x, y;

	// 炸弹的生命
	int life = 9;

	// 存放炸弹的死活
	boolean isLive = true;

	public Bomb(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	// 减少生命值
	public void lifeDown()
	{
		if (life > 0)
			life--;
		else
		{
			isLive = false;
		}
	}
}

// 坦克类
class Tank
{
	// 表示坦克的横坐标
	int x = 0;
	// 坦克纵坐标
	int y = 0;
	// 方向
	int direct = 0;
	// 类型
	int type = 0;
	// 坦克的速度
	int speed = 1;
	// 坦克的颜色
	int color;
	//坦克的死活
	boolean isLive = true;
	public int getColor()
	{
		return color;
	}

	public void setColor(int color)
	{
		this.color = color;
	}

	public int getSpeed()
	{
		return speed;
	}

	public void setSpeed(int speed)
	{
		this.speed = speed;
	}

	public int getDirect()
	{
		return direct;
	}

	public void setDirect(int direct)
	{
		this.direct = direct;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public Tank(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}

// 敌人的坦克
class EnemyTank extends Tank implements Runnable
{

	//给敌人坦克添加子弹时用的
	int times = 0;
	
	// 敌人坦克发出的子弹
	Vector<Shot> ss = new Vector<Shot>();
	Vector<EnemyTank> ets= new Vector<EnemyTank>();
	Shot s = null;
	
	public void setEts(Vector<EnemyTank> vv)
	{
		this.ets = vv;
	}
	
	public EnemyTank(int x, int y)
	{
		super(x, y);
	}

	public boolean isTouchOtherEnemy()
	{
		boolean b = false;
		switch(this.direct)
		{
		//向上
		case 0:
			for(int i=0; i<ets.size(); i++)
			{
				EnemyTank et = ets.get(i);
				if(et!=this)
				{
				if(et.direct==0||et.direct==2)
				{
					if(((this.x>=et.x && this.x<=et.x+20)
						||(this.x+20>=et.x&&this.x+20<=et.x+20))&& this.y>=et.y && this.y<=et.y+30)
						b = true;
				}
				if(et.direct==1||et.direct==3)
				{
					if(((this.x>=et.x && this.x<=et.x+30)
							||(this.x+20>=et.x&&this.x+20<=et.x+30))&& this.y>=et.y && this.y<=et.y+20)
							b = true;
				}
				}
			}
			break;
		//向右
		case 1:
			for(int i=0; i<ets.size(); i++)
			{
				EnemyTank et = ets.get(i);
				if(et!=this)
				{
				if(et.direct==0||et.direct==2)
				{
					if(this.x+30>=et.x && this.x+30<=et.x+20&&
						((this.y>=et.y&&this.y<=et.y+30)||(this.y+20>=et.y && this.y+20<=et.y+30)))
						b = true;
				}
				if(et.direct==1||et.direct==3)
				{
					if(this.x+30>=et.x && this.x+30<=et.x+30&&
							((this.y>=et.y&&this.y<=et.y+20)||(this.y+20>=et.y && this.y+20<=et.y+20)))
							b = true;
				}
				}
			}
			break;
		//向下
		case 2:
			for(int i=0; i<ets.size(); i++)
			{
				EnemyTank et = ets.get(i);
				if(et!=this)
				{
				if(et.direct==0||et.direct==2)
				{
					if(((this.x>=et.x && this.x<=et.x+20)
						||(this.x+20>=et.x&&this.x+20<=et.x+20))&& this.y+30>=et.y && this.y+30<=et.y+30)
						b = true;
				}
				if(et.direct==1||et.direct==3)
				{
					if(((this.x>=et.x && this.x<=et.x+30)
							||(this.x+20>=et.x&&this.x+20<=et.x+30))&& this.y+30>=et.y && this.y+30<=et.y+20)
							b = true;
				}
				}
			}
			break;
		//向左
		case 3:
			for(int i=0; i<ets.size(); i++)
			{
				EnemyTank et = ets.get(i);
				if(et!=this)
				{
				if(et.direct==0||et.direct==2)
				{
					if(this.x>=et.x && this.x<=et.x+20&&
						((this.y>=et.y&&this.y<=et.y+30)||(this.y+20>=et.y && this.y+20<=et.y+30)))
						b = true;
				}
				if(et.direct==1||et.direct==3)
				{
					if(this.x>=et.x && this.x<=et.x+30&&
							((this.y>=et.y&&this.y<=et.y+20)||(this.y+20>=et.y && this.y+20<=et.y+20)))
							b = true;
				}
				}
			}
			break;
		}
		return b;
	}
	// 重写run方法
	public void run()
	{
		// TODO Auto-generated method stub
		// 让敌人坦克自由随机走动
		while (true)
		{
			boolean star = MyPanel.isRunning;
			switch (this.direct)
			{
			case 0:
				// 向上
				for (int i = 0; i < 30; i++)
				{
					if (y > 0&&!isTouchOtherEnemy())
						y -= speed;
					try
					{
						Thread.sleep(50);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case 1:
				// 向右
				for (int i = 0; i < 30; i++)
				{
					//防止越界, 因为x,y是坦克的左上角的坐标, 得考虑坦克自身大小
					if (x < 400-30&&!isTouchOtherEnemy())
						x += speed;
					try
					{
						Thread.sleep(50);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case 2:
				// 向下
				for (int i = 0; i < 30; i++)
				{
					//防止越界, 因为x,y是坦克的左上角的坐标, 得考虑坦克自身大小
					if (y<300-30&&!isTouchOtherEnemy())
						y += speed;
					try
					{
						Thread.sleep(50);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			case 3:
				// 向左
				for (int i = 0; i < 30; i++)
				{
					if (x > 0&&!isTouchOtherEnemy())
						x -= speed;
					try
					{
						Thread.sleep(50);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				break;
			}
			if(star)
				times++;
			
			//坦克是活的, 添加子弹,3秒加一个,最多5个?
			if(ss.size()<5 && isLive && star)
			{
				//创建子弹
				switch (this.direct)
				{
				case 0:
					// 向上
					s = new Shot(x + 10, y, 0);
					break;
				case 1:
					// 向右
					s = new Shot(x + 30, y + 10, 1);
					break;
				case 2:
					// 向下
					s = new Shot(x + 10, y + 30, 2);
					break;
				case 3:
					// 向左
					s = new Shot(x, y + 10, 3);
					break;
				}
				//加入线程
				Thread t = new Thread(s);
				//启动线程
				t.start();
				ss.add(s);
			}
			
			
			// 产生一个随机方向
			if(star)
				this.direct = (int) (Math.random() * 4);
			// 如果敌人坦克已经死亡
			if (!isLive)
				break;
		}
	}
}

// 我的坦克
class Hero extends Tank
{

	Vector<Shot> ss = new Vector<Shot>();
	Shot s = null;
	
	public Hero(int x, int y)
	{
		super(x, y);
	}

	// 开火
	public void shotEnemy()
	{
		switch (this.direct)
		{
		case 0:
			// 向上
			s = new Shot(x + 10, y, 0);
			break;
		case 1:
			// 向右
			s = new Shot(x + 30, y + 10, 1);
			break;
		case 2:
			// 向下
			s = new Shot(x + 10, y + 30, 2);
			break;
		case 3:
			// 向左
			s = new Shot(x, y + 10, 3);
			break;
		}
		//设置自己子弹的速度
		//s.setSpeed(4);
		ss.add(s);

		Thread t = new Thread(s);
		t.start();
	}

	// 坦克向上移动
	public void moveUp()
	{
		if(y>0)
			this.y -= speed;
	}

	// 坦克向右移动
	public void moveRight()
	{
		if(x<400-30)
			this.x += speed;
	}

	// 坦克向下移动
	public void moveDown()
	{
		if(y<300-30)
		this.y += speed;
	}

	// 坦克向左移动
	public void moveLeft()
	{
		if(x>0)
		this.x -= speed;
	}
}
