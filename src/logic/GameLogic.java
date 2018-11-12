package logic;

import java.util.ArrayList;
import java.util.List;
import menu.GameMain;
import drawing.GameResult;
import drawing.GameUI;
import drawing.LevelUpAnimation;
import input.InputUtility;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class GameLogic {
	private List<Entity> gameObjectContainer; //test only
	private Ship ship;
	private Enemy[][] enemyArray;
	private int enemycolSpace = 80; 
	private int enemyrowSpace = 80;
	private int bulletCounter;//will reduce rate of fire
	public static int spawncounter=0;//to minimize lag
	public int enemyWave;
	public int numberOfEnemy;
	public static int enemybodycount;
	public static int score;
	private boolean isLogicRunning;
	private static final int FPS = 60;
	private static final long LOOP_TIME = 1000000000 / FPS;
	private Thread gameLogic;
	private GameResult result;
	private LevelUpAnimation levelUpAnimation;
	private int shipLevel=1;
	
	private int startTime;
	private boolean isStartCountTime = false;
	private boolean isStartResultThread = false;
	
	public GameLogic(GameResult result,LevelUpAnimation levelUpAnimation) {
		this.result = result;
		this.levelUpAnimation = levelUpAnimation;
		score = 0;
		enemybodycount = 0;
		enemyWave = 1;
		//enemyShootCounter=0;
		bulletCounter=0;
		/*RenderableHolder.gameSong.play();
		RenderableHolder.gameSong.setCycleCount(-1);*/
		this.gameObjectContainer=new ArrayList<Entity>();
		ship = new Ship(500,700);
		//EnemyBullet bullet = new EnemyBullet(100, 100, 0, 15, 50, 50);
		spawnEnemy(enemyWave);
		addNewObject(ship);

	}
	public void spawnEnemy(int wavenumber) {//still have only wave number 1 must update to 1-3 level and then boss
		if(wavenumber<=3&&wavenumber>0) {//set their health to be low too
			enemyArray=new Enemy[5][10];
			numberOfEnemy=50;
			for(int i =0;i<enemyArray.length;i++) {
				for(int j = 0; j<enemyArray[i].length;j++) {
					enemyArray[i][j] = new Enemy(j*enemycolSpace, i*enemyrowSpace, 1+((wavenumber-1)*5), 0, wavenumber, 40, 40);// i must be y axis because it indicate the row
					addNewObject(enemyArray[i][j]);
					//wave[i][j].setHealth(1);//tougher enemy
					
				}
				
			}
		}
		if(wavenumber==4) {
			enemyArray=new Enemy[1][1];
			numberOfEnemy=1;
			enemyArray[0][0]=new Enemy((SceneManager.DEFAULT_WIDTH/2)-75, SceneManager.DEFAULT_HEIGHT/5, 10, 0, wavenumber, 150, 150);
			addNewObject(enemyArray[0][0]);
			//wave[0][0].setHealth(50);
			
		}
		
		
	}
	protected void addNewObject(Entity entity){
		gameObjectContainer.add(entity);
		RenderableHolder.getInstance().add(entity);
	}
	
	public void startLogic() {
		isLogicRunning = true;
		gameLogic = new Thread( new Runnable() {
			@Override
			public void run() {
				long lastLoopStartTime = System.nanoTime();
				while (isLogicRunning) {
					long elapsedTime = System.nanoTime() - lastLoopStartTime;
					if (elapsedTime >= LOOP_TIME) {
						lastLoopStartTime += LOOP_TIME;
						synchronized (RenderableHolder.getInstance().getEntities()) {
							//System.out.println("LOGIC");
							updateLogic();
							
						}
					}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		},"gameLogic");
		gameLogic.start();
		
		
	}
	
	public void stopGame() {
		this.isLogicRunning = false;
		clearGame();
		//System.out.println("LOGIC is ALIVE : " + gameLogic.isAlive());
	}
	
	public void logicUpdate(){
		//System.out.println("your score is "+score);
		ship.setShipLv((int)(score/1000)+1);
		if(ship.getShipLv()!=shipLevel) {
			shipLevel=ship.getShipLv();
			RenderableHolder.levelupSound.play();
			levelUpAnimation.startLevelUp(ship);
		}
//		System.out.println("tmp =="+tmp);
//		System.out.println("shiplv =="+ship.getShipLv());
		if(enemybodycount==numberOfEnemy) {
			enemyWave++;
			spawnEnemy(enemyWave);
			enemybodycount=0;
		}
		if(enemyWave==5) {
			if (!isStartResultThread) {
				result.startResult();
				RenderableHolder.gameSong.stop();
				RenderableHolder.winSound.play();
				gameObjectContainer.clear();
				isStartResultThread = true;
			}
			if (countDown3()){
				GameMain.stopGameWithCongrats();
			}
		}
		
		if(bulletCounter<20) bulletCounter+=1;
		//System.out.println("bullet COunter" + bulletCounter);//test bullet reload
		//enemy.update();
		ship.update();
		if(ship.isShooting) {
			shoot();
			//splitShoot();
			ship.isShooting=false;
		}
		//RenderableHolder.getInstance().update();
		for (int i = gameObjectContainer.size() - 1; i >= 0; i--) {
			if(gameObjectContainer.get(i) instanceof Enemy){
				if(ship.collideWith((Enemy) gameObjectContainer.get(i))){
					if(!ship.flashing&&!ship.destroyed) {
						((Enemy) gameObjectContainer.get(i)).hit();
						ship.decreaseHealth(1);
						//if(((Enemy) gameObjectContainer.get(i)).destroyed) enemybodycount++;
					}
					
				}
				
				((Enemy) gameObjectContainer.get(i)).update();
			}
			if (gameObjectContainer.get(i) instanceof Bullet) {
				((Bullet) gameObjectContainer.get(i)).update();
				if(((Bullet) gameObjectContainer.get(i)).isCollide(enemyArray)) {
					//System.out.println("                                    " + enemybodycount);
					gameObjectContainer.remove(i);
					//System.out.println("COLLIDED TRUE");
				}
			}
			
			else if(gameObjectContainer.get(i) instanceof EnemyBullet) {
				((EnemyBullet) gameObjectContainer.get(i)).update();
				if(((EnemyBullet) gameObjectContainer.get(i)).isCollide(ship)) {
					//isCollide already decrease the enemy health
					gameObjectContainer.remove(i);
					//System.out.println("COLLIDED TRUE");
				}
			}
			else if(((Entity) gameObjectContainer.get(i)).destroyed){
				
				gameObjectContainer.remove(i);
				
			}
		}
		//RenderableHolder.getInstance().update();
	}
	private void shoot() {// shoot will take in when we have score and ship level and then we will level up these cannon
		if(bulletCounter==0||bulletCounter%20==0) {
		try {
			
			RenderableHolder.shootsound.play();
		} catch (Exception e) {
			System.out.println("Cant load bullet sound");
		}
		
		if(ship.getShipLv()>=1) {
			Bullet bullet = new Bullet(ship.getX(),ship.getY()+3,0,15,5);
			addNewObject(bullet);
			
			}
		//will add ship level later
		if(ship.getShipLv()>=5) {
			Bullet oleftbullet = new Bullet(ship.getX()-24,ship.getY()+30,0,15,5);//outer left gun
			Bullet orightbullet = new Bullet(ship.getX()+24,ship.getY()+30,0,15,5);//outer right gun
			addNewObject(oleftbullet);
			addNewObject(orightbullet);
			
		}
		if(ship.getShipLv()>=10) {
			Bullet ileftbullet = new Bullet(ship.getX()-15,ship.getY()+27,0,15,5);//inner left gun
			Bullet irightbullet = new Bullet(ship.getX()+15,ship.getY()+27,0,15,5);//inner right gun
			addNewObject(ileftbullet);
			addNewObject(irightbullet);
			
		}
		
		bulletCounter=0;
		}
		// gotta add bullet reloaded UI
	}
	public int getScore() {
		return score;
	}
	private void clearGame() {
		RenderableHolder.gameSong.stop();
		gameObjectContainer.clear();
		RenderableHolder.getInstance().getEntities().clear();
		InputUtility.clearKey();
	}
	
	
	
	private void updateLogic() {
		logicUpdate();
		RenderableHolder.getInstance().update();
		InputUtility.updateInputState();
	}
	
	public void startCountTime() {
		startTime = GameTime.seconds;
	}
	
	private boolean countDown3() {
		if (!isStartCountTime) {
			startCountTime();
			isStartCountTime = true;
			return false;
		}
		if ((GameTime.seconds - startTime > 3) && isStartCountTime) {
			isStartCountTime = false;
			return true;
		}
		return false;
	}
}
