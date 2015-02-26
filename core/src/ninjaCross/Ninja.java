package ninjaCross;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Ninja extends Actor{

	public static boolean DOWN = false;
	
	Texture texture;
	TextureRegion region;
	Sprite sprite;
	
	private float y_speed, x_speed;
	
	private float x, y;
	
	float stateTime = 0;
	float formerStateTime = 0;
	static float stepTime = 0.025f;
	
	private static float rotate = -20;
	private static float gravity = 0;//重力
	
	float[][] collision;
	
	
	public Ninja(float width, float height){
		this.setWidth(width);
		this.setHeight(height);
		
		texture = new Texture(Gdx.files.internal(File.ninja));
		region = new TextureRegion(texture);
		sprite = new Sprite(region);
		
		sprite.setSize(width, height);
		sprite.setOrigin(width / 2, height / 2);
		
		collision = new float[5][2];
		
		this.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				DOWN = true;
				setPosition(x - getWidth() / 2, y - getHeight() / 2);
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				DOWN = false;
				setPosition(x - getWidth() / 2, y - getHeight() / 2);
			}
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer){
				setPosition(x - getWidth() / 2, y - getHeight() / 2);
			}
		});
	}
	
	public void setPosition(float x, float y){
		this.x = x;
		this.y = y;
		
		this.iniCollisionPosition();
		
		sprite.setPosition(x, y);
		
		stateTime = 0;
		formerStateTime = 0;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		stateTime += Gdx.graphics.getDeltaTime();
		if(stateTime - formerStateTime > stepTime){
			formerStateTime = stateTime;
			sprite.rotate(rotate);
			this.move(get_x_speed_With_stateTime(), y_speed);
		}
		sprite.draw(batch);
	}
	
	public void calculateDefaultSpeed(float frontX, float frontY, float nowX, float nowY){
		x_speed = (nowX - frontX) / 30;
		y_speed = (nowY - frontY) / 30;
		
		stateTime = 0;
		formerStateTime = 0;
	}
	
	public void setGravity(float g){
		Ninja.gravity = g;
	}

	private float get_x_speed_With_stateTime() {
		return x_speed + stateTime * gravity;
	}
	
	private void iniCollisionPosition(){
		collision[0][0] = x + this.getWidth() / 2;//中心
		collision[0][1] = y + this.getHeight() / 2;
		
		collision[1][0] = x;//极左
		collision[1][1] = collision[0][1];
		
		collision[2][0] = collision[0][0];//极上
		collision[2][1] = y + this.getHeight();
		
		collision[3][0] = x + this.getWidth();//极右
		collision[3][1] = collision[0][1];
		
		collision[4][0] = collision[0][0];//极下
		collision[4][1] = y;
	}
	
	private void move(float mx, float my){
		x += mx;
		y += my;
		for(int i = 0; i < 5; i++){
			collision[i][0] += mx;
			collision[i][1] += my;
		}
		
		sprite.setPosition(x, y);
	}
	
}
