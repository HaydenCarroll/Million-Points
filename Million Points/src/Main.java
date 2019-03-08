
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;

import com.sun.glass.ui.Screen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application{
	private ArrayList<Dot> dList = new ArrayList<Dot>();
	public Pane pane ;
	private Timeline animation;
	private List<Screen> screenList = Screen.getScreens();
	private int screenNo=0;
	private boolean isRunning =false;
	private int num =100;
	private int maxSize =30;
	private int minSize = 3;
	private Random rand = new Random();
	public static void main(String[] args) throws LineUnavailableException {
		
		
		
		// TODO Auto-generated method stub
		Application.launch(args);

	}

	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Display.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			scene.setFill(Color.TRANSPARENT);
			//primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setWidth(Screen.getMainScreen().getWidth());
			primaryStage.setHeight(Screen.getMainScreen().getHeight());
			primaryStage.show();
			primaryStage.setAlwaysOnTop(true);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void nextScreen() {
		if(screenList.size()==screenNo) {
			screenNo=0;
		}
		Stage prim = (Stage) pane.getScene().getWindow();
		Screen mainScreen = screenList.get(screenNo);
		prim.setWidth(mainScreen.getWidth());
		prim.setHeight(mainScreen.getHeight());
		prim.setX(mainScreen.getX());
		prim.setY(mainScreen.getY());
		screenNo++;
		
	}
	
	private void populate() {
		dList.clear();
		pane.getChildren().clear();
		
		for (int i=0;i<num;i++) {
			
			int x = (int) (Math.random()*pane.getWidth());
			int y = (int) (Math.random()*pane.getHeight());
			Dot d = new Dot((Math.random()*maxSize)+minSize,x,y);
			
			int r =rand.nextInt(255);
			int g = rand.nextInt(255);
			int b = rand.nextInt(255);
			
			Color rc = Color.rgb(r, g, b);
			d.dot.setFill(rc);
			r =rand.nextInt(255);
			g = rand.nextInt(255);
			b = rand.nextInt(255);
			rc = Color.rgb(r, g, b);
			d.dot.setStroke(rc);
			dList.add(d);
			pane.getChildren().add(d.getCircle());
		}
		for (int i=0;i<dList.size();i++) {
			dList.get(i).setList(dList);
		}
	}
	
	public void play(){
		if(isRunning==true) {
			return;
		}
		populate();
		animation = new Timeline(new KeyFrame(Duration.millis(20), e -> update()));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.setRate(1);
		animation.play();
		isRunning=true;
	}
	
	//not used
	public void pause() {
		animation.pause();
	}
	
	private void update() {
		for (int i=0;i<dList.size();i++) {
			dList.get(i).update();
		}
	}
	
	//not used
	public void jiggleStart() {
		for (int i=0;i<dList.size();i++) {
			dList.get(i).jiggleStart();
		}
	}
	
	//not used
	public void jiggleEnd() {
		for (int i=0;i<dList.size();i++) {
			dList.get(i).jiggleEnd();
		}
	}
	
	//not used
	public void jiggle() {
		Timeline beat = new Timeline(new KeyFrame(Duration.millis(10),e-> jiggleStart()));
		beat.setCycleCount(5);
		beat.setOnFinished(e-> jiggleEnd());
		beat.play();
		
	}
	
	
	public void quit() {
		System.exit(0);
	}
	
	
	
	


}
