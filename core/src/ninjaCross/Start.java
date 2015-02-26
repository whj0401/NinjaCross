package ninjaCross;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Start extends ApplicationAdapter {
	static int width, height;//ÆÁÄ»³¤¿í
	static int diameter_ball;//ÇòÖ±¾¶
	static int width_bottle, height_bottle;//¹Þ×Ó³¤¿í
	
	static float stepTime = 0.025f;
	static float stateTime = 0;
	
	static int score = 0;
	
	InputMultiplexer multiplexer;
	TouchPointer pointer;
	Stage stage;
	
	Line line;
	Ball ball;
	Bottle bottle;
	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		diameter_ball = height / 5;
		
		width_bottle = height / 4;
		height_bottle = width_bottle / 2;
		
		stage = new Stage();
		
		multiplexer = new InputMultiplexer();
		pointer = new TouchPointer(-1000, -1000);
		multiplexer.addProcessor(pointer);
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);
		
		line = new Line("blue-pointer.png");
		
		ball = new Ball(diameter_ball, diameter_ball);
		ball.setPosition(width / 10, height / 2);
		ball.setGravity(width / 50);
		
		bottle = new Bottle(width_bottle, height_bottle);
		stage.addActor(bottle);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
		
		if(ball.isTouched(pointer.getDownX(), pointer.getDownY())){
			line.render(pointer.getDownX(), pointer.getDownY(), pointer.getNowX(), pointer.getNowY(), pointer.DOWN);
			if(!line.DRAWING){
				ball.setOriginPosition(pointer.getNowX(), pointer.getNowY());
				ball.calculateDefaultSpeed(pointer.getDownX(), pointer.getDownY(), pointer.getNowX(), pointer.getNowY());
				pointer.initialPosition();
			}
		}
		else if(!line.DRAWING){
			line.render(pointer.getDownX(), pointer.getDownY(), pointer.getNowX(), pointer.getNowY(), pointer.DOWN);
		}
		ball.render();
		
		for(;line.DRAWING;){
			Actor temp = stage.hit(pointer.getNowX(), pointer.getNowY(), true);
			if(temp == null) break;
			stage.getRoot().removeActor(temp);
			score++;
		}
	}
}
