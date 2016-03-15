package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class Menu {
	private Skin skin;
	private Stage stage;
	private MyGdxGame game;
	
	public Menu(InputMultiplexer mp, MyGdxGame g){
		stage = new Stage();
		game = g;
		loadSkin();
		//Gdx.input.setInputProcessor(stage);
		mp.addProcessor(stage);
	}
	
	public void loadSkin(){
		BitmapFont font = new BitmapFont();
		skin = new Skin();
		skin.add("default", font);
		  //Create a texture
		  Pixmap pixmap = new Pixmap(200, 50, Pixmap.Format.RGB888);
		  pixmap.setColor(Color.WHITE);
		  pixmap.fill();
		  skin.add("background",new Texture(pixmap));
		 
		  //Create a button style
		  TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		  textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
		  textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
		  textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
		  textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
		  textButtonStyle.font = skin.getFont("default");
		  skin.add("default", textButtonStyle);
		
	}
	
	public void loadMainMenu(){
		//stage.clear();
		
		TextButton startButton = new TextButton("START", skin);
		startButton.setPosition(0, 60);
		startButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.startGame();
			}
		});
		TextButton exitButton = new TextButton("EXIT", skin);
		exitButton.setPosition(0, 0);
		
		stage.addActor(startButton);
		stage.addActor(exitButton);
	}
	
	public void render(){
		Gdx.gl.glClearColor(0, 0.6f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act();
		stage.draw();
	}
	

}

