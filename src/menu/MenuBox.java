package menu;

import application.Main;
import menu.MenuItem;
import sharedObject.RenderableHolder;
import window.SceneManager;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MenuBox extends StackPane {
	private int recwidth = (int) (SceneManager.DEFAULT_WIDTH * 0.35);
	public FadeTransition ft; // to sync

	public MenuBox(String title, MenuItem... items) { // Menuitems... �����Ҩ��Ѻ��������� menuitem �����µ��
		Rectangle bg = new Rectangle(recwidth, SceneManager.DEFAULT_HEIGHT+10);
		bg.setOpacity(0.5); // set opacity ��� 20%

		DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
		shadow.setSpread(0.8);// �� spread �ҡ���

		bg.setEffect(shadow);

		// background blink
		/*
		 * FadeTransition ft = new FadeTransition(Duration.millis(1800),bg);
		 * 
		 * ft.setFromValue(0.5); ft.setToValue(0.3); ft.setCycleCount(-1); // infinite
		 * loop ft.setAutoReverse(true); ft.play();
		 */

		Text text = new Text(title + " ");

		text.setFont(RenderableHolder.font);
		text.setFill(Color.RED);
		ft = new FadeTransition(Duration.millis(750), text);
		ft.setFromValue(1.0);
		ft.setToValue(0);
		ft.setCycleCount(100);
		ft.setAutoReverse(true);
		ft.play();

		/*Line hSep1 = new Line(); // ����� title
		// hSep.setStartX(recwidth*0.20);
		hSep1.setEndX(recwidth); // ����Һ����ࡹ x ��ǹԹ
		hSep1.setStrokeWidth(5);
		hSep1.setStroke(Color.CRIMSON);

		Line hSep2 = new Line(); // ����� title // hSep.setStartX(recwidth*0.20);
		hSep2.setEndX(recwidth); // ����Һ����ࡹ x ��ǹԹ hSep.setStrokeWidth(3);
		hSep2.setStroke(Color.CRIMSON);
		hSep2.setStrokeWidth(5);
		//hSep2.setOpacity(1);// ********************************change*/

		Line vSep = new Line();// ����๵��
		vSep.setStartX(recwidth);
		vSep.setEndX(recwidth);
		vSep.setEndY(SceneManager.DEFAULT_WIDTH);
		vSep.setStroke(Color.DARKMAGENTA);
		vSep.setOpacity(0.4);

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.TOP_RIGHT);
		vbox.setPadding(new Insets(55, 0, 0, 0));
		vbox.getChildren().addAll(text);// ���ǡ menu item ������ŧ � vbox
		vbox.getChildren().addAll(items);
		
		setAlignment(Pos.TOP_RIGHT); // top right of stack pane
		getChildren().addAll(bg, vSep, vbox);

	}
}
