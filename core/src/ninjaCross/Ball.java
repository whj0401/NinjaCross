package ninjaCross;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ball {
	Texture texture;
	TextureRegion region;
	SpriteBatch batch;
	Sprite sprite;
	
	float width, height;
	
	float y, x;//这里x, y需要反过来理解,x是高度上的，y是宽度上的，竖屏
	
	//坐标系顺时针旋转90度
	
	float y_speed, x_speed;
	
	float stateTime = 0;
	float formerStateTime = 0;
	static float stepTime = 0.025f;
	
	static float rotate = -20;
	static float gravity = 0;//重力
	
	float upW, doW, upH, doH;
	
	public Ball(float width, float height){
		this.width = width;
		this.height = height;
		
		texture = new Texture(Gdx.files.internal("ball.png"));
		region = new TextureRegion(texture);
		
		batch = new SpriteBatch();
		sprite = new Sprite(region);
		
		sprite.setSize(width, height);
		sprite.setOrigin(width / 2, height / 2);
		sprite.setColor(1, 1, 1, 1);
		
		upW = 1.5f * width;
		upH = 1.5f * height;
		doW = -width / 2;
		doH = -height / 2;
	}
	
	public void setPosition(int x, int y){
		this.y = y;
		this.x = x;
	}
	
	public void setOriginPosition(int x, int y){
		this.y = y - width / 2;
		this.x = x - height / 2;
		stateTime = 0;
		formerStateTime = 0;
	}
	
	public void setGravity(int gravity){
		this.gravity = gravity;
	}
	
//	public void calculateDefaultSpeed(float length, float degree){
//		x_speed = (float) (length * Math.cos(degree)) / 50;
//		y_speed = (float) (length * Math.sin(degree)) / 50;
//		
//		stateTime = 0;
//		formerStateTime = 0;
//	}
	
	private float get_x_speed_With_stateTime(){
		return x_speed + stateTime * gravity;
	}
	
	public void render(){
		stateTime += Gdx.graphics.getDeltaTime();
		if(stateTime - formerStateTime > stepTime){
			formerStateTime = stateTime;
			sprite.rotate(rotate);
			x += get_x_speed_With_stateTime();
			sprite.setPosition(x, y);
		}
		batch.begin();
		sprite.draw(batch);
		batch.end();
	}
	
	public boolean isTouched(int x, int y){
		if(x - this.x < upH && x - this.x > doH && 
				y - this.y < upW && y - this.y > doW) 
			return true;
		return false;
	}
}
