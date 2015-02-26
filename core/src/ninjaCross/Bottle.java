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
	
	static float gravity = 1;//ÖØÁ¦
	
	float xSpeed, ySpeed = 1;
	
	float width, height;
	
	float x, y;
	
	float stateTime = 0;
	float formerStateTime = 0;
	
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
		
//		this.addListener(new InputListener(){
//			@Override
//			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//				sprite.setColor(0, 0, 0, 0);
//				System.out.println("touchDown");
//				return true;
//			}
//			@Override
//	        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//				sprite.setColor(0, 0, 0, 0);
//				System.out.println("touchUp");
//	        }
//			@Override
//			public void touchDragged(InputEvent event, float x, float y, int pointer){
//				sprite.setColor(0, 0, 0, 0);
//				System.out.println("touchDragged");
//			}
//			@Override
//			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor){
//				sprite.setColor(0, 0, 0, 0);
//				System.out.println("enter");
//			}
//			@Override
//			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor){
//				sprite.setColor(0, 0, 0, 0);
//				System.out.println("exit");
//			}
//			@Override
//			public boolean scrolled(InputEvent event, float x, float y, int amount){
//				sprite.setColor(0, 0, 0, 0);
//				System.out.println("scrolled");
//				return true;
//			}
//		});
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		stateTime += Gdx.graphics.getDeltaTime();
		if(stateTime - formerStateTime > stepTime){
			formerStateTime = stateTime;
			this.rotateBy(rotate * rotateTimes++);
			sprite.rotate(rotate);
			x += get_x_speed_With_stateTime();
			y += ySpeed;
			this.setPosition(x, y);
			sprite.setPosition(x, y);
		}
		sprite.draw(batch);
	}
	
//	public void render(){
//		stateTime += Gdx.graphics.getDeltaTime();
//		if(stateTime - formerStateTime > stepTime){
//			formerStateTime = stateTime;
//			sprite.rotate(rotate);
//			this.rotateBy(rotate);
//			x += get_x_speed_With_stateTime();
//			y += ySpeed;
//			sprite.setPosition(x, y);
//			this.setPosition(x, y);
//		}
//		batch.begin();
//		sprite.draw(batch);
//		batch.end();
//	}
	
	public boolean isHit(float x, float y){
		Actor temp = this.hit(x, y, true);
		if(temp == null) return false;
		
		return true;
	}
	
	private float get_x_speed_With_stateTime(){
		return xSpeed + stateTime * gravity;
	}
	
}
