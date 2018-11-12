package drawing;



import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import logic.Ship;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class LevelUpAnimation extends Canvas {
	private Thread levelUpThread;
	private boolean isLevelUpThreadRunning;
	private int countX = 0;
	private int countY = 2;
	public LevelUpAnimation() {
		super(SceneManager.DEFAULT_WIDTH, SceneManager.DEFAULT_HEIGHT);
		this.setVisible(true);
	}
	
	public void startLevelUp(Ship ship) {
		isLevelUpThreadRunning = true;
		levelUpThread = new Thread( new Runnable() {
			@Override
			public void run() {	
				while (isLevelUpThreadRunning) {
					Platform.runLater(()->{
						updateLevelUp(ship);
					});
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		},"levelUpThread");
		levelUpThread.start();
	}
	public void stopLevelUp() {
		isLevelUpThreadRunning = false;
		clearRect();
	}
	private void updateLevelUp(Ship ship) {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, this.getWidth(), this.getWidth());
		
		WritableImage croppedImage = new WritableImage(RenderableHolder.levelUpImg.getPixelReader(),
				countX*140, countY*140, 140, 140);
		gc.drawImage(croppedImage, ship.getX()-13-35, ship.getY()-10-35);
		
		//System.out.println(countX + " " + countY);
		countX++;
		if (countY == 5) {
			countX = countX % 4;
			if (countX == 3) {
				countY += 1;
			}
		}
		if (countX >= 7) {
			countX = countX % 7;
			countY += 1;
		}
		
		if (countY >= 6) stopLevelUp();
		if (countY >= 6) {
			countY = 2;
		}
	}
	private void clearRect() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, this.getWidth(), this.getWidth());
	}
	
	
}

