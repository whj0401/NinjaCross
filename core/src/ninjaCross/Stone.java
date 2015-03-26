package ninjaCross;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Stone extends Actor{
	private String img = "stone";
	private String last = ".png";
	
	Texture texture;
	TextureRegion region;
	
	SpriteBatch batch;
	Sprite sprite;
	
	float rotate = 20;
	int rotateTimes = 0;
	
	private static float x_gravity = 0;//重力
	private static float y_gravity = 0;
	
	private float x_speed, y_speed = 0;
	
	private float width, height;
	
	private float x, y;
	
	private float stateTime = 0;
	private float formerStateTime = 0;
	
	static float stepTime = 0.025f;
	
	public Stone(float width, float height, String imgName){
		this.width = width;
		this.height = height;
		
		img = imgName;
		
		texture = new Texture(img+last);
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
			this.rotateBy(rotate * ++rotateTimes);//TODO 旋转
			sprite.rotate(rotate);
			//TODO 速度加上加速度
			x_speed += x_gravity;
			y_speed += y_gravity;
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
