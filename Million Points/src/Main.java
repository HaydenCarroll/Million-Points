import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class Main extends Application{
	public ArrayList<Dot> dList = new ArrayList<Dot>();
	public Pane pane;
	public Slider dotCount;
	public Button enter;
	private Timeline animation;

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		Application.launch(args);

	}

	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Display.fxml"));
			Scene scene = new Scene(root,700,560);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void populate() {
		pane.getChildren().clear();
		int num = (int) dotCount.getValue();
		for (int i=0;i<num;i++) {
			
			int x = (int) (Math.random()*pane.getWidth());
			int y = (int) (Math.random()*pane.getHeight());
			Dot d = new Dot((Math.random()*30)+3,x,y);
			
			
			dList.add(d);
			pane.getChildren().add(d.getCircle());
		}
		for (int i=0;i<dList.size();i++) {
			dList.get(i).setList(dList);
		}
	}
	
	public void play() {
		animation = new Timeline(new KeyFrame(Duration.millis(20), e -> update()));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.setRate(1);
		animation.play();
	}
	
	public void pause() {
		animation.pause();
	}
	
	public void update() {
		for (int i=0;i<dList.size();i++) {
			dList.get(i).update();
		}
	}

}
