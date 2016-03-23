package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Obstacle {
	private Sprite sprite;
	private Sprite[] sprites_anim;
	
	//Anzahl der animierten Teile
	private int num_parts;
	
	/*0 := Felsen
	 *1 := Seerose
	 *2	:= Hai
	 *3	:= Schwan 
	 *4 := Herzen
	 *5 := Coins
	 *6 := Stopwatch
	 *7 := Taucherbrille
	 *8 := Loch / Strudel
	 *100:= Unterwasserhindernis
	 */
	private int type;
	private long line;

	//Obstacle animiert?
	private boolean anim;
	// Von 1 bis 7
	private int bahn;
	private float posY;
	private float posX;
	//0 := keine Richtung
	//1 := schwimmt nach rechts
	//2 := schwimmt nach links
	private int richtung = 0;
	
	//Laenge Unterwasserhindernis
	private int laenge;
	
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
	
	//Konstruktor fuer nicht-animierte Hindernisse unter Wasser
		public Obstacle(Sprite s, int t, float px, float py, int laenge_h){
			sprite = s;
			type = t;
			posX = px;
			posY = py;
			laenge = laenge_h;
			anim = false;
			num_parts = 0;	
		}
	
	public int getType(){
		return type;
	}
	
	public long getLine(){
		return line;
	}
	
	public void setLine(long L){
		line = L;
	}
	
	public float getX(){
		return posX;
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
	
	public Sprite[] getSpritesAnim(){
		return sprites_anim;
	}
	
	public int getRichtung(){
		return richtung;
	}
	
	public int getLaenge(){
		return laenge;
	}
	
	public void setX(float new_pos){
		posX = new_pos;
		return;
	}
	
	public void setY(float new_pos){
		posY = new_pos;
		return;
	}
	
	public void setBahn(int new_bahn){
		bahn = new_bahn;
		return;
	}
	
	public void setSprite(Sprite new_sprite){
		sprite = new_sprite;
		return;
	}
	
	public void setRichtung(int new_richtung){
		richtung = new_richtung;
		return;
	}
	
	public void setLaenge(int new_laenge){
		laenge = new_laenge;
		return;
	}
	
	public void dispose(){
		//Speicherplatz von Texturen freigeben
		sprite.getTexture().dispose();
		if(anim){
			for(int i = 0; i<num_parts; i++){
				sprites_anim[i].getTexture().dispose();
			}
		}
		return;
	}
	
}
