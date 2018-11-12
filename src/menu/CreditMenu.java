package menu;

import javax.swing.text.html.HTMLDocument.HTMLReader.BlockAction;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import sharedObject.RenderableHolder;
import window.SceneManager;

public class CreditMenu {
	private MenuBox menu;
	private Rectangle textbox;

	public CreditMenu() {
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
		menu = new MenuBox("credit", itemBack); // set iteamQuit เสดก็ใส่ตรวนี้
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

		hbox.getChildren().add(menu);
		root.getChildren().add(hbox);

		textbox = new Rectangle(750, 700);//
		textbox.setOpacity(0.5);

		Text name1 = new Text("\nSaranpat\n    Prasertthum");
		name1.setFill(Color.RED);
		name1.setEffect(new DropShadow(30, 30, 30, Color.BLACK));
		name1.setFont(RenderableHolder.font);

		Text name2 = new Text("\n\n          Nuttapon\nVittayaprechapon");
		name2.setFill(Color.RED);
		name2.setEffect(new DropShadow(30, 30, 30, Color.BLACK));
		name2.setFont(RenderableHolder.font);

		textfield.getChildren().addAll(name1, name2);
		stackpane.getChildren().add(textfield);
		vbox.getChildren().add(stackpane);
		hbox.getChildren().add(vbox);

		SceneManager.gotoSceneOf(scene);
	}
}
