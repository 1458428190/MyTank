package com.tank2;

//子弹
class Shot implements Runnable{
	int x;
	int y;
	int direct;
	int speed = 5;
	boolean isLive = true;
	public Shot(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			try{
				Thread.sleep(50);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			switch(this.direct)
			{
			case 0:
				//向上
				this.y -= speed;
				break;
			case 1:
				//向右
				this.x += speed;
				break;
			case 2:
				//向下
				this.y += speed;
				break;
			case 3:
				//向左
				this.x -= speed;
				break;
			}
			
			System.out.println("子弹在 x=" + x + " y="+y);
			//判断子弹什么时候死亡
			if(x<0||x>400||y<0||y>300)
			{
				isLive = false;
				break;
			}
		}
	}
}

// 坦克类
class Tank {
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

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

// 敌人的坦克
class EnemyTank extends Tank {
	public EnemyTank(int x, int y) {
		super(x, y);
	}
}

// 我的坦克
class Hero extends Tank {

	Shot s = null;

	public Hero(int x, int y) {
		super(x, y);
	}
	
	//开火
	public void shotEnemy()
	{
		switch(this.direct)
		{
		case 0:
			//向上
			s = new Shot(x+10, y, 0);
			break;
		case 1:
			//向右
			s = new Shot(x+30, y+10, 1);
			break;
		case 2:
			//向下
			s = new Shot(x+10, y+30, 2);
			break;
		case 3:
			//向左
			s = new Shot(x, y+10, 3);
			break;
		}
		Thread t = new Thread(s);
		t.start();
	}

	// 坦克向上移动
	public void moveUp() {
		this.y -= speed;
	}

	// 坦克向右移动
	public void moveRight() {
		this.x += speed;
	}

	// 坦克向下移动
	public void moveDown() {
		this.y += speed;
	}

	// 坦克向左移动
	public void moveLeft() {
		this.x -= speed;
	}
}
