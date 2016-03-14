package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class MyGdxGame extends ApplicationAdapter {
	private int state;
	
	private Sprite wellen1;
	private Sprite wellen2;
	private Sprite ufer_links;
	private Sprite ufer_rechts;
	private Sprite herz_leer;
	private Sprite herz_voll;
	private Sprite swimmer;
	
	private SpriteBatch batch;
		
	// Schrift
	private BitmapFont font;

	//Test-Hindernis 
	//TODO: Hindernisse nicht hardcoden, sondern dynamisch?
	private Sprite felsen;
	
	//Graphics Updates -> Variables to update positions
	private float wellen_x_pos;

	//Position Test-Hindernis
	private float felsen_x_pos;
	
	// Variablen für Schwimmer, Hintergrund	
	private float geschwindigkeit;
	//Aenderung der Geschwindigkeit
	private float beschleunigung;
	
	//swimmer variables
	//Bahn des Schwimmers
	private int swimmer_position;
	//swimmer Groesse
	private float swimmer_width;
	private float swimmer_height;
	//Abstand zur Bahn
	private float swimmer_offset;
	
	private int score;
	private int level;
	private int health;
		
	// shortcuts for graphics fields
	private int width, height;
	private float ppiX, ppiY;

		
	private EventListener steuerung;
	
	//Kollisionserkennung -> TODO: Ohne Variable loesen
	private boolean accident;
	
	@Override
	public void create () {
		//init state
		state = 1;
		
		//Infos Screen;
		readGraphics();
		
		//New Sprite Batch
		batch = new SpriteBatch();
		
		
		//init Wellentextur
		wellen1 = new Sprite(new Texture("wellen.png"));
		wellen1.setSize(width, height);
		wellen2 = new Sprite(new Texture("wellen.png"));
		wellen2.setSize(width, height);
		wellen_x_pos = 0;
		
		//Anzeigen
		//init Lebens-Anzeige
		herz_leer = new Sprite(new Texture("herz_leer.png"));
		herz_voll = new Sprite(new Texture ("herz_voll.png"));		
		herz_voll.setSize(width/18, height/18);
		herz_leer.setSize(width/18, height/18);
		
		health = 5;
		
		//Schrift
		//	font = new BitmapFont(Gdx.files.internal("arial-15.fnt"), false);
		//font.setColor(Color.BLACK);

		//init Swimmer_Grafik
		swimmer = new Sprite(new Texture("schwimmer_aufsicht.png"));

		//TODO: Width/9 statt width/7
		swimmer_offset = (width / 7) * 1/8;
		swimmer_width = (width / 7) * 3/4;
		swimmer_height= (width/7) * 3/4;

		//init Ufertextur
		ufer_links = new Sprite(new Texture("ufer.png"));
		ufer_links.setSize(width/9, height);
		ufer_rechts = new Sprite(new Texture("ufer.png"));
		ufer_rechts.setSize(width/9, height);
		ufer_rechts.flip(true, false);
		ufer_rechts.setOrigin(width - ufer_rechts.getWidth(), 0);
		
		//Test Hindernis
		felsen = new Sprite(new Texture("hindernis_felsen.png"));
		felsen.setSize(width/9, height/9);
		felsen_x_pos = 0.0f;
	
		//init geschwindigkeit
		geschwindigkeit = 1.0f;
		beschleunigung = 0;
		
		//init swimmer_position
		swimmer_position = 4;
		
		//init score
		score = 0;
		level = 1;
		
		//erstelle und registriere Steuerung
		steuerung = new EventListener();
		steuerung.setGame(this);
		Gdx.input.setInputProcessor(steuerung);
	}

	
	@Override 
	public void render () {
		if(state == 1)render_upperworld();
		
		//Game-Variablen updaten
		update_variables();
		
		//Graphik-Variablem updaten
		update_graphics();

	}
	
	private void render_upperworld(){
		// TODO: Hindernisse generieren
		
		// Hindernisse bewegen
				
		// Kollisionsabfrage
		
		//Hintergrundfarbe
		Gdx.gl.glClearColor(0, 0.6f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
		batch.begin();
				
		//Hintergrund		
		batch.draw(wellen1, 0, wellen_x_pos % height, width, height);
		batch.draw(wellen2, 0, (wellen_x_pos % (height)) + height, width, height);
		batch.draw(ufer_links, 0, 0, width/9, height);
		batch.draw(ufer_rechts, ufer_rechts.getOriginX(), ufer_rechts.getOriginY(), width/9, height);
		
		// Herzen update
		
		if (health == 5){
			batch.draw(herz_voll, 19, 440, width/18, height/18);
			batch.draw(herz_voll, 55, 440, width/18, height/18);
			batch.draw(herz_voll, 90, 440, width/18, height/18);
			batch.draw(herz_voll, 125, 440, width/18, height/18);
			batch.draw(herz_voll, 160, 440, width/18, height/18);
				
		} else if (health == 4) {
			batch.draw(herz_voll, 19, 440, width/18, height/18);
			batch.draw(herz_voll, 55, 440, width/18, height/18);
			batch.draw(herz_voll, 90, 440, width/18, height/18);
			batch.draw(herz_voll, 125, 440, width/18, height/18);
			batch.draw(herz_leer, 160, 440, width/18, height/18);
					
		}else if (health == 3) {
			batch.draw(herz_voll, 19, 440, width/18, height/18);
			batch.draw(herz_voll, 55, 440, width/18, height/18);
			batch.draw(herz_voll, 90, 440, width/18, height/18);
			batch.draw(herz_leer, 125, 440, width/18, height/18);
			batch.draw(herz_leer, 160, 440, width/18, height/18);
				
				
		}else if (health == 2) {
			batch.draw(herz_voll, 19, 440, width/18, height/18);
			batch.draw(herz_voll, 55, 440, width/18, height/18);
			batch.draw(herz_leer, 90, 440, width/18, height/18);
			batch.draw(herz_leer, 125, 440, width/18, height/18);
			batch.draw(herz_leer, 160, 440, width/18, height/18);
				
		}else if (health == 1) {
			batch.draw(herz_voll, 19, 440, width/18, height/18);
			batch.draw(herz_leer, 55, 440, width/18, height/18);
			batch.draw(herz_leer, 90, 440, width/18, height/18);
			batch.draw(herz_leer, 125, 440, width/18, height/18);
			batch.draw(herz_leer, 160, 440, width/18, height/18);
			
		}
		else if (health == 0) {
			batch.draw(herz_leer, 19, 440, width/18, height/18);
			batch.draw(herz_leer, 55, 440, width/18, height/18);
			batch.draw(herz_leer, 90, 440, width/18, height/18);
			batch.draw(herz_leer, 125, 440, width/18, height/18);
			batch.draw(herz_leer, 160, 440, width/18, height/18);
				
		}		
		
		batch.draw(swimmer, (width-2*width/9) / 7 * (swimmer_position-1) + swimmer_offset + width/9, 0, swimmer_width, swimmer_width);
		batch.draw(felsen, (width/9)*2, height-felsen_x_pos, width/9, width/9);				
		//	batch.draw(herz_leer, 5, 5, width/17, height/17 ); 
					
		batch.end();
		
	}
	
	//Helpermethods
	
	public void changeSwimmerPosition(int change){
		swimmer_position += change;	
		if(swimmer_position < 1){
			swimmer_position = 1;
		}
		if(swimmer_position > 7)
		{
			swimmer_position = 7;
		}
	}
	
	public boolean meetObstacle(Obstacle obs, int swimmer_position){
		if(swimmer_position == obs.getPosition()){
			if(swimmer_height == obs.getY()){
				accident = true;
			}else{
				accident = false;
			}
		}
		return accident;
	
	}
	
	public void loseLife(){
		if(accident == true){
			health--;
			accident=false;
		}
	}
	
	private void update_graphics(){
		wellen_x_pos -= geschwindigkeit;
		felsen_x_pos = (felsen_x_pos + geschwindigkeit)%(height+felsen.getHeight());

	}
	
	private void readGraphics() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		ppiX = Gdx.graphics.getPpiX();
		ppiY = Gdx.graphics.getPpiY();
	}
	
	

	private void update_variables() {
		geschwindigkeit += beschleunigung;
		score += 1;
		level = (score/10);

	}
}
