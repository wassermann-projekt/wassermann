package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Input.Keys;

public class EventListener implements InputProcessor {
	public MyGdxGame game;
	private Menu menu;
	private Music bewegungmusic;

	@Override
	public boolean keyDown(int keycode) {

		// keine Steuerung wenn das Spiel pausiert/Gameover/Gefrohren ist
		if (!game.isPaused() && !game.isGameOver() && !game.isFrozen()) {

			if (game.getState() == GameState.UPPERWORLD) {

				switch (keycode) {
				case Keys.LEFT:
					game.changeSwimmerPosition_swim(-1);
					bewegungmusic = Gdx.audio.newMusic(Gdx.files
							.internal("button-21.mp3"));

					bewegungmusic.setVolume(0.06f);
					if(game.music_enabled){
						bewegungmusic.play();
					}

					break;
				case Keys.RIGHT:
					game.changeSwimmerPosition_swim(1);
					bewegungmusic = Gdx.audio.newMusic(Gdx.files
							.internal("button-21.mp3"));
					bewegungmusic.setVolume(0.05f);
					if(game.music_enabled){
						bewegungmusic.play();
					}
					break;
				case Keys.UP:
					break;
				case Keys.DOWN:
					if (game.getBrillen()>0){
						game.changeDiveState();
					}
					break;
				}
			}

			else if (game.getState() == GameState.LOWERWORLD) {

				switch (keycode) {
				case Keys.UP:
					game.changeSwimmerPosition_dive(1);
					break;
				case Keys.DOWN:
					game.changeSwimmerPosition_dive(-1);
					break;
				}
			}

		}
/*
		if (game.getState() != GameState.MAINMENU) {
			if (keycode == Keys.ESCAPE) {
				game.pauseGame(true);

			}
		}
*/		
		menu.handleKey(keycode);

		return false;

	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setGame(MyGdxGame g) {
		game = g;
	}
	
	public void setMenu(Menu m){
		menu = m;
	}

}
