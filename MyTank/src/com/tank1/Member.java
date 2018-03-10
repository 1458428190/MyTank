package com.tank1;

//�ӵ�
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

//̹����
class Tank {
	// ��ʾ̹�˵ĺ�����
	int x = 0;
	// ̹��������
	int y = 0;
	// ����
	int direct = 0;
	// ����
	int type = 0;
	// ̹�˵��ٶ�
	int speed = 1;
	//̹�˵���ɫ
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

//���˵�̹��
class EnemyTank extends Tank
{
	public EnemyTank(int x, int y) {
		super(x, y);
	}
}

//�ҵ�̹��
class Hero extends Tank {
	
	Shot shotEnemy=null;
	public Hero(int x, int y) {
		super(x, y);
	}
	// ̹�������ƶ�
		public void moveUp() {
			this.y -= speed;
		}

		// ̹�������ƶ�
		public void moveRight() {
			this.x += speed;
		}

		// ̹�������ƶ�
		public void moveDown() {
			this.y += speed;
		}

		// ̹�������ƶ�
		public void moveLeft() {
			this.x -= speed;
		}
}
