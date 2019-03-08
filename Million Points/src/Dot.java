import java.util.ArrayList;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Dot {
	double MAXV = .1;
	ArrayList<Dot> dotList;
	double mass;
	double x;
	double y;
	double velX;
	double velY;
	Circle dot;
	int dotCount=0;
	Random rand;
	private int maxSize =50;
	private int minSize = 3;
	
	public Dot(double m, double x, double y) {
		this.mass=m;
		this.x=x;
		this.y=y;
		this.velX=0;
		this.velY=0;
		this.dot= new Circle();
		dot.setCenterX(x);
		dot.setCenterY(y);
		dot.setRadius(m);
		rand = new Random();
		
		int r =rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);
		
		Color rc = Color.rgb(r, g, b);
		r =rand.nextInt(255);
		g = rand.nextInt(255);
		b = rand.nextInt(255);
		rc = Color.rgb(r, g, b);
		this.dot.setStroke(rc);
		
		dot.setFill(rc);
		dot.setOpacity(rand.nextDouble()*.7);
		this.dotList= new ArrayList<Dot>();
	}
	
	public void setList(ArrayList<Dot> list) {
		this.dotList.addAll(list);
	}
	
	public Circle getCircle() {
		return this.dot;
	}
	
	
	
	//getting the average pull in the X direction
	public double getXPull() {
		double f=0;
		for (int i=0;i<dotList.size();i++) {
			
			if(dotList.get(i).mass==mass) {
				continue;
			}
			
			f+=getForceX(dotList.get(i));
			
		}
		
		double tempF = f;
		f=tempF/(dotList.size());
		
		tempF=f;
		f=tempF/mass;
		return f;
	}
	
	
	
	public double getForceX(Dot d) {
		return ((d.mass*mass)/(2*(d.x-x)));
	}
	
	
	
	//getting the average pull in Y direction
	public double getYPull() {
		double f=0;
		for (int i=0;i<dotList.size();i++) {
			
			if(dotList.get(i).mass==mass) {
				continue;
			}
			
			f+=getForceY(dotList.get(i));
			
		}
		
		double tempF = f;
		f=tempF/(dotList.size());
		tempF=f;
		f=tempF/mass;
		return f;
	}
	
	
	public double getForceY(Dot d) {
		return (mass*d.mass/(2*(d.y-y)));
	}
	
	
	public boolean isTouching(Dot d) {
		if(distance(d)<=dot.getRadius()) {
			jiggle();
			return true;
		}else {
			return false;
		}
	}
	
	public double distance(Dot d) {
		return Math.sqrt((Math.pow((dot.getCenterX()-d.dot.getCenterX()), 2))+Math.pow((dot.getCenterY()-d.dot.getCenterY()), 2));
	}

	
	public void pop(Dot d) {

		Pane p = (Pane) d.dot.getParent();
		d.dot.setCenterX(Math.random()*p.getWidth());
		d.dot.setCenterY(Math.random()*p.getHeight());
		d.dot.setRadius(Math.random()*maxSize+minSize);
		
		int r =rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);
		Color rc = Color.rgb(r, g, b);
		d.dot.setFill(rc);
		
		r =rand.nextInt(255);
		g = rand.nextInt(255);
		b = rand.nextInt(255);
		rc = Color.rgb(r, g, b);
		this.dot.setStroke(rc);
		d.dot.setOpacity(0);
		FadeTransition fadeIn = new FadeTransition(Duration.millis(2000),d.dot);
		fadeIn.setFromValue(0);
		fadeIn.setToValue(rand.nextDouble()*.7);
		fadeIn.play();
	}
	

	

	
	public boolean isOutside(Dot d) {
		Pane p = (Pane) d.dot.getParent();
		if(d.dot.getCenterX()<0||d.dot.getCenterX()>p.getWidth()) {
			if(d.dot.getCenterY()<0||d.dot.getCenterY()>p.getHeight()) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
		
	}
	
	public void check() {
		for(int i=0;i<dotList.size();i++) {
			if(dotList.get(i).mass==mass) {
				continue;
			}
			Dot current = dotList.get(i);
			if(isTouching(current)) {
				pop(current);
				this.mass++;
				dot.setRadius(dot.getRadius()+(current.dot.getRadius()/5));
			}
			if(isOutside(current)) {
				pop(current);
			}
			if(dot.getRadius()>50) {
				pop(this);
			}
		}
	}
	//rand.nextDouble()*
	public void jiggleStart() {
		MAXV=10/mass;
		
	}
	
	public void jiggleEnd() {
		MAXV=.1;
	}
	
	
	//not used
	public void jiggle() {
		Timeline beat = new Timeline(new KeyFrame(Duration.millis(900),e-> jiggleStart()));
		beat.setCycleCount(3);
		beat.setOnFinished(e-> jiggleEnd());
		beat.play();
		
	}
	
	
	public void update() {
		this.velX+=getXPull();
		this.velY+=getYPull();
		check();
	
		if(Math.abs(this.velX)>this.MAXV) {
			if(this.velX>0) {
				this.velX=this.MAXV;
			}
			else {
				this.velX=(this.MAXV*-1);
			}
			
		}
		if(Math.abs(this.velY)>this.MAXV) {
			if(this.velY>0) {
				this.velY=this.MAXV;
			}
			else {
				this.velY=(this.MAXV*-1);
			}
		}
		
		double newX = this.dot.getCenterX()+this.velX;
		double newY = this.dot.getCenterY()+this.velY;
		this.dot.setCenterX(newX);
		this.x=newX;
		this.dot.setCenterY(newY);
		this.y=newY;
		
	}
	


}