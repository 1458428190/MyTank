package com.tank2;

//�ӵ�
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
				//����
				this.y -= speed;
				break;
			case 1:
				//����
				this.x += speed;
				break;
			case 2:
				//����
				this.y += speed;
				break;
			case 3:
				//����
				this.x -= speed;
				break;
			}
			
			System.out.println("�ӵ��� x=" + x + " y="+y);
			//�ж��ӵ�ʲôʱ������
			if(x<0||x>400||y<0||y>300)
			{
				isLive = false;
				break;
			}
		}
	}
}

// ̹����
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
	// ̹�˵���ɫ
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

// ���˵�̹��
class EnemyTank extends Tank {
	public EnemyTank(int x, int y) {
		super(x, y);
	}
}

// �ҵ�̹��
class Hero extends Tank {

	Shot s = null;

	public Hero(int x, int y) {
		super(x, y);
	}
	
	//����
	public void shotEnemy()
	{
		switch(this.direct)
		{
		case 0:
			//����
			s = new Shot(x+10, y, 0);
			break;
		case 1:
			//����
			s = new Shot(x+30, y+10, 1);
			break;
		case 2:
			//����
			s = new Shot(x+10, y+30, 2);
			break;
		case 3:
			//����
			s = new Shot(x, y+10, 3);
			break;
		}
		Thread t = new Thread(s);
		t.start();
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
