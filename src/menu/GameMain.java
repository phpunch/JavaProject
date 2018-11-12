package menu;

import drawing.GameResult;
import drawing.GameUI;
import drawing.LevelUpAnimation;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import logic.GameLogic;
import logic.GameTime;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class GameMain {

	public static GameLogic logic;
	private static GameUI gameui;
	private static GameTime time;
	private static GameResult result;
	private static LevelUpAnimation levelUp;
	public static boolean isGameStart = false;

	public static void newGame() {
		isGameStart = true;
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		result = new GameResult();
		levelUp = new LevelUpAnimation();
		logic = new GameLogic(result, levelUp);
		gameui = new GameUI(logic);
		time = new GameTime();

		root.getChildren().add(gameui);
		root.getChildren().add(result);
		root.getChildren().add(levelUp);

		gameui.requestFocus(); // without this UI didnt response
		SceneManager.gotoSceneOf(scene);
		gameui.startAnimation();
		logic.startLogic();
		
		RenderableHolder.gameSong.setPriority(1);
		RenderableHolder.gameSong.play();
		

	}

	public static void stopGameLogicAndAnimation() {
		isGameStart = false;
		gameui.stopAnimation();
		logic.stopGame();
		time.stop();
		result.stopResult();
	}

	private static void displayGameOverResult() {

		new Alert(AlertType.NONE, "Game over! Your score is " + logic.getScore() + ".", ButtonType.OK).showAndWait();
		SceneManager.gotoMainMenu();
	}

	private static void displayCongratsResult() {
		new Alert(AlertType.NONE, "Congratulations! Your score is " + logic.getScore() + ".", ButtonType.OK)
				.showAndWait();
		SceneManager.gotoMainMenu();
	}

	public static void stopGame() {
		stopGameLogicAndAnimation();
		Platform.runLater(GameMain::displayGameOverResult);
	}

	public static void stopGameWithCongrats() {
		stopGameLogicAndAnimation();
		Platform.runLater(GameMain::displayCongratsResult);
	}

}
