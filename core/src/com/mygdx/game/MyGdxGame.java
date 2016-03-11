package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	private Sprite wellen1;
	private Sprite wellen2;
	private Sprite swimmer;
	private SpriteBatch batch;
	//Graphics Updates -> Variables to update positions
	private float wellen_x_pos;
	
	private float geschwindigkeit;
	private float beschleunigung;
	//swimmer variables
	private int swimmer_position;
	//swimmer constants
	private float swimmer_width;
	private float swimmer_offset;
	// game variables
	private int score;
	private int level;
	private int health;
	
	// shortcuts for graphics fields
	private int width, height;
	private float ppiX, ppiY;
	
	
	private EventListener steuerung;
	
	@Override
	public void create () {
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
		
		//init Swimmer_Grafik
		swimmer = new Sprite(new Texture("schwimmer_aufsicht.png"));
		swimmer_offset = (width / 7) * 1/8;
		swimmer_width = (width / 7) * 3/4;
		
		//init geschwindigkeit
		geschwindigkeit = 1.0f;
		beschleunigung = 0;
		
		//init swimmer_position
		swimmer_position = 4;
		
		//init score
		score = 0;
		level = 1;
		health = 5;
		
		//erstelle und registriere Steuerung
		steuerung = new EventListener();
		steuerung.setGame(this);
		Gdx.input.setInputProcessor(steuerung);
	}
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
	
	@Override 
	public void render () {
		Gdx.gl.glClearColor(0, 0.6f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(wellen1, 0, wellen_x_pos % height, width, height);
		batch.draw(wellen2, 0, (wellen_x_pos % (height)) + height, width, height);
		batch.draw(swimmer, width / 7 * (swimmer_position-1) + swimmer_offset, 0, swimmer_width, swimmer_width);
		update_graphics();
		update_variables();
		batch.end();
		
	}
	
	//Helpermethods
	private void update_graphics(){
		wellen_x_pos -= geschwindigkeit;
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
