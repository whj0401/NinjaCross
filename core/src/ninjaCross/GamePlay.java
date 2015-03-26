package ninjaCross;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GamePlay extends ApplicationAdapter {
	static int width, height;//屏幕长宽
	static int diameter_ball;//球直径
	static int width_bottle, height_bottle;//罐子长宽
	
	static float stepTime = 0.025f;
	static float stateTime = 0;
	
	static int score = 0;
	
	static int condition = 0;//0为等待开始状态，1为开始游戏状态，2为游戏结束状态
	
	static BitmapFont font;
	static SpriteBatch batch;
	
	InputMultiplexer multiplexer;
	static TouchPointer pointer;
	Stage gameStage;
	
	Line line;
	static Ninja ball;
	static int broken = 3;//记录可以碰撞的次数
	final static int brokenIni = 3;
	
	Bottle[] bottle;
	@Override
	public void create () {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		diameter_ball = height / 4;
		
		width_bottle = height / 4;
		height_bottle = width_bottle / 2;
		
		font = new BitmapFont(Gdx.files.internal("game.fnt"), Gdx.files.internal("game.png"), false);
		batch = new SpriteBatch();
		
		gameStage = new Stage();
		
		gameStage.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				return true;
			}
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				if(condition == 0){
					condition = 1;
				}
				else if(condition == 2){
					condition = 0;
					broken = brokenIni;
				}
				
			}
		});
		
		multiplexer = new InputMultiplexer();
		pointer = new TouchPointer(-1000, -1000);
		multiplexer.addProcessor(pointer);
		multiplexer.addProcessor(gameStage);
		Gdx.input.setInputProcessor(multiplexer);
		
		line = new Line("blue-pointer.png");
		
		ball = new Ninja(diameter_ball, diameter_ball);
		ball.setOriginPosition(width / 2, height / 2);
//		ball.setX_gravity(width / 50);
		
		gameStage.addActor(ball);
		
//		Bottle.setX_gravity(width / 50);
		
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
		
		if(condition == 1){
			ball.setX_gravity(Gdx.input.getAccelerometerY() * width / 100);
			ball.setY_gravity(-Gdx.input.getAccelerometerX() * height / 100);
		}
		
		
//		Bottle.setX_gravity(Gdx.input.getAccelerometerY() * width / 100);
//		Bottle.setY_gravity(-Gdx.input.getAccelerometerX() * height / 100);
		
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
		
		if(condition == 0){
			batch.begin();
			font.drawMultiLine(batch, "GAME\nSTART", (width/2-diameter_ball), (height-diameter_ball)/2);
			batch.end();
		}
		else if(condition == 1){
			for(int p = 0; p <= 8; p++){
				Actor temp = gameStage.hit(ball.collision[p][0], ball.collision[p][1], true);
				if(temp instanceof Ninja) return;
				if(temp instanceof Bottle){
					Bottle t = (Bottle)temp;
					crash(ball, t, p);
					broken--;
					if(broken == 0){
						condition = 2;
					}
					return;
				}
			}
		}
		else if(condition == 2){
			batch.begin();
			font.drawMultiLine(batch, "GAME\nOVER", (width/2-diameter_ball), (height-diameter_ball)/2);
			batch.end();
		}
	}
	
	public static void initialNinja(){
		ball.setX_gravity(width / 50);
	}
	
	public void crash(Ninja ball, Bottle bottle, int p){//1极上，2极右，3极下，4极左
		float temp;
		
		if(p == 0 || p > 4){
			temp = ball.get_x_speed_With_stateTime();
			ball.setX_speed(bottle.get_x_speed_With_stateTime() / 5);
			bottle.setX_speed((temp-bottle.get_x_speed_With_stateTime())*2);
			temp = ball.get_y_speed_With_stateTime();
			ball.setY_speed(bottle.get_y_speed_With_stateTime() / 5);
			bottle.setY_speed((temp-bottle.get_y_speed_With_stateTime())*2);
		}
		else if(p == 1 || p == 3){
			temp = ball.get_x_speed_With_stateTime();
			ball.setX_speed(bottle.get_x_speed_With_stateTime() / 5);
			bottle.setX_speed((temp-bottle.get_x_speed_With_stateTime())*2);
		}
		else if(p == 2 || p == 4){
			temp = ball.get_y_speed_With_stateTime();
			ball.setY_speed(bottle.get_y_speed_With_stateTime() / 5);
			bottle.setY_speed((temp-bottle.get_y_speed_With_stateTime())*2);
		}
		ball.resetStateTime();
		bottle.resetStateTime();
		gameStage.draw();
	}
}
