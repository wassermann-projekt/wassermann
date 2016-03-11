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
	private SpriteBatch batch;
	//Graphics Updates -> Variables to update positions
	private float wellen_x_pos;
	
	// Variablen für Schwimmer, Hinderniss
	
	private int schwimmer_x_pos;
	private float hinderniss_y_pos;
	
	private float geschwindigkeit;
	
	// shortcuts for graphics fields
	private int width, height;
	private float ppiX, ppiY;
	
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
		
		//init geschwindigkeit
		geschwindigkeit = 1.0f;
	}

	@Override
	public void render () {
		
		// Hindernisse generieren
		
		// Hindernisse bewegen
		
		// Kollisionsabfrage
		
		Gdx.gl.glClearColor(0, 0.6f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(wellen1, 0, wellen_x_pos % height, width, height);
		batch.draw(wellen2, 0, (wellen_x_pos % (height)) + height, width, height);
		update_graphics();
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
	
	public boolean collision(isBlocked&&po){
		
	}
}
