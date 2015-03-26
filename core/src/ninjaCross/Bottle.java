package ninjaCross;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Bottle extends Actor{
	private String img = "bottle";
	private String last = ".png";
	
	Texture texture;
	TextureRegion region;
	
	SpriteBatch batch;
	Sprite sprite;
	
	static float rotate = 20;
	int rotateTimes = 0;
	
	private static float x_gravity = 0;//ÖØÁ¦
	private static float y_gravity = 0;
	
	private float x_speed, y_speed = 0;
	
	private float width, height;
	
	private float x, y;
	
	private float stateTime = 0;
	private float formerStateTime = 0;
	
	static float stepTime = 0.025f;
	
	public Bottle(float width, float height){
		this.width = width;
		this.height = height;
		
		int index = (int) (Math.random() * 4 + 1);
		
		texture = new Texture(img + index + last);
		region = new TextureRegion(texture);
		
		batch = new SpriteBatch();
		sprite = new Sprite(texture);
		
		sprite.setSize(width, height);
		sprite.setOrigin(width / 2, height / 2);
		
		this.setSize(width, height);
		this.setPosition(x, y);
		this.setOrigin(width / 2, height / 2);
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		stateTime += Gdx.graphics.getDeltaTime();
		if(stateTime - formerStateTime > stepTime){
			formerStateTime = stateTime;
			this.rotateBy(rotate * ++rotateTimes);
			sprite.rotate(rotate);
//			x_speed += x_gravity;
//			y_speed += y_gravity;
			move(x_speed, y_speed);
		}
		sprite.draw(batch);
	}
	
	public boolean isHit(float x, float y){
		Actor temp = this.hit(x, y, true);
		if(temp == null) return false;
		
		return true;
	}
	
	public static void setX_gravity(float g){
		x_gravity = g * stepTime;
	}
	
	public static void setY_gravity(float g){
		y_gravity = g * stepTime;
	}
	
	public boolean isOutOfSight(){
		if(x > GamePlay.width + 2 * width || x < -2 * width || 
				y > GamePlay.height + 2 * width || y < - 2 * width) 
			return true;
		return false;
	}
	
	public void reset(){
		stateTime = 0;
		formerStateTime = 0;
		int t = (int) (Math.random() * 4);
		int choiceSpeed = (int)(Math.random() * 5) + 1;
		switch(t){
		case 0:
			x = -width;
			y = (float) (-Math.random() * GamePlay.height);
//			x_speed = (float) (Math.random() * 5) * x_gravity / stepTime;
//			y_speed = (float) (Math.random() * 0.5) * y_gravity / stepTime;
			x_speed = (GamePlay.ball.collision[0][0] - x) * stepTime * choiceSpeed;
			y_speed = (GamePlay.ball.collision[0][1] - y) * stepTime * choiceSpeed;
			break;
		case 1:
			x = (float) (-Math.random() * GamePlay.width);
			y = GamePlay.height + height;
//			x_speed = (float) (Math.random() * 5) * x_gravity / stepTime;
//			y_speed = -(float) (Math.random() * 5) * y_gravity / stepTime;
			x_speed = (GamePlay.ball.collision[0][0] - x) * stepTime * choiceSpeed;
			y_speed = (GamePlay.ball.collision[0][1] - y) * stepTime * choiceSpeed;
			break;
		case 2:
			x = GamePlay.width + width;
			y = (float) (Math.random() * GamePlay.height);
//			x_speed = (float) (-Math.random() * 5) * x_gravity / stepTime;
//			y_speed = (float) (Math.random() * 0.5) * y_gravity / stepTime;
			x_speed = (GamePlay.ball.collision[0][0] - x) * stepTime * choiceSpeed;
			y_speed = (GamePlay.ball.collision[0][1] - y) * stepTime * choiceSpeed;
			break;
		case 3:
			x = (float) (Math.random() * GamePlay.width);
			y = -height;
//			x_speed = (float) (-Math.random() * 2) * x_gravity / stepTime;
//			y_speed = (float) (Math.random() * 2) * y_gravity / stepTime;
			x_speed = (GamePlay.ball.collision[0][0] - x) * stepTime * choiceSpeed;
			y_speed = (GamePlay.ball.collision[0][1] - y) * stepTime * choiceSpeed;
			break;
		}
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
		return x_speed + stateTime * x_gravity;
	}
	
	public float get_y_speed_With_stateTime(){
		return y_speed + stateTime * y_gravity;
	}
	
	public void resetStateTime(){
		stateTime = 0;
		formerStateTime = 0;
	}
	
	private void move(float mx, float my){
		x += mx;
		y += my;
		sprite.setPosition(x, y);
		super.setPosition(x, y);
	}
	
}
