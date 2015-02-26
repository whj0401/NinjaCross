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
	
	private float r = 0;
	
	float stateTime = 0;
	float formerStateTime = 0;
	static float stepTime = 0.025f;
	
	float dragTime = 0;
	static float maxDragTime = 0.2f;
	
	private static float rotate = -20;
	private static float gravity = 0;//重力
	
	float[][] collision;
	
	
	public Ninja(float width, float height){
		
		texture = new Texture(Gdx.files.internal(File.ninja));
		region = new TextureRegion(texture);
		sprite = new Sprite(region);
		
		sprite.setSize(width, height);
		
		r = width / 2;
		
		sprite.setOrigin(width / 2, height / 2);
		
		collision = new float[9][2];
		
		this.setSize(width, height);
		this.setOrigin(width / 2, height / 2);
		
		this.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				DOWN = true;
//				System.out.println("down");
				dragTime = 0;
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				DOWN = false;
//				System.out.println("up");
			}
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer){
				
				dragTime += Gdx.graphics.getDeltaTime();
				if(dragTime < maxDragTime){
				setOriginPosition(GamePlay.pointer.getNowX(), GamePlay.pointer.getNowY());
				reflect();
				calculateDefaultSpeed(GamePlay.pointer.getDownX(), GamePlay.pointer.getDownY(), GamePlay.pointer.getNowX(), GamePlay.pointer.getNowY());
//				System.out.println("drag");
				}
				else{
					DOWN = false;
				}
			}
		});
	}
	
	@Override
	public void setPosition(float x, float y){
		super.setPosition(x, y);
		this.x = x;
		this.y = y;
		
		this.iniCollisionPosition();
		
		sprite.setPosition(x, y);
		
		stateTime = 0;
		formerStateTime = 0;
	}
	
	public void setOriginPosition(float x, float y){
		this.setPosition(x - getWidth() / 2, y - getHeight() / 2);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		stateTime += Gdx.graphics.getDeltaTime();
		if(stateTime - formerStateTime > stepTime){
			formerStateTime = stateTime;
			sprite.rotate(rotate);
			this.move(get_x_speed_With_stateTime(), y_speed);
//			System.out.println(y_speed);
		}
		sprite.draw(batch);
	}
	
	public void calculateDefaultSpeed(float frontX, float frontY, float nowX, float nowY){
		x_speed = (nowX - frontX) / 30;
		y_speed = (nowY - frontY) / 30;
//		System.out.println(y_speed);
		
		stateTime = 0;
		formerStateTime = 0;
	}
	
	public void setGravity(float g){
		Ninja.gravity = g;
	}
	
	public float getX_speed(){
		return x_speed;
	}
	
	public float getY_speed(){
		return y_speed;
	}
	
	public void setX_speed(float speed){
		x_speed = speed;
	}
	
	public void setY_speed(float speed){
		y_speed = speed;
	}

	public float get_x_speed_With_stateTime() {
		return x_speed + stateTime * gravity;
	}
	
	public void resetStateTime(){
		stateTime = 0;
		formerStateTime = 0;
	}
	
	private void iniCollisionPosition(){
		collision[0][0] = x + r;//中心
		collision[0][1] = y + r;
		
		collision[1][0] = x;//极上
		collision[1][1] = collision[0][1];
		
		collision[2][0] = collision[0][0];//极右
		collision[2][1] = y + 2 * r;
		
		collision[3][0] = x + 2 * r;//极下
		collision[3][1] = collision[0][1];
		
		collision[4][0] = collision[0][0];//极左
		collision[4][1] = y;
		
		collision[5][0] = collision[0][0] - 0.7f * r;
		collision[5][1] = collision[0][1] - 0.7f * r;
		
		collision[6][0] = collision[0][0] + 0.7f * r;
		collision[6][1] = collision[0][1] - 0.7f * r;
		
		collision[7][0] = collision[0][0] + 0.7f * r;
		collision[7][1] = collision[0][1] + 0.7f * r;
		
		collision[8][0] = collision[0][0] - 0.7f * r;
		collision[8][1] = collision[0][1] + 0.7f * r;
	}
	
	private void move(float mx, float my){
		x += mx;
		y += my;
		
		reflect();
		
		for(int i = 0; i < 5; i++){
			collision[i][0] += mx;
			collision[i][1] += my;
		}
		
		sprite.setPosition(x, y);
		super.setPosition(x, y);
	}
	
	private void reflect(){
		if(x < 0){
			x = 0;
			x_speed = -get_x_speed_With_stateTime();
			resetStateTime();
		}
		else if(x > GamePlay.width - 2 * r){
			x = GamePlay.width - 2 * r;
			x_speed = -get_x_speed_With_stateTime();
			resetStateTime();
		}
		else if(y < 0){
			y = 0;
			y_speed = -y_speed;
		}
		else if(y > GamePlay.height - 2 * r){
			y = GamePlay.height - 2 * r;
			y_speed = -y_speed;
		}
	}
	
}
