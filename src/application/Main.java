package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import menu.GameMain;
import window.SceneManager;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			SceneManager.initialize(primaryStage);
			SceneManager.gotoMainMenu();
			primaryStage.setTitle("Cy3erPunk");
			primaryStage.centerOnScreen();
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest(event -> {
				stop();
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void stop() {
		if (GameMain.isGameStart) {
			GameMain.stopGameLogicAndAnimation();
		}
		
	}
	public static void main(String[] args) {
		launch(args);
	}
}
