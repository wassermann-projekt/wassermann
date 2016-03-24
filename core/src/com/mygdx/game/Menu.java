package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class Menu {
	
	enum MenuState{
		NONE,
		MAIN,
		PAUSE,
		INPUT_HIGHSCORE,
		HIGHSCORE_SCREEN,
		GAMEOVER,
		LOGO
		
	}
	
	private Skin skin;
	private Skin background_skin;
	private Stage stage;
	private MyGdxGame game;
	private Highscore highscore;
	private int button_w, button_h, button_space;
	
	private MenuState state;
	private TextField textfield;
	private long score_to_save;
	private Label heading; 
	private SpriteBatch batch;
	private Sprite logo; 
	
	
	

	public Menu(InputMultiplexer mp, MyGdxGame g, Highscore hs, BitmapFont font) {
		button_w = 200;
		button_h = 50;
		button_space = 10;
		state = MenuState.NONE;

		stage = new Stage();
		game = g;
		highscore = hs;
		loadSkin(font);
		mp.addProcessor(stage);
	}


	public void loadSkin(BitmapFont font) {
		skin = new Skin();
		skin.add("default", font);
		Pixmap pixmap = new Pixmap(button_w, button_h, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background", new Texture(pixmap));
		skin.add("cursor", new Texture(pixmap));

		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("background", Color.BLUE);
		textButtonStyle.down = skin.newDrawable("background", Color.CORAL);
		textButtonStyle.over = skin.newDrawable("background", Color.CYAN);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
		textFieldStyle.font = skin.getFont("default");
		textFieldStyle.fontColor = Color.BLUE;
		textFieldStyle.focusedFontColor = Color.WHITE;
		textFieldStyle.cursor = skin.newDrawable("cursor", Color.WHITE);
		textFieldStyle.cursor.setMinWidth(2f);
		skin.add("default", textFieldStyle);

		Label.LabelStyle labelStyle = new Label.LabelStyle();
		labelStyle.font = skin.getFont("default");
		labelStyle.fontColor = Color.WHITE;
		skin.add("default", labelStyle);
		
		background_skin = new Skin();
		background_skin.add("default", font);
		background_skin.add("background", new Texture(pixmap));
		Label.LabelStyle background_style = new Label.LabelStyle();
		background_style.background = background_skin.newDrawable("background", new Color(0f, 0f, 0f, 0.5f));
		background_style.font = background_skin.getFont("default");
		background_skin.add("default", background_style);

	}

	public void loadMainMenu() {
		stage.clear();
		state = MenuState.MAIN;
		
		batch = new SpriteBatch();
	
		
//		Image logo = new Image(new Texture(Gdx.files.internal("logo-ohne-traegerform.png")));
//		logo.setPosition(210, 320);
//		logo.setSize(Gdx.graphics.getWidth()/3, Gdx.graphics.getWidth()/3);

	
		int num_buttons = 3;
		int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		int left = (w - button_w) / 2;
		int bottom = (h + (num_buttons * button_h + (num_buttons - 1)
				* button_space))
				/ 2 - button_h - 125;

		TextButton startButton = new TextButton("START", skin);
		startButton.setPosition(left, bottom);
		startButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.startGame();
			}
		});

		bottom -= button_h + button_space;
		
		TextButton hallofButton = new TextButton("HALL OF FAME", skin);
		hallofButton.setPosition(left, bottom);
		hallofButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				loadHighscoreScreen(false);
			}
		});
		
		bottom -= button_h + button_space;

		TextButton exitButton = new TextButton("EXIT", skin);
		exitButton.setPosition(left, bottom);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.endApplication();
			}
		});

 
		stage.addActor(startButton);
		stage.addActor(hallofButton);
		stage.addActor(exitButton);

//		stage.addActor(logo);


	}

	public void loadPauseMenu() {
		stage.clear();
		state = MenuState.PAUSE;

		int num_buttons = 3;
		int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		int left = (w - button_w) / 2;
		int bottom = (h + (num_buttons * button_h + (num_buttons - 1)
				* button_space))
				/ 2 - button_h;

		TextButton mainMenuButton = new TextButton("MAIN MENU", skin);
		mainMenuButton.setPosition(left, bottom);
		mainMenuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.returnToMainMenu();
			}
		});

		bottom -= button_h + button_space;
		
		TextButton musicButton = new TextButton("MUSIC : " + (game.musicIsPlaying() ? "ON" : "OFF"), skin);
		musicButton.setPosition(left, bottom);
		musicButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.toggleMusic();
				loadPauseMenu();
			}
		});

		bottom -= button_h + button_space;

		TextButton resumeButton = new TextButton("RESUME", skin);
		resumeButton.setPosition(left, bottom);
		resumeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.pauseGame(false);
			}
		});

		stage.addActor(mainMenuButton);
		stage.addActor(musicButton);
		stage.addActor(resumeButton);

	}

	public void loadGameOverMenu() {
		stage.clear();
		state = MenuState.GAMEOVER;

		int num_buttons = 2;
		int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		int left = (w - button_w) / 2;
		int bottom = (h + (num_buttons * button_h + (num_buttons - 1)
				* button_space))
				/ 2 - button_h;
		
		Label background_label = new Label("",background_skin);
		background_label.setSize(w, h);
		
		TextButton mainMenuButton = new TextButton("MAIN MENU", skin);
		mainMenuButton.setPosition(left, bottom);
		mainMenuButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.returnToMainMenu();
			}
		});

		bottom -= button_h + button_space;

		TextButton newGameButton = new TextButton("TRY AGAIN", skin);
		newGameButton.setPosition(left, bottom);
		newGameButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				game.startGame();
			}
		});
		
		stage.addActor(background_label);
		stage.addActor(mainMenuButton);
		stage.addActor(newGameButton);

	}

	public void loadHighscoreInput(long score) {
		stage.clear();
		state = MenuState.INPUT_HIGHSCORE;
		
		score_to_save = score;
		
		int num_buttons = 5;
		int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		int left = (w - button_w) / 2;
		int bottom = (h + (num_buttons * button_h + (num_buttons - 1)
				* button_space))
				/ 2 - button_h;
		
		Label background_label = new Label("",background_skin);
		background_label.setSize(w, h);
		
		Label label0 = new Label("Score " + score + "!", skin);
		label0.setPosition(left, bottom);
		
		bottom -= button_h + button_space;
		
		Label label1 = new Label("You have entered the Hall Of Fame!", skin);
		label1.setPosition(left, bottom);
		
		bottom -= button_h + button_space;
		
		Label label2 = new Label("Your name:", skin);
		label2.setPosition(left, bottom);
		
		bottom -= button_h + button_space;
		
		textfield = new TextField("", skin);
		textfield.setPosition(left, bottom);
		textfield.setMaxLength(15);
		textfield.clearListeners();
		textfield.addListener(textfield.new TextFieldClickListener(){
			@Override
			public boolean keyDown(InputEvent event, int keycode){
				if(keycode == Keys.ENTER){
					highscore.enterHighscore(textfield.getText(), score_to_save);
					loadHighscoreScreen(true);
				}
				else if(keycode == Keys.ESCAPE){
					loadHighscoreScreen(true);
				}
				return true;
			}
		});
		
		bottom -= button_h + button_space;
		
		TextButton proceedButton = new TextButton("PROCEED", skin);
		proceedButton.setPosition(left, bottom);
		proceedButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				highscore.enterHighscore(textfield.getText(), score_to_save);
				loadHighscoreScreen(true);
			}
		});
		
		bottom -= button_h + button_space;
		
		TextButton cancelButton = new TextButton("CANCEL", skin);
		cancelButton.setPosition(left, bottom);
		cancelButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				loadHighscoreScreen(true);
			}
		});
		
		
		stage.addActor(background_label);
		stage.addActor(label0);
		stage.addActor(label1);
		stage.addActor(label2);
		stage.addActor(textfield);
		stage.addActor(proceedButton);
		stage.addActor(cancelButton);
		
		stage.setKeyboardFocus(textfield);
		
	}

	public void loadHighscoreScreen(boolean gameover) {
		stage.clear();
		state = MenuState.HIGHSCORE_SCREEN;

		int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
		int left = (w - button_w - button_space) / 2, bottom = h - (button_h + 80);
		
//		if(gameover){
			Label background_label = new Label("",background_skin);
			background_label.setSize(w, h);
			stage.addActor(background_label);
//		}
		
		for(int i = 0; i < highscore.numItems(); ++i){
				String namestr = "";
			if(!highscore.getScoreString(i).isEmpty()){
				namestr = "(" + (i + 1) + ")   " + highscore.getName(i);
			}
			
				Label nameLabel = new Label(namestr, skin);
				nameLabel.setPosition(left, bottom);
				nameLabel.setSize(button_w, button_h);
				
				
				Label scoreLabel = new Label(highscore.getScoreString(i), skin);
				scoreLabel.setPosition(left + button_w + button_space, bottom);
				scoreLabel.setSize(button_w, button_h);
				
				stage.addActor(nameLabel);
				stage.addActor(scoreLabel);
				
				bottom -= button_h;
			
		}
		
		bottom -= 20;
		
		if(gameover){
			
			TextButton mainMenuButton = new TextButton("MAIN MENU", skin);
			mainMenuButton.setPosition(left, bottom);
			mainMenuButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.returnToMainMenu();
				}
			});

			bottom -= button_h + button_space;

			TextButton newGameButton = new TextButton("TRY AGAIN", skin);
			newGameButton.setPosition(left, bottom);
			newGameButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					game.startGame();
				}
			});

			stage.addActor(mainMenuButton);
			stage.addActor(newGameButton);	
		}
		else{
			
			if(!highscore.getScoreString(0).isEmpty()){			
				TextButton resetButton = new TextButton("RESET", skin);
				resetButton.setPosition(left, bottom);
				resetButton.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y){
						highscore.resetScores();
						loadHighscoreScreen(false);
					}
				});
				
				stage.addActor(resetButton);
				bottom -= button_h + button_space;
			}
			
			TextButton backButton = new TextButton("BACK", skin);
			backButton.setPosition(left, bottom);
			backButton.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					loadMainMenu();
				}
			});
			
			
			stage.addActor(backButton);
			
		}
		

	}
	
	public void handleKey(int keycode){
		if(state == MenuState.NONE){
			if(keycode == Keys.ESCAPE || keycode == Keys.SPACE){
				game.pauseGame(true);
			}
		}
		else if(state == MenuState.MAIN){
			if(keycode == Keys.ENTER){
				game.startGame();
			}
			if(keycode == Keys.ESCAPE){
				
			}
		}
		else if(state == MenuState.PAUSE){
			if(keycode == Keys.ESCAPE || keycode == Keys.SPACE){
				game.pauseGame(false);
			}
		}
		else if(state == MenuState.INPUT_HIGHSCORE){
			
		}	
	}

	public void unloadMenu() {
		stage.clear();
		state = MenuState.NONE;
	}

	public void render() {
		if (game.getState() == GameState.MAINMENU) {
			game.renderLogo();
		}

		stage.act();
		stage.draw();
	}

}
