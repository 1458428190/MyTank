package com.tank4;

import java.util.Vector;

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
	Shot s = null;
		
	public EnemyTank(int x, int y)
	{
		super(x, y);
	}

	// 重写run方法
	public void run()
	{
		// TODO Auto-generated method stub
		// 让敌人坦克自由随机走动
		while (true)
		{
			switch (this.direct)
			{
			case 0:
				// 向上
				for (int i = 0; i < 30; i++)
				{
					if (y > 0)
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
					if (x < 400-30)
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
					if (y<300-30)
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
					if (x > 0)
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

			times++;
			
			//坦克是活的, 添加子弹,3秒加一个,最多5个?
			if(ss.size()<5 && isLive)
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
