package logic;



import drawing.GameUI;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class Bullet extends MovingEntity {
	int diameter; // how large the bullet will be
	private int damage; // damage of the bullet
	public static final int DEFAULT_BULLET_DAMAGE = 1;
	//public boolean deleted=false;//may have getter for this later'
	int ySpeed; //bullet will shoot down or up only
	public int getDiameter() {
		return diameter;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}

	public boolean isCollide(Enemy[][] enemywave) {//only ship can shoot with this iscollied function 
		for(int i =0;i<enemywave.length;i++) {
			for(int j = 0; j<enemywave[i].length;j++) {
				if(this.collideWith(enemywave[i][j])&&!enemywave[i][j].isDestroyed()) {
					//decrease health
					
						
					RenderableHolder.enemydamageSound.play();
					enemywave[i][j].decreaseHealth(damage);
					
					destroyed=true;// this destroy the bullet
					return isDestroyed(); // this return this bullet is destroy
				}
			}
			
		}
		return false;
	}
	
	public Bullet(int x,int y,int xSpeed,int ySpeed,int diameter) {
		super(x, y, 0, 0);
		this.ySpeed=ySpeed; // Not sure why put ySpeed to the super isnt working
		this.diameter=diameter;
		this.damage = DEFAULT_BULLET_DAMAGE;
		this.z=500;
	}
			

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.setFill(Color.DEEPPINK);
        gc.fillRect(x+22, y, diameter, 10);
		
	}
	public void update() {
		// delete when go off the screen to save memory
		if(y<0||y>SceneManager.DEFAULT_HEIGHT) destroyed=true;
		this.y-=ySpeed; // should go up
	}
	@Override  public Rectangle getBounds() {
		//System.out.println(diameter);
		//Rectangle df=new re
        Rectangle bulletHitbox = new Rectangle(x+22, y, diameter,10);//use shape.getBound().getBoundInParent().intersects(shape.getBound().getBoundInParent());
        //System.out.println(bulletHitbox.getBoundsInParent().toString());
        return bulletHitbox;
        //ตอน update ให้ Update ตัวนี้ด้วย เปนhitbox
        
    }

}
