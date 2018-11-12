package window;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import menu.GameMain;
import menu.MainMenu;
import sharedObject.RenderableHolder;

public final class SceneManager {

	private static Stage primaryStage;
	
	private static Scene mainMenuScene = new MainMenu();
	public static final int DEFAULT_WIDTH = 1280;// 16:9 Ratio
	public static final int DEFAULT_HEIGHT = 800;

	public static void initialize(Stage stage) {
		primaryStage = stage;
		primaryStage.show();
	}

	public static void gotoMainMenu() {
		primaryStage.setScene(mainMenuScene);
		if(!RenderableHolder.themeSong.isPlaying()) RenderableHolder.themeSong.play();
	}

	public static void gotoSceneOf(Scene scene) {
		//Scene scene = new Scene(scene);
		primaryStage.setScene(scene);
		//canvas.requestFocus();
	}
}
