package application;
	
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	
	public ArrayList<Dot> dList = new ArrayList<Dot>();
	public Pane pane;
	public Slider dotCount;
	public Button enter;
	private Timeline animation;
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
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void populate() {
		dList.clear();
		pane.getChildren().clear();
		int num = (int) dotCount.getValue();
		//int num =2;
		for (int i=0;i<num;i++) {
			
			int x = (int) (Math.random()*pane.getWidth());
			int y = (int) (Math.random()*pane.getHeight());
			Dot d = new Dot((Math.random()*30)+3,x,y);
			
			
			dList.add(d);
			pane.getChildren().add(d.getCircle());
		}
		//System.out.println("Size of list: "+dList.size());
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
