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
	private Sprite ufer_links;
	private Sprite ufer_rechts;
	//Test-Hindernis 
	//TODO: Hindernisse nicht hardcoden, sondern dynamisch?
	private Sprite felsen;
	private SpriteBatch batch; 
	
	//Graphics Updates -> Variables to update positions
	private float wellen_x_pos;

	//Position Test-Hindernis
	private float felsen_x_pos;
	
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
		batch.draw(ufer_links, 0, 0, width/9, height);
		batch.draw(ufer_rechts, ufer_rechts.getOriginX(), ufer_rechts.getOriginY(), width/9, height);
		batch.draw(felsen, (width/9)*2, height-felsen_x_pos, width/9, width/9);
		batch.end();
		
		update_graphics();
	}
	
	//Helpermethods
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
}
