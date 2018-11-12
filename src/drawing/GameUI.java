package drawing;

import java.util.List;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;


import input.InputUtility;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.GameLogic;
import logic.GameTime;
import menu.GameMain;
import sharedObject.IRenderable;
import sharedObject.RenderableHolder;
import window.SceneManager;


public class GameUI extends Canvas{

	private static final int FPS = 60;
	private static final long LOOP_TIME = 1000000000 / FPS;
	private Thread gameAnimation;
	private GameLogic logic;
	private boolean isAnimationRunning;
	 
	public GameUI(GameLogic logic) {
		super(SceneManager.DEFAULT_WIDTH, SceneManager.DEFAULT_HEIGHT);
		this.logic = logic;
		this.setVisible(true);
		addListerner();//add event handler to canvas
		//startAnimation();
	}
	public void addListerner() {
		this.setOnKeyPressed((KeyEvent event) -> {
			InputUtility.setKeyPressed(event.getCode(), true);
		});
		// comment this when want to do only triggered || shoot one bullet at a time
		this.setOnKeyReleased((KeyEvent event) -> {
			InputUtility.setKeyPressed(event.getCode(), false);
		});

		this.setOnMousePressed((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY)
				InputUtility.mouseLeftDown();
		});

		this.setOnMouseReleased((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY)
				InputUtility.mouseLeftRelease();
		});

		this.setOnMouseEntered((MouseEvent event) -> {
			InputUtility.mouseOnScreen = true;
		});

		this.setOnMouseExited((MouseEvent event) -> {
			InputUtility.mouseOnScreen = false;
		});

		this.setOnMouseMoved((MouseEvent event) -> {
			if (InputUtility.mouseOnScreen) {
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
			}
		});

		this.setOnMouseDragged((MouseEvent event) -> {
			if (InputUtility.mouseOnScreen) {
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
			}
		});
	
	}
	public void paintComponent() {
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.drawImage(RenderableHolder.backGround,0,0, this.getWidth(), this.getHeight());
		
		final List<IRenderable> entites =  RenderableHolder.getInstance().getEntities();
		synchronized(entites) {
			for (IRenderable entity : entites) {//draw the entity in the list //SPAWNER
				//System.out.println(entity.getZ());
				if (entity.isVisible() && !entity.isDestroyed()) {
					entity.draw(gc);
				}
				
			}
		}
		
		FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
		double sX =  fontLoader.computeStringWidth("Time", RenderableHolder.font);
		double sY = fontLoader.getFontMetrics(RenderableHolder.font).getLineHeight();
		
		gc.setFill(Color.WHITE);
		gc.setFont(RenderableHolder.helpfont);
		gc.fillText("Score : " + logic.getScore(), SceneManager.DEFAULT_WIDTH - sX , 50 + sY);
		gc.fillText("Time : " + GameTime.seconds, SceneManager.DEFAULT_WIDTH - sX , sY);
		gc.setFill(Color.PALEGREEN);
		gc.setLineWidth(2);
		if (logic.enemyWave != 5) {
			gc.fillText("Wave : " + logic.enemyWave, 40 , sY+60);
			gc.fillText("Ship Level :"+(int) (logic.getScore()/1000+1), 130, sY+60);
		}
		

	}
	public void startAnimation() {
		isAnimationRunning = true;
		gameAnimation = new Thread( new Runnable() {

			@Override
			public void run() {
				long lastLoopStartTime = System.nanoTime();
				while (isAnimationRunning) {
					
					long elapsedTime = System.nanoTime() - lastLoopStartTime;
					if (elapsedTime >= LOOP_TIME) {
						lastLoopStartTime += LOOP_TIME;
						synchronized (RenderableHolder.getInstance().getEntities()) {
//							try {
//								RenderableHolder.getInstance().wait();
//							} catch (InterruptedException e) {
//								
//								e.printStackTrace();
//							}
							Platform.runLater(()->{
								paintComponent();
							});
						}
					}
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					//System.out.println("UI");
				}
				//System.out.println("YYYYYYYYYYYYYY");
			}
			
		},"gameAnimation");
		
		gameAnimation.start();
		
		
		
	}
	public void stopAnimation() {
		this.isAnimationRunning = false;
		//System.out.println("ANIMATION is ALIVE : " + gameAnimation.isAlive());
	}
	
}
