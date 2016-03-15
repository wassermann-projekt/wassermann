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
	private int button_w, button_h, button_space;
	
	public Menu(InputMultiplexer mp, MyGdxGame g, BitmapFont font){
		button_w = 200;
		button_h = 50;
		button_space = 10;
		
		stage = new Stage();
		game = g;
		loadSkin(font);
		mp.addProcessor(stage);
	}
	
	public void loadSkin(BitmapFont font){
		skin = new Skin();
		skin.add("default", font);
		Pixmap pixmap = new Pixmap(button_w, button_h, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background",new Texture(pixmap));
		 
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("background", Color.BLUE);
		textButtonStyle.down = skin.newDrawable("background", Color.CORAL);
		textButtonStyle.over = skin.newDrawable("background", Color.CYAN);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		
	}
	
	public void loadMainMenu(){
		stage.clear();
		
		int num_buttons = 2;
		int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		int left = (w - button_w) / 2;
		int top = (h + (num_buttons * button_h + (num_buttons - 1) * button_space)) / 2 - button_h;
		
		TextButton startButton = new TextButton("START", skin);
		startButton.setPosition(left, top);
		startButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.startGame();
			}
		});
		
		top -= button_h + button_space;
		
		TextButton exitButton = new TextButton("EXIT", skin);
		exitButton.setPosition(left, top);
		exitButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.endApplication();
			}
		});
		
		stage.addActor(startButton);
		stage.addActor(exitButton);
	}
	
	public void loadPauseMenu(){
		stage.clear();
		
		int num_buttons = 2;
		int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		int left = (w - button_w) / 2;
		int top = (h + (num_buttons * button_h + (num_buttons - 1) * button_space)) / 2 - button_h;
		
		TextButton mainMenuButton = new TextButton("MAIN MENU", skin);
		mainMenuButton.setPosition(left, top);
		mainMenuButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.returnToMainMenu();
			}
		});
		
		top -= button_h + button_space;
		
		TextButton resumeButton = new TextButton("RESUME", skin);
		resumeButton.setPosition(left, top);
		resumeButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.pauseGame(false);
			}
		});
		
		stage.addActor(mainMenuButton);
		stage.addActor(resumeButton);
		
	}
	
	public void loadGameOverMenu(){
		stage.clear();
		
		int num_buttons = 2;
		int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		int left = (w - button_w) / 2;
		int top = (h + (num_buttons * button_h + (num_buttons - 1) * button_space)) / 2 - button_h;
		
		TextButton mainMenuButton = new TextButton("MAIN MENU", skin);
		mainMenuButton.setPosition(left, top);
		mainMenuButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.returnToMainMenu();
			}
		});
		
		top -= button_h + button_space;
		
		TextButton newGameButton = new TextButton("TRY AGAIN", skin);
		newGameButton.setPosition(left, top);
		newGameButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				game.startGame();
			}
		});
		
		stage.addActor(mainMenuButton);
		stage.addActor(newGameButton);
		
	}
	
	public void render(){
		if(game.getState() == GameState.MAINMENU){
			Gdx.gl.glClearColor(0, 0.6f, 0.9f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		}
		
		stage.act();
		stage.draw();
	}
	

}

