import java.util.ArrayList;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Dot {
	Timeline grow;
	double MAXV = .1;
	ArrayList<Dot> dotList;
	double mass;
	double x;
	double y;
	double velX;
	double velY;
	Circle dot;
	int dotCount=0;

	
	
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
		Random rand = new Random();
		
		int r =rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);
		
		Color rc = Color.rgb(r, g, b);
		dot.setFill(rc);
		dot.setOpacity(0.7);
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
			Dot current = dotList.get(i);
			
			if(dotList.get(i).mass==mass) {
				continue;
			}
			
			//if(dotList.get(i).dot.getCenterX()==dot.getCenterX()&&dotList.get(i).dot.getCenterY()==dot.getCenterY()) {
			
			//System.out.println("adding vector for dot :"+i);
			f+=getForceY(dotList.get(i));
			
		}
		
		double tempF = f;
		f=tempF/(dotList.size());
		tempF=f;
		f=tempF/mass;
		//System.out.println("ForceY = "+f+" of dot :"+this);
		return f;
	}
	
	
	public double getForceY(Dot d) {
		return (mass*d.mass/(2*(d.y-y)));
	}
	
	
	public boolean isTouching(Dot d) {
		if(distance(d)<=dot.getRadius()) {
			System.out.println("Touch");
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
		d.dot.setRadius(Math.random()*30+3);
		Random rand = new Random();
		
		int r =rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);
		
		Color rc = Color.rgb(r, g, b);
		d.dot.setFill(rc);
		d.dot.setOpacity(0);
		FadeTransition fadeIn = new FadeTransition(Duration.millis(2000),d.dot);
		fadeIn.setFromValue(0);
		fadeIn.setToValue(0.7);
		fadeIn.play();
	}
	

	

	
	public boolean isOutside(Dot d) {
		Pane p = (Pane) d.dot.getParent();
		if(d.dot.getCenterX()<0||d.dot.getCenterX()>p.getWidth()) {
			if(d.dot.getCenterY()<0||d.dot.getCenterY()>p.getHeight()) {
				System.out.println("Outside");
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
			}
			if(isOutside(current)) {
				pop(current);
			}
		}
	}
	
	public void jiggleStart() {
		MAXV=1;
	}
	
	public void jiggleEnd() {
		MAXV=.1;
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
		
		
		
		
		//System.out.println("XVelocity :"+this.velX);
		
		double newX = this.dot.getCenterX()+this.velX;
		double newY = this.dot.getCenterY()+this.velY;
		//System.out.println("Moving x : "+(newX-velX));
		
		this.dot.setCenterX(newX);
		this.x=newX;
		//System.out.println("Moving y to: "+newY);
		this.dot.setCenterY(newY);
		this.y=newY;
		
	}
	


}