package ninjaCross;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GamePlay extends ApplicationAdapter {
	static int width, height;//屏幕长宽
	static int diameter_ball;//球直径
	static int width_bottle, height_bottle;//罐子长宽
	
	static float stepTime = 0.025f;
	static float stateTime = 0;
	
	static int score = 0;
	
	InputMultiplexer multiplexer;
	static TouchPointer pointer;
	Stage gameStage;
	
	Line line;
	static Ninja ball;
	
	
	Bottle[] bottle;
	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		diameter_ball = height / 5;
		
		width_bottle = height / 4;
		height_bottle = width_bottle / 2;
		
		gameStage = new Stage();
		
		multiplexer = new InputMultiplexer();
		pointer = new TouchPointer(-1000, -1000);
		multiplexer.addProcessor(pointer);
		multiplexer.addProcessor(gameStage);
		Gdx.input.setInputProcessor(multiplexer);
		
		line = new Line("blue-pointer.png");
		
		ball = new Ninja(diameter_ball, diameter_ball);
		ball.setOriginPosition(width / 10, height / 2);
		ball.setGravity(width / 50);
		
		gameStage.addActor(ball);
		
		Bottle.setGravity(width / 50);
		
		bottle = new Bottle[5];
		for(int i = 0; i < 5; i++){
			bottle[i] = new Bottle(width_bottle, height_bottle);
			bottle[i].reset();
			gameStage.addActor(bottle[i]);
		}
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gameStage.draw();
		
		line.render(pointer.getDownX(), pointer.getDownY(), pointer.getNowX(), pointer.getNowY(), ball.DOWN);
//		if(ball.isTouched(pointer.getDownX(), pointer.getDownY())){
//			line.render(pointer.getDownX(), pointer.getDownY(), pointer.getNowX(), pointer.getNowY(), pointer.DOWN);
//			if(!line.DRAWING){
//				ball.setOriginPosition(pointer.getNowX(), pointer.getNowY());
//				ball.calculateDefaultSpeed(pointer.getDownX(), pointer.getDownY(), pointer.getNowX(), pointer.getNowY());
//				pointer.initialPosition();
//			}
//		}
//		else if(!line.DRAWING){
//			line.render(pointer.getDownX(), pointer.getDownY(), pointer.getNowX(), pointer.getNowY(), pointer.DOWN);
//		}
//		ball.render();
		
		for(int i = 0; i < 5; i++){
			if(bottle[i].isOutOfSight()){
				bottle[i].reset();
			}
		}
		
		for(int p = 0; p <= 8; p++){
			Actor temp = gameStage.hit(ball.collision[p][0], ball.collision[p][1], true);
			if(temp instanceof Ninja) return;
			if(temp instanceof Bottle){
				Bottle t = (Bottle)temp;
				crash(ball, t, p);
				return;
			}
		}
		
	}
	
	public static void initialNinja(){
		ball.setGravity(width / 50);
	}
	
	public void crash(Ninja ball, Bottle bottle, int p){//1极上，2极右，3极下，4极左
		float temp;
		
		if(p == 0 || p > 4){
			temp = ball.get_x_speed_With_stateTime();
			ball.setX_speed(bottle.get_x_speed_With_stateTime() / 5);
			bottle.setX_speed(temp * 5);
			temp = ball.getY_speed();
			ball.setY_speed(bottle.getY_speed() / 5);
			bottle.setY_speed(temp * 5);
		}
		else if(p == 1 || p == 3){
			temp = ball.get_x_speed_With_stateTime();
			ball.setX_speed(bottle.get_x_speed_With_stateTime() / 5);
			bottle.setX_speed(temp * 5);
		}
		else if(p == 2 || p == 4){
			temp = ball.getY_speed();
			ball.setY_speed(bottle.getY_speed() / 5);
			bottle.setY_speed(temp * 5);
		}
		ball.resetStateTime();
		bottle.resetStateTime();
		gameStage.draw();
	}
}
