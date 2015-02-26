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
	
	float[][] collision;
	
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
		
		collision = new float[5][2];
	}
	
	public void setPosition(int x, int y){
		this.y = y;
		this.x = x;
		this.iniCollisionPosition();
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
	
	public void calculateDefaultSpeed(float frontX, float frontY, float nowX, float nowY){
		x_speed = (nowX - frontX) / 30;
		y_speed = (nowY - frontY) / 30;
		
		stateTime = 0;
		formerStateTime = 0;
	}
	
	public float get_x_speed_With_stateTime(){
		return x_speed + stateTime * gravity;
	}
	
	public void render(){
		stateTime += Gdx.graphics.getDeltaTime();
		if(stateTime - formerStateTime > stepTime){
			formerStateTime = stateTime;
			sprite.rotate(rotate);
			y += y_speed;
			x += get_x_speed_With_stateTime();
			sprite.setPosition(x, y);
			this.iniCollisionPosition();
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

	public void setX_speed(float f) {
		x_speed = f;
	}

	public float getY_speed() {
		return y_speed;
	}

	public void setY_speed(float f) {
		y_speed = f;
	}

	public void resetStateTime() {
		stateTime = 0;
		formerStateTime = 0;
	}
	
	private void iniCollisionPosition(){
		collision[0][0] = x + width / 2;//中心
		collision[0][1] = y + height / 2;
		
		collision[1][0] = x;//极上
		collision[1][1] = collision[0][1];
		
		collision[2][0] = collision[0][0];//极右
		collision[2][1] = y + height;
		
		collision[3][0] = x + this.width;//极下
		collision[3][1] = collision[0][1];
		
		collision[4][0] = collision[0][0];//极左
		collision[4][1] = y;
	}
}
