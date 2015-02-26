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
	
	static float gravity = 0;//ÖØÁ¦
	
	private float xSpeed, ySpeed = 0;
	
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
			x += get_x_speed_With_stateTime();
			y += ySpeed;
			this.setPosition(x, y);
			sprite.setPosition(x, y);
		}
		sprite.draw(batch);
	}
	
	public boolean isHit(float x, float y){
		Actor temp = this.hit(x, y, true);
		if(temp == null) return false;
		
		return true;
	}
	
	public static void setGravity(float g){
		gravity = g;
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
		switch(t){
		case 0:
			x = -width;
			y = (float) (-Math.random() * GamePlay.height);
			xSpeed = (float) (Math.random() * 5) * gravity;
			ySpeed = (float) (Math.random() * 0.5) * gravity;
			break;
		case 1:
			x = (float) (-Math.random() * GamePlay.width);
			y = GamePlay.height + height;
			xSpeed = (float) (Math.random() * 5) * gravity;
			ySpeed = -(float) (Math.random() * 5) * gravity;
			break;
		case 2:
			x = GamePlay.width + width;
			y = (float) (Math.random() * GamePlay.height);
			xSpeed = (float) (-Math.random() * 5) * gravity;
			ySpeed = (float) (Math.random() * 0.5) * gravity;
			break;
		case 3:
			x = (float) (Math.random() * GamePlay.width);
			y = -height;
			xSpeed = (float) (-Math.random() * 2) * gravity;
			ySpeed = (float) (Math.random() * 2) * gravity;
			break;
		}
	}
	
	public float getX_speed(){
		return xSpeed;
	}
	
	public float getY_speed(){
		return ySpeed;
	}
	
	public void setX_speed(float speed){
		xSpeed = speed;
	}
	
	public void setY_speed(float speed){
		ySpeed = speed;
	}
	
	public float get_x_speed_With_stateTime(){
		return xSpeed + stateTime * gravity;
	}
	
	public void resetStateTime(){
		stateTime = 0;
		formerStateTime = 0;
	}
	
}
