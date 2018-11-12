package logic;

import java.util.Random;

import drawing.GameUI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import menu.GameMain;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class Enemy extends MovingEntity {

	private final int DEFAULT_ALIEN_HEALTH = 1; // have to hit it 1 time
	private int health;
	private int enemytype, width, height;
	// public boolean deleted;
	private int shootcounter = 1;
	private int SHOOT;// will shoot when shootcounter%SHOOT = 0;
	private int move = 40;
	public int spawncounter=0;

	public Image alien1;
	public Image alien2;
	public Image alien3;
	public Image alienBoss;

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean decreaseHealth(int damage) {// for more level gun
		if (health - damage <= 0) {
			health = 0;
			destroyed = true;
			GameLogic.score += 100;
			GameLogic.enemybodycount++;
			return true;

		} else
			health -= damage;
		return false;
	}

	public void loadResource() {

		alien1 = RenderableHolder.alien1;
		alien2 = RenderableHolder.alien2;
		alien3 = RenderableHolder.alien3;
		alienBoss = RenderableHolder.alienBoss;

	}

	public Enemy(int x, int y, int xSpeed, int ySpeed, int enemytype, int width, int height) {
		super(x, y, xSpeed, ySpeed);
		this.enemytype = enemytype;
		this.width = width;
		this.height = height;
		this.z = 400;
		loadResource();
		if (this.enemytype < 4) {
			if (this.enemytype == 1) {
				health = DEFAULT_ALIEN_HEALTH;
			} else {
				//System.out.println(health);
				health = DEFAULT_ALIEN_HEALTH * this.enemytype * 2;// boss will add in later in FULL GAME RELEASE
			}
			Random rand = new Random();
			if (GameLogic.spawncounter % 2 == 0) {
				SHOOT = rand.nextInt((500 - 400) + 1) + 400;
			} else {
				SHOOT = rand.nextInt((300 - 200) + 1) + 150;
			}
			GameLogic.spawncounter++;

		}
		if (this.enemytype == 4) {
			health = 150;
			Random rand = new Random();
			SHOOT = rand.nextInt((60 - 40) + 1) + 40;
		}
		// System.out.println("BOSS HEALTH ="+health);

	}

	public void hit() { // when ship hit the enemy
		decreaseHealth(2);// more damage than shoot
		RenderableHolder.damageSound.play();
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		if (this.enemytype == 1) {
			gc.drawImage(alien1, x, y);
			// test to find the middle of the alien to find the right point to let them
			// shoot later
			/*
			 * gc.setStroke(Color.WHITE); gc.strokeLine(getX()+20, getY(), getX()+20,
			 * getY()+50);// x=x+20 and y=y+20 is the most cloestes we could find
			 */
		}
		if (this.enemytype == 2) {
			gc.drawImage(alien2, x, y);
		}
		if (this.enemytype == 3) {
			gc.drawImage(alien3, x, y);
		}
		if (enemytype == 4) {
			gc.setStroke(Color.WHITE);
			gc.strokeRect(x, y - 50, 150, 10);

			gc.setFill(Color.GREENYELLOW);
			gc.fillRect(x, y - 50, health, 10);// boss health bar
			gc.drawImage(alienBoss, x, y);
		}
		// System.out.println("Boss is at Y== "+y);

	}

	public void update() {

		if (shootcounter % SHOOT == 0 && enemytype != 4) {
			GameMain.logic.addNewObject(new EnemyBullet(x, y, 0, 5, 5, 10));
			RenderableHolder.enemyShoot.play();

		}
		if (shootcounter % SHOOT == 0 && enemytype == 4) {
			GameMain.logic.addNewObject(new EnemyBullet(x + 51, y + 120, 0, 15, 10, 50));
			RenderableHolder.enemyShoot.play();

		}

		if ((x < 0 || x > SceneManager.DEFAULT_WIDTH - 40) && enemytype != 4) {

			xSpeed = -xSpeed; // to make it turn back
			y = y + 40; // ใส่ +yspeed ตรงนี้

		}
		x += xSpeed;// move normaly
		// **************************yspeed add in later ใส่ ที่หลังให้เคลื่อน ตอนหมดจอ
		if (enemytype == 4 && (x < 0 || x > SceneManager.DEFAULT_WIDTH - 150)) {
			xSpeed = -xSpeed; // to make it turn back
			if (y + 40 < 320) {
				y = y + move;
			} else {
				y = y - move;
			}
		}
		/*
		 * if(enemytype==4&&y>=320) { move=-move; }
		 */
		if (y > SceneManager.DEFAULT_HEIGHT || y < 0) {
			destroyed = true;// fixed memory leak
			GameLogic.enemybodycount++;
		}
		shootcounter++;

	}

	@Override
	public Rectangle getBounds() {
		Rectangle enemyHitbox = new Rectangle(x, y, width, height - 3); // reduce hit box to eleminate square feeling
																		// when bullet hitting
		
		return enemyHitbox;
	}

}
