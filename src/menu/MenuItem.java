package menu;

import application.Main;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import window.SceneManager;

public class MenuItem extends StackPane {// ������ٵ�ҧ�
	private int recwidth = (int) (SceneManager.DEFAULT_WIDTH * 0.35);

	public MenuItem(String name) {
		Rectangle bg = new Rectangle(recwidth, 35);// background �ͧ menu ������ѹ

		LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
				new Stop[] { new Stop(0, Color.WHITE), new Stop(0.2, Color.VIOLET) });

		bg.setFill(gradient);
		bg.setVisible(false);
		bg.setEffect(new DropShadow(5, 0, 5, Color.DARKMAGENTA));// was Black***************** //radius,drop at
																	// x,drop at y,colour

		Text text = new Text(name + "      ");
		text.setFill(Color.LAVENDER);
		text.setFont(Font.font(30));
	    
		setAlignment(Pos.CENTER_RIGHT);
		getChildren().addAll(bg, text);

		setOnMouseEntered(event -> { // lambda function
			bg.setVisible(true);
			text.setFill(Color.WHITE);
			
		});

		setOnMouseExited(event -> {
			bg.setVisible(false);
			text.setFill(Color.LAVENDER);
		});

		setOnMousePressed(event -> {
			bg.setFill(Color.WHITE);
			text.setFill(Color.VIOLET);
		});

		setOnMouseReleased(event -> {
			bg.setFill(gradient);
			text.setFill(Color.WHITE);
		});

	}
}
