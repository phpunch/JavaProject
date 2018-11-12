package menu;

import drawing.GameUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import logic.GameLogic;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class HelpMenu {
	private MenuBox menu;
	private Rectangle textbox;
	private Text text;

	public HelpMenu() {
		Pane root = new Pane();
		Scene scene = new Scene(root);
		root.setPrefSize(SceneManager.DEFAULT_WIDTH, SceneManager.DEFAULT_HEIGHT);
		// ADD SOMETHING

		Image img = RenderableHolder.menuBG;
		ImageView backgroundImageView = new ImageView(img);
		backgroundImageView.setFitWidth(SceneManager.DEFAULT_WIDTH + 12);
		backgroundImageView.setFitHeight(SceneManager.DEFAULT_HEIGHT + 12);
		root.getChildren().add(backgroundImageView);
		MenuItem itemBack = new MenuItem("BACK");
		MenuItem itemHowtoplay = new MenuItem("HOW TO PLAY");
		MenuItem itemStory = new MenuItem("STORY");
		menu = new MenuBox("help", itemHowtoplay, itemStory, itemBack); // set iteamQuit เสดก็ใส่ตรวนี้
		itemBack.setOnMouseClicked(event -> {
			SceneManager.gotoMainMenu();
		});
		// show box
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(50, 50, 50, 50));
		StackPane stackpane = new StackPane();
		stackpane.setAlignment(Pos.TOP_CENTER);
		VBox textfield = new VBox();
		textfield.setAlignment(Pos.TOP_LEFT);
		textfield.setPadding(new Insets(20));

		itemHowtoplay.setOnMouseClicked(event -> {
			if (!stackpane.getChildren().contains(textbox)) {
				textbox = new Rectangle(750, 700);//
				textbox.setOpacity(0.5);
			} else {
				textfield.getChildren().clear();
			}
			stackpane.getChildren().clear();
			text = new Text("INSTRUCTION");
			text.setFill(Color.VIOLET);
			// text.setTextAlignment(TextAlignment.CENTER);
			text.setFont(RenderableHolder.helpfont);
			// text.setFont(Font.font(30));
			// text
			Text detail = new Text(
					"To Win the game you have to destory the Boss and save the world\nPress W to move the ship Forward\nPress S to move the ship Backward\nPress D to move the ship Right\n"
							+ "Press A to move the ship Left\nPress Space to shoot\nPress F to active the shield");
			detail.setFill(Color.LIGHTGRAY);
			detail.setTextAlignment(TextAlignment.LEFT);
			detail.setFont(RenderableHolder.helpfont);
			textfield.getChildren().addAll(text, detail);
			stackpane.getChildren().addAll(textbox, textfield);
			vbox.getChildren().add(stackpane);
			hbox.getChildren().add(vbox);

		});
		itemStory.setOnMouseClicked(event -> {
			if (!stackpane.getChildren().contains(textbox)) {
				textbox = new Rectangle(750, 700);//
				textbox.setOpacity(0.5);
			} else {
				textfield.getChildren().clear();
			}
			stackpane.getChildren().clear();
			text = new Text("INSTRUCTION");
			text.setFill(Color.VIOLET);
			// text.setTextAlignment(TextAlignment.CENTER);
			text.setFont(RenderableHolder.helpfont);
			// text.setFont(Font.font(30));
			// text
			Text detail = new Text(
					"In Year 2048 Earth has become unbarable place to live from pullotion\nso human decide to inhabit another planet."
							+ "Human will to expand the\nhuman race has become incresingly concerning by the other\nextraterrestrial being because how destructive human nature can be\n"
							+ "Alien sent it minion to destroy every ship that leave the planet earth\nSo your mission in to kill any ship that prevent youfrom inhabit\ntheir planet");

			detail.setFill(Color.LIGHTGRAY);
			detail.setTextAlignment(TextAlignment.LEFT);
			detail.setFont(RenderableHolder.helpfont);
			textfield.getChildren().addAll(text, detail);
			stackpane.getChildren().addAll(textbox, textfield);
			vbox.getChildren().add(stackpane);
			hbox.getChildren().add(vbox);

		});

		hbox.getChildren().add(menu);
		root.getChildren().add(hbox);
		SceneManager.gotoSceneOf(scene);
	}
}
