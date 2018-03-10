package com.tank1;

//子弹
class Shot 
{
	int x;
	int y;
	int direct;
	public Shot(int x, int y, int direct)
	{
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
}

//坦克类
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
	//坦克的颜色
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

//敌人的坦克
class EnemyTank extends Tank
{
	public EnemyTank(int x, int y) {
		super(x, y);
	}
}

//我的坦克
class Hero extends Tank {
	
	Shot shotEnemy=null;
	public Hero(int x, int y) {
		super(x, y);
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
