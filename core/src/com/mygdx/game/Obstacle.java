package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Obstacle {
	private Sprite sprite;
	private int type;
	private int bahn;
	private float posy;
	
	
	public Obstacle(Sprite s, int t, int b, float py){
		sprite = s;
		type = t;
		bahn = b;
		posy = py;
		
		if(bahn < 1){
			bahn = 1;
		}
		if(bahn > 7){
			bahn = 7;
		}	
	}
	
	
	public int getType(){
		return type;
	}
	
	public float getY(){
		return posy;
	}
	
	public int getPosition(){
		return bahn;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
}
