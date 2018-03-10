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

//������������
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
		//���ǻ���
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

//��¼��,ͬʱҲ���Ա�����ҵ�����
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
	// ��¼ÿ���ж��ٵ���
	private static int enNum = 20;
	// �������ж�����
	private static int myLife = 3;
	//�ܹ�������ٸ�̹��
	private static int allEnNum = 0;
	
	private static FileWriter wf;
	private static BufferedWriter bw;
	private static FileReader fr;
	private static BufferedReader br;
	
	//�ѵ��˵�̹�˴�����
	private static Vector<EnemyTank> ets = new Vector<EnemyTank>();
	//��ȡ���˵�̹�˵�����ͷ���
	private static Vector<Node> nodes = new Vector<Node>();
	
	
	public static Vector<EnemyTank> getEts() {
		return ets;
	}

	public static void setEts(Vector<EnemyTank> ets) {
		Recoder.ets = ets;
	}

	//��ɶ�ȡ����
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
	
	//������ŵĵ���̹�˵�����
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
	
	//���ļ��ж�ȡ, ��¼
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
	
	//����һ��ٵ���̹���������浽�ļ���, , ������Ŀ�����
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
	
	//���ٵ�����
	public static void reduceEnNum()
	{
		enNum--;
	}
	
	//�����Լ�������
	public static void reduceMyLife()
	{
		myLife--;
	}
	
	//��������ĵ���
	public static void addEnNum()
	{
		allEnNum++;
	}
}


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
		//����
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
		//����
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
		//����
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
		//����
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
	// ��дrun����
	public void run()
	{
		// TODO Auto-generated method stub
		// �õ���̹����������߶�
		while (true)
		{
			boolean star = MyPanel.isRunning;
			switch (this.direct)
			{
			case 0:
				// ����
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
				// ����
				for (int i = 0; i < 30; i++)
				{
					//��ֹԽ��, ��Ϊx,y��̹�˵����Ͻǵ�����, �ÿ���̹�������С
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
				// ����
				for (int i = 0; i < 30; i++)
				{
					//��ֹԽ��, ��Ϊx,y��̹�˵����Ͻǵ�����, �ÿ���̹�������С
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
				// ����
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
			
			//̹���ǻ��, ����ӵ�,3���һ��,���5��?
			if(ss.size()<5 && isLive && star)
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
			if(star)
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
