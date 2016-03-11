package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Obstacle {
	private Sprite sprite;
	private int type;
	private float posx;
	private float posy;
	
	
	public Obstacle(Sprite s, int t, float px, float py){
		sprite = s;
		type = t;
		posx = px;
		posy = py;
		
		
	}
	
	
	public int getType(){
		return type;
	}
	
	/*public boolean blocks(int b){
		if(b < 1 || b > 7){
			// error
		}
		
	}*/
	
	
	public float getY(){
		return posy;
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(sprite, posx, posy);
	}
	
	
	

}
