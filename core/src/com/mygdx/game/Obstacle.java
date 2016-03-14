package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Obstacle {
	private Sprite sprite;
	private Sprite[] sprites_anim;
	//Anzahl der animierten Teile
	private int num_parts;
	private int type;
	//Obstacle animiert?
	private boolean anim;
	private int bahn;
	private float posY;
	
	//Konstruktor fuer nicht-animierte Hindernisse
	public Obstacle(Sprite s, int t, int b, float py){
		sprite = s;
		type = t;
		bahn = b;
		posY = py;
		anim = false;
		num_parts = 0;
		
		if(bahn < 1){
			bahn = 1;
		}
		if(bahn > 7){
			bahn = 7;
		}	
	}
	
	//Konstruktor fuer animierte Hindernisse
	public Obstacle(Sprite s, int t, int b, float py, int np, Sprite[] s_anim){
		sprite = s;
		type = t;
		bahn = b;
		posY = py;
		anim = true;
		num_parts = np;
		sprites_anim = s_anim;
			
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
		return posY;
	}
	
	public int getBahn(){
		return bahn;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public void setY(float new_pos){
		posY = new_pos;
	}
	
}
