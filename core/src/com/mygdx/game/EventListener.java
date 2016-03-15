package com.mygdx.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;

public class EventListener implements InputProcessor {
	private MyGdxGame game;
	
	@Override
	public boolean keyDown(int keycode) {
		
		if(game.getState() == 1){
		
			switch(keycode)
			{
			case Keys.LEFT:
				game.changeSwimmerPosition_swim(-1);
				break;
			case Keys.RIGHT:
				game.changeSwimmerPosition_swim(1);
				break;
			case Keys.UP:
				break;
			case Keys.DOWN:
				break;
			case Keys.ALT_LEFT:
				game.changeDiveState();
			}
			
		}
		
		else if (game.getState() == 2){
			
			switch(keycode)
			{
			case Keys.UP:
				game.changeSwimmerPosition_dive(5000);
				break;
			case Keys.DOWN:
				game.changeSwimmerPosition_dive(-5000);
				break;
			}
			
		}
		
		if(game.getState() != 0){
			if(keycode == Keys.ESCAPE){
				game.pauseGame(true);
			}
		}
		
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
	
	public void setGame(MyGdxGame g)
	{
		game = g;
	}
	
	

}
