package drawing;


import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class GameResult extends Canvas {
	private Thread gameResult;
	private boolean isResultRunning;
	private int count = 0;
	
	public GameResult() {
		super(SceneManager.DEFAULT_WIDTH, SceneManager.DEFAULT_HEIGHT);
		this.setVisible(true);
	}
	
	public void startResult() {
		isResultRunning = true;
		gameResult = new Thread( new Runnable() {
			@Override
			public void run() {	
				while (isResultRunning) {
					Platform.runLater(()->{
						updateResult();
					});
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		},"gameResult");
		gameResult.start();
	}
	public void stopResult() {
		isResultRunning = false;
	}
	public void updateResult() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.clearRect(0, 0, this.getWidth(), this.getWidth());
		drawY(gc,-300);
		drawO(gc,-200);
		drawU(gc,-120);
		drawW(gc,0);
		drawI(gc,120);
		drawN(gc,200);
		System.out.println(count);
		count++;
		count = count%4;
	}

	private void drawI(GraphicsContext gc,double x) {
		WritableImage croppedImage = new WritableImage(RenderableHolder.f.getPixelReader(),
				count*76, 1284, 76, 85);
		gc.drawImage(croppedImage, x + SceneManager.DEFAULT_WIDTH/2, SceneManager.DEFAULT_HEIGHT/2+30-100);
	}
	private void drawN(GraphicsContext gc,double x) {
		WritableImage croppedImage = new WritableImage(RenderableHolder.k.getPixelReader(),
				count*80, 1206, 80, 80);
		gc.drawImage(croppedImage, x + SceneManager.DEFAULT_WIDTH/2, SceneManager.DEFAULT_HEIGHT/2+30-100);
	}
	private void drawO(GraphicsContext gc,double x) {
		WritableImage croppedImage = new WritableImage(RenderableHolder.k.getPixelReader(),
				count*83, 1534, 83, 81);
		gc.drawImage(croppedImage, x + SceneManager.DEFAULT_WIDTH/2, SceneManager.DEFAULT_HEIGHT/2+30-100);
	}
	private void drawU(GraphicsContext gc,double x) {
		WritableImage croppedImage = new WritableImage(RenderableHolder.u.getPixelReader(),
				count*88, 17, 88, 100);
		gc.drawImage(croppedImage, x + SceneManager.DEFAULT_WIDTH/2, SceneManager.DEFAULT_HEIGHT/2+10-100);
	}
	private void drawW(GraphicsContext gc,double x) {
		WritableImage croppedImage = new WritableImage(RenderableHolder.u.getPixelReader(),
				count*112, 946, 112, 105);
		gc.drawImage(croppedImage, x + SceneManager.DEFAULT_WIDTH/2, SceneManager.DEFAULT_HEIGHT/2-100);
	}
	private void drawY(GraphicsContext gc,double x) {
		Image croppedImage;
		if (count != 3) {
			croppedImage = new WritableImage(RenderableHolder.u.getPixelReader(),
					615+count*95, 1622, 97, 120);
		}
		else {
			croppedImage = new WritableImage(RenderableHolder.u.getPixelReader(),
					804, 1622, 90, 120);
		}
		gc.drawImage(croppedImage, x + SceneManager.DEFAULT_WIDTH/2, SceneManager.DEFAULT_HEIGHT/2-100);
	}
}
