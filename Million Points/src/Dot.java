package application;

import java.util.ArrayList;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Dot {
	
	final double G= 6.67*Math.pow(10, -11);
	ArrayList<Dot> dotList;
	double mass;
	double x;
	double y;
	double initialVel;
	double velX;
	double velY;
	Circle dot;
	
	
	public Dot(double m, double x, double y) {
		this.mass=m;
		this.x=x;
		this.y=y;
		this.initialVel=0;
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
	
	
	public double getMass() {
		return this.mass;
	}
	
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public double getInitialV() {
		return this.initialVel;
	}
	
	public double getVX() {
		return this.velX;
	}
	
	public double getVY() {
		return this.velY;
	}
	
	public Circle getCircle() {
		return this.dot;
	}
	
	public void setList(ArrayList<Dot> list) {
		this.dotList.addAll(list);
	}
	
	public void setMass(double m) {
		this.mass=m;
		dot.setRadius(m);
	}
	
	public void setX(double x) {
		dot.setCenterX(x);
		this.x=x;
	}
	
	public void setY(double y) {
		dot.setCenterY(y);
		this.y=y;
	}
	
	public void setInitialV(double v) {
		this.initialVel=v;
	}
	
	public void setVX(double v) {
		this.velX=v;
	}
	
	public void setVY(double v) {
		this.velY=v;
	}
	
	public double getXPull() {
		if(dotList.size()==0) {
			return 0;
		}else {
			double x2=0;
			for (int i=0;i<dotList.size();i++) {
				//System.out.println("List size for dot: "+dotList.get(i)+" is "+dotList.size());
				if(dotList.get(i).equals(this)) {
					continue;
				}
				//System.out.println("gEquation x"+i+" = "+ gEquationX(dotList.get(i)));
				double xPull=gEquationX(dotList.get(i));
				if(Double.isInfinite(xPull)) {
					xPull=0.00000;
				}
				
				
				x2+=xPull;
			}
			
			x2 = x2/dotList.size();
			
			
			double accl = x2/this.mass;
			boolean isNegative=false;
			if(accl<0) {
				accl=accl*-1;
				isNegative=true;
			}
			
			accl=Math.sqrt(accl);
			if(isNegative) {
				accl=accl*-1;
			}
			//System.out.println("X pull = "+accl);
			return accl;
		}
	}
	
	public double getYPull() {
		if(dotList.size()==0) {
			return 0;
		}else {
			double y2=0;
			for (int i=0;i<dotList.size();i++) {
				if(dotList.get(i).equals(this)) {
					continue;
				}
				//System.out.println("gEquation y"+i+" = "+ gEquationY(dotList.get(i)));
				double yPull=gEquationY(dotList.get(i));
				
				
				if(Double.isInfinite(yPull)) {
					yPull=0.00000;
				}
				
				y2+=yPull;
			}
			
			y2 = y2/dotList.size();
			
			double accl = y2/this.mass;
			boolean isNegative=false;
			if(accl<0) {
				accl=accl*-1;
				isNegative=true;
			}
			
			accl=Math.sqrt(accl);
			if(isNegative) {
				accl=accl*-1;
			}
			
			
			System.out.println("Y pull = "+accl);
			return accl;
		}
	}
	
	public double gEquationX(Dot d) {
		if(dEquationX(d)>0) {
			return ((this.mass*d.getMass())/Math.pow(dEquationX(d), 2));
		}
		else return ((this.mass*d.getMass())/Math.pow(dEquationX(d), 2)*-1);
		
	}
	
	public double gEquationY(Dot d) {
		if(dEquationY(d)>0) {
			return ((this.mass*d.getMass())/Math.pow(dEquationY(d), 2));
		}
		else return ((this.mass*d.getMass())/Math.pow(dEquationY(d), 2)*-1);
	}
	
	
	
	public double dEquationX(Dot d) {
		double num = d.getX()-getX();
		return num*100;
	}
	//You need to average out the x and y distance it acts as though if x distance was 0 then total distance is 0
	
	public double dEquationY(Dot d) {
		double num = (d.getY()-getY());
		//System.out.println("Y distance = "+num);
		return num*100;
	}
	
	
	public double distance(Dot d) {
		return Math.sqrt(Math.pow((this.x-d.getX()), 2)+Math.pow(this.y-d.getY(), 2));
	}
	
	public double gEquation(Dot d) {
		return ((this.mass*d.getMass())/Math.pow(distance(d), 2));
	}
	public void update() {
		this.velX+=getXPull();
		this.velY+=getYPull();
		double newX = this.dot.getCenterX()+this.velX;
		double newY = this.dot.getCenterY()+this.velY;
		//System.out.println("Moving x to: "+newX);
		
		this.dot.setCenterX(newX);
		this.x=newX;
		//System.out.println("Moving y to: "+newY);
		this.dot.setCenterY(newY);
		this.y=newY;
		
	}
	


}
