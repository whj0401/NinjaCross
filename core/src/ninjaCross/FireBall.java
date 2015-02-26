package ninjaCross;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class FireBall {
	Texture[] texture;
	TextureRegion[] region;
	Animation animation;
	SpriteBatch batch;
	Sprite sprite;
	
	TextureRegion current;
	
	float width, height;
	
	float y, x;//这里x, y需要反过来理解,x是高度上的，y是宽度上的，竖屏
	
	//坐标系顺时针旋转90度
	
	float y_speed, x_speed;
	
	float stateTime = 0;
	float formerStateTime = 0;
	static float stepTime = 0.025f;
	
	static float gravity = 0;//重力
	
	float upW, doW, upH, doH;
	
	float rotate = 180;
	
	public FireBall(float width, float height){
		this.width = width;
		this.height = height;
		
		texture = new Texture[10];
		region = new TextureRegion[10];
		for(int i = 0; i < 10; i++){
			texture[i] = new Texture(Gdx.files.internal("fireball" + (i+1) + ".png"));
			region[i] = new TextureRegion(texture[i]);
		}
		animation = new Animation(stepTime, region);
		
		batch = new SpriteBatch();
		sprite = new Sprite();
		
		sprite.setSize(width * 1.5f, height);
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
	
	private float get_x_speed_With_stateTime(){
		return x_speed + stateTime * gravity;
	}
	
	public void render(){
		stateTime += Gdx.graphics.getDeltaTime();
		current = animation.getKeyFrame(stateTime, true);
		sprite.setRegion(current);
		if(stateTime - formerStateTime > stepTime){
			formerStateTime = stateTime;
			x += get_x_speed_With_stateTime();
			sprite.setPosition(x, y);
			
		}
		sprite.rotate(-rotate);
		batch.begin();
		sprite.draw(batch);
		batch.end();
		sprite.rotate(rotate);
	}
	
	public boolean isTouched(int x, int y){
		if(x - this.x < upH && x - this.x > doH && 
				y - this.y < upW && y - this.y > doW) 
			return true;
		return false;
	}
}
