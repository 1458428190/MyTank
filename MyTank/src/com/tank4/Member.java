package com.tank4;

import java.util.Vector;

//�ӵ�
class Shot implements Runnable
{
	int x;
	int y;
	int direct;
	int speed = 4;
	boolean isLive = true;

	//�޸��ӵ����ٶ�
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
				// ����
				this.y -= speed;
				break;
			case 1:
				// ����
				this.x += speed;
				break;
			case 2:
				// ����
				this.y += speed;
				break;
			case 3:
				// ����
				this.x -= speed;
				break;
			}

			// System.out.println("�ӵ��� x=" + x + " y="+y);
			// �ж��ӵ�ʲôʱ������
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
	// ����ը��������
	int x, y;

	// ը��������
	int life = 9;

	// ���ը��������
	boolean isLive = true;

	public Bomb(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	// ��������ֵ
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

// ̹����
class Tank
{
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
	//̹�˵�����
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

// ���˵�̹��
class EnemyTank extends Tank implements Runnable
{

	//������̹������ӵ�ʱ�õ�
	int times = 0;
	
	// ����̹�˷������ӵ�
	Vector<Shot> ss = new Vector<Shot>();
	Shot s = null;
		
	public EnemyTank(int x, int y)
	{
		super(x, y);
	}

	// ��дrun����
	public void run()
	{
		// TODO Auto-generated method stub
		// �õ���̹����������߶�
		while (true)
		{
			switch (this.direct)
			{
			case 0:
				// ����
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
				// ����
				for (int i = 0; i < 30; i++)
				{
					//��ֹԽ��, ��Ϊx,y��̹�˵����Ͻǵ�����, �ÿ���̹�������С
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
				// ����
				for (int i = 0; i < 30; i++)
				{
					//��ֹԽ��, ��Ϊx,y��̹�˵����Ͻǵ�����, �ÿ���̹�������С
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
				// ����
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
			
			//̹���ǻ��, ����ӵ�,3���һ��,���5��?
			if(ss.size()<5 && isLive)
			{
				//�����ӵ�
				switch (this.direct)
				{
				case 0:
					// ����
					s = new Shot(x + 10, y, 0);
					break;
				case 1:
					// ����
					s = new Shot(x + 30, y + 10, 1);
					break;
				case 2:
					// ����
					s = new Shot(x + 10, y + 30, 2);
					break;
				case 3:
					// ����
					s = new Shot(x, y + 10, 3);
					break;
				}
				//�����߳�
				Thread t = new Thread(s);
				//�����߳�
				t.start();
				
				ss.add(s);
			}
			
			
			// ����һ���������
			this.direct = (int) (Math.random() * 4);

			// �������̹���Ѿ�����
			if (!isLive)
				break;
		}
	}
}

// �ҵ�̹��
class Hero extends Tank
{

	Vector<Shot> ss = new Vector<Shot>();
	Shot s = null;

	public Hero(int x, int y)
	{
		super(x, y);
	}

	// ����
	public void shotEnemy()
	{
		switch (this.direct)
		{
		case 0:
			// ����
			s = new Shot(x + 10, y, 0);
			break;
		case 1:
			// ����
			s = new Shot(x + 30, y + 10, 1);
			break;
		case 2:
			// ����
			s = new Shot(x + 10, y + 30, 2);
			break;
		case 3:
			// ����
			s = new Shot(x, y + 10, 3);
			break;
		}
		//�����Լ��ӵ����ٶ�
		//s.setSpeed(4);
		ss.add(s);

		Thread t = new Thread(s);
		t.start();
	}

	// ̹�������ƶ�
	public void moveUp()
	{
		if(y>0)
			this.y -= speed;
	}

	// ̹�������ƶ�
	public void moveRight()
	{
		if(x<400-30)
			this.x += speed;
	}

	// ̹�������ƶ�
	public void moveDown()
	{
		if(y<300-30)
		this.y += speed;
	}

	// ̹�������ƶ�
	public void moveLeft()
	{
		if(x>0)
		this.x -= speed;
	}
}
