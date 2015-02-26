package ninjaCross;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Line {
	
	private Texture texture;
	private TextureRegion img;
	private Sprite sprite;
	private SpriteBatch batch;
	
	public boolean DRAWING = false;
	
	int fX = 0, fY = 0, rX = 0, rY = 0;
	
	float width = 0, height = 0;
	
	float degree = 0;
	
	float opacity = 0;
	
	float stateTime = 0;
	float dragTime = 0;
	static float dragMaxTime = 0.2f;
	static float stepTime = 0.0625f;
	
	public Line(String PicPath){
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal(PicPath));
		img = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
		sprite = new Sprite(img);
		sprite.setSize(width, height);
	}
	
	public void render(int frontX, int frontY, int realX, int realY, boolean isDown){
		if(!isDown){
			dragTime = 0;
			DRAWING = false;
		}
		if(isDown && dragTime < dragMaxTime){
			DRAWING = true;
			dragTime += Gdx.graphics.getDeltaTime();
			fX = frontX;
			fY = frontY;
			rX = realX;
			rY = realY;
			
			this.setImgBounds();
			sprite.setSize(width, height);
			sprite.setOrigin(0, 0);
			sprite.setPosition(frontX, frontY);
			opacity = 1;
			stateTime = 0;
			sprite.setColor(1, 1, 1, 1);
			if(rX != fX){
				degree = (float) Math.abs((Math.asin((rY - fY) / width) / Math.PI * 180));
				if(rY > fY && rX < fX){
					degree = 180 - degree;
				}
				else if(rY < fY && rX < fX){
					degree += 180;
				}
				else if(rY < fY && rX > fX){
					degree = 360 - degree;
				}
			}
			else{
				if(rY > fY)
					degree = 90;
				else
					degree = 270;
			}
			sprite.rotate(degree);
			batch.begin();
			sprite.draw(batch);
			batch.end();
			sprite.rotate(-degree);
		}
		else{
			DRAWING = false;
			stateTime += Gdx.graphics.getDeltaTime();
			int d = (int) (stateTime / stepTime);
			if(d > 8) return;
			d = 8 - d;
			sprite.setColor(1, 1, 1, 0.125f*d);
			sprite.rotate(degree);
			batch.begin();
			sprite.draw(batch);
			batch.end();
			sprite.rotate(-degree);
		}
	}
	
	private void setImgBounds(){
		width = (float) Math.sqrt((rX - fX) * (rX - fX) + (rY - fY) * (rY - fY));
		height = width / 20;
	}
}
