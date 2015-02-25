package ninjaCross;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class TouchPointer implements InputProcessor {
	
	public boolean DOWN = false;
	
	private int defaultX, defaultY;
	
	private int downX, downY;
	
	private int nowX, nowY;
	
	public TouchPointer(int defaultX, int defaultY){
		this.defaultX = defaultX;
		this.defaultY = defaultY;
		this.initialPosition();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		this.initialPosition();
		downX = screenX;
		downY = Gdx.graphics.getHeight() - screenY;
		nowX = screenX;
		nowY = Gdx.graphics.getHeight() - screenY;
		DOWN = true;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		DOWN = false;
//		this.initialPosition();
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		nowX = screenX;
		nowY = Gdx.graphics.getHeight() - screenY;
		DOWN = true;
//		this.printPosition();
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		nowX = screenX;
		nowY = Gdx.graphics.getHeight() - screenY;
//		this.printPosition();
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public int getDefaultX(){
		return defaultX;
	}
	
	public int getDefaultY(){
		return defaultY;
	}
	
	public int getDownX(){
		return downX;
	}
	
	public int getDownY(){
		return downY;
	}
	
	public int getNowX(){
		return nowX;
	}
	
	public int getNowY(){
		return nowY;
	}
	
	public void initialPosition(){
		this.downX = this.defaultX;
		this.downY = this.defaultY;
		
		this.nowX = this.defaultX;
		this.nowY = this.defaultY;
	}
	
//	private void printPosition(){
//		System.out.println(nowX + ", " + nowY);
//	}

}
