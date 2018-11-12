package logic;

import drawing.GameUI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class EnemyBullet extends MovingEntity {

	private int diameter;
	private int height;
	public static final int DEFAULT_BULLET_DAMAGE = 1;

	public EnemyBullet(int x,int y ,int xSpeed,int ySpeed,int diameter,int height) {
		super(x,y,xSpeed,ySpeed);
		
		this.diameter=diameter;
		this.height=height;
		this.z=300; //will appear to go through the enemy
	}
	public boolean isCollide(Ship ship) {
		if(this.collideWith(ship)) {
			destroyed=true;
			ship.decreaseHealth(DEFAULT_BULLET_DAMAGE);
			RenderableHolder.damageSound.play();
			return true;
		}
		//may be add damage
		return false;
	}
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setFill(Color.CHARTREUSE);
        gc.fillRect(x+20, y, diameter, height);
	}
	
	
	public void update() {
		// delete when go off the screen to save memory
		if(y<0||y>SceneManager.DEFAULT_HEIGHT) destroyed=true;
		this.y+=ySpeed; // should go down
		this.x+=xSpeed;
	}
	@Override  public Rectangle getBounds() {
		//System.out.println(diameter);
        Rectangle enemyBulletHitbox = new Rectangle(x+20, y, diameter, height);//use shape.getBound().getBoundInParent().intersects(shape.getBound().getBoundInParent());
        //System.out.println(bulletHitbox.getBoundsInParent().toString());
        return enemyBulletHitbox;
        //ตอน update ให้ Update ตัวนี้ด้วย เปนhitbox
        
    }

}
