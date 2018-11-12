package menu;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class MainMenu extends Scene{

	public static Font font;
	private MenuBox menu;
	
	private static Pane root = new Pane();

	public MainMenu() {
		super(root);
		
		root.setMaxSize(SceneManager.DEFAULT_WIDTH, SceneManager.DEFAULT_HEIGHT);
		root.setMinSize(SceneManager.DEFAULT_WIDTH, SceneManager.DEFAULT_HEIGHT);
		
		ImageView img = new ImageView(RenderableHolder.menuBG);
		img.setFitWidth(SceneManager.DEFAULT_WIDTH+12);
		img.setFitHeight(SceneManager.DEFAULT_HEIGHT+12);

		root.getChildren().add(img);
		font = RenderableHolder.font;

		MenuItem itemQuit = new MenuItem("QUIT");
		MenuItem itemStart = new MenuItem("START GAME");
		MenuItem itemHelp = new MenuItem("HELP");
		MenuItem itemCredit = new MenuItem("CREDITS");
		itemQuit.setOnMouseClicked(event -> System.exit(0)); // ใส่ function ให้ ปุ่ม quit
		itemStart.setOnMouseClicked(event ->{
			RenderableHolder.themeSong.stop();
			GameMain.newGame();
			
		});
		itemHelp.setOnMouseClicked(event ->{
			new HelpMenu();
		});
		itemCredit.setOnMouseClicked(event ->{
			new CreditMenu();
		});
		
		menu = new MenuBox("cy3erpunk", itemStart, itemHelp, itemCredit ,
				itemQuit); // set iteamQuit เสดก็ใส่ตรวนี้
		
		root.getChildren().add(menu);

	}
}
