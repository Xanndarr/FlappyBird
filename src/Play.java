package com.Barwick.FlappyBird;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends BasicGameState{
	
	private final int stateID = 0;
	
	private Image[] background = new Image[2];
	private Image currentBackground = null;
	private Image flappyBird = null;
	private Image floorImage = null;
	private Image pipeImageUp = null;
	private Image pipeImageDown = null;
	
	private float height = 100;
	private float velocity = 0.0f;
	private final float gravity = 0.0014f;
	
	private int score = 0;
	
	private Polygon flappyCollisionPolygon = null;
	private Polygon floorPolygon = null;
	
	private ArrayList<Float> pipeXCoords = null;
	private ArrayList<Float> pipeYCoords = null;
	private ArrayList<Polygon> pipeCollisionTop = null;
	private ArrayList<Polygon> pipeCollisionBottom = null;
	private ArrayList<Boolean> scoreChecked = null;
	
	private float floorPos = 0.0f;
	private float floorPos2 = 288;
	private float sideScrollVelocity = 0.1f;
	
	private final Random rand = new Random(System.currentTimeMillis());
	private UnicodeFont flappyFont = null;

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		flappyFont = new UnicodeFont("res/flappy.ttf", 24, false, false);
		flappyFont.getEffects().add(new ColorEffect(java.awt.Color.white));
		flappyFont.addAsciiGlyphs();
		flappyFont.loadGlyphs();
		
		background[0] = new Image("res/bg1.png");
		background[1] = new Image("res/bg2.png");
		currentBackground = background[rand.nextInt(2)];
		
		flappyBird = new Image("res/flappy.png");
		floorImage = new Image("res/floorImage.png");
		pipeImageUp = new Image("res/pipeImageUp.png");
		pipeImageDown = new Image("res/pipeImageDown.png");
		
		pipeXCoords = new ArrayList<Float>(0);
		pipeYCoords = new ArrayList<Float>(0);
		pipeCollisionTop = new ArrayList<Polygon>(0);
		pipeCollisionBottom = new ArrayList<Polygon>(0);
		scoreChecked = new ArrayList<Boolean>(0);
		
		pipeXCoords.add(600f);
		pipeYCoords.add((float)rand.nextInt(250) - 465);
		scoreChecked.add(false);
		pipeCollisionTop.add(new Polygon(new float[]{
			pipeXCoords.get(0), pipeYCoords.get(0),
			pipeXCoords.get(0) + pipeImageDown.getWidth(), pipeYCoords.get(0),
			pipeXCoords.get(0) + pipeImageDown.getWidth(), pipeYCoords.get(0) + pipeImageDown.getHeight(),
			pipeXCoords.get(0), pipeYCoords.get(0) + pipeImageDown.getHeight(),
		}));
		pipeCollisionBottom.add(new Polygon(new float[]{
			pipeXCoords.get(0), pipeYCoords.get(0) + 600,
			pipeXCoords.get(0) + pipeImageUp.getWidth(), pipeYCoords.get(0) + 600,
			pipeXCoords.get(0) + pipeImageUp.getWidth(), pipeYCoords.get(0) + pipeImageUp.getHeight() + 600,
			pipeXCoords.get(0), pipeYCoords.get(0) + pipeImageUp.getHeight() + 600,
		}));
		
		flappyCollisionPolygon = new Polygon(new float[]{
			50, height,
			50 + flappyBird.getWidth(), height,
			50 + flappyBird.getWidth(), height + flappyBird.getHeight(),
			50, height + flappyBird.getHeight(),
		});
		
		floorPolygon = new Polygon(new float[]{
			0, 400,
			288, 400,
			288, 512,
			0, 512,
		});
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setFont(flappyFont);
		g.drawImage(currentBackground, 0, 0);
		g.drawImage(flappyBird, 50, height);
		
		for(int pipeNum = 0; pipeNum < pipeXCoords.size(); pipeNum++){
			g.drawImage(pipeImageDown, pipeXCoords.get(pipeNum), pipeYCoords.get(pipeNum));
			g.drawImage(pipeImageUp, pipeXCoords.get(pipeNum), pipeYCoords.get(pipeNum) + 600);
		}
		
		g.drawImage(floorImage, (int)floorPos, 400);
		g.drawImage(floorImage, (int)floorPos2, 400);
		
		g.setColor(new Color(221, 216, 148));
		g.fillRect(0, 422, 288, 90);
		
		g.setColor(Color.white);
		g.drawString(Integer.toString(score), 5, 5);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if(pipeXCoords.size() > 1){
			if(pipeXCoords.get(pipeXCoords.size() - 2) < 65 && scoreChecked.get(scoreChecked.size() - 2) == false){
				score++;
				scoreChecked.set(scoreChecked.size() - 2, true);
			}
		}
		
		if (pipeXCoords.get(pipeXCoords.size() - 1) < 122) {
			System.out.println("New Pipe");
			pipeXCoords.add(295f);
			pipeYCoords.add((float)rand.nextInt(250) - 465);
			scoreChecked.add(false);
			pipeCollisionTop.add(new Polygon(new float[]{
				pipeXCoords.get(pipeXCoords.size() - 1), pipeYCoords.get(pipeXCoords.size() - 1),
				pipeXCoords.get(pipeXCoords.size() - 1) + pipeImageDown.getWidth(), pipeYCoords.get(pipeXCoords.size() - 1),
				pipeXCoords.get(pipeXCoords.size() - 1) + pipeImageDown.getWidth(), pipeYCoords.get(pipeXCoords.size() - 1) + pipeImageDown.getHeight(),
				pipeXCoords.get(pipeXCoords.size() - 1), pipeYCoords.get(pipeXCoords.size() - 1) + pipeImageDown.getHeight(),
			}));
			pipeCollisionBottom.add(new Polygon(new float[]{
				pipeXCoords.get(pipeXCoords.size() - 1), pipeYCoords.get(pipeXCoords.size() - 1) + 600,
				pipeXCoords.get(pipeXCoords.size() - 1) + pipeImageUp.getWidth(), pipeYCoords.get(pipeXCoords.size() - 1) + 600,
				pipeXCoords.get(pipeXCoords.size() - 1) + pipeImageUp.getWidth(), pipeYCoords.get(pipeXCoords.size() - 1) + pipeImageUp.getHeight() + 600,
				pipeXCoords.get(pipeXCoords.size() - 1), pipeYCoords.get(pipeXCoords.size() - 1) + pipeImageUp.getHeight() + 600,
			}));
		}
		
		flappyCollisionPolygon.setY(height);
		if(!checkCollisions()){
			height += velocity * delta * 2.5;
			
			if(height < -10){
				height++;
				velocity = 0;
			}else if (height >= 380){
				velocity = 0;
			}else{
				velocity += gravity * delta / 3;
			}
			
			floorPos -= sideScrollVelocity * delta;
			floorPos2 -= sideScrollVelocity * delta;
			if(floorPos < -288){
				floorPos = 288;
			}else if (floorPos2 < -288) {
				floorPos2 = 288;
			}
			
			for(int pipeNum = 0; pipeNum < pipeXCoords.size(); pipeNum++){
				pipeXCoords.set(pipeNum, pipeXCoords.get(pipeNum) - sideScrollVelocity * delta);
				if(pipeXCoords.get(pipeNum) < -47){
					pipeXCoords.remove(pipeNum);
					pipeYCoords.remove(pipeNum);
					pipeCollisionBottom.remove(pipeNum);
					pipeCollisionTop.remove(pipeNum);
					scoreChecked.remove(pipeNum);
				}
				pipeCollisionBottom.get(pipeNum).setX(pipeXCoords.get(pipeNum));
				pipeCollisionTop.get(pipeNum).setX(pipeXCoords.get(pipeNum));
			}
			if(input.isKeyPressed(Input.KEY_SPACE)){
				velocity = -0.14f;
			}
		}else if(input.isKeyPressed(Input.KEY_ENTER)){
			reset();
			System.out.println("Test");
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}
	}
	
	private boolean checkCollisions(){
		if(flappyCollisionPolygon.intersects(floorPolygon)){
			//System.out.println("Collide with floor");
			return true;
		}
		for(int pipeNum = 0; pipeNum < pipeXCoords.size(); pipeNum++){
			if(flappyCollisionPolygon.intersects(pipeCollisionBottom.get(pipeNum)) || flappyCollisionPolygon.intersects(pipeCollisionTop.get(pipeNum))){
				System.out.println("Collide with pipe");
				return true;
			}
		}
		return false;
	}
	
	private void reset(){
		currentBackground = background[rand.nextInt(2)];
		pipeXCoords = new ArrayList<Float>(0);
		pipeYCoords = new ArrayList<Float>(0);
		pipeCollisionTop = new ArrayList<Polygon>(0);
		pipeCollisionBottom = new ArrayList<Polygon>(0);
		scoreChecked = new ArrayList<Boolean>(0);
		
		pipeXCoords.add(600f);
		pipeYCoords.add((float)rand.nextInt(250) - 465);
		scoreChecked.add(false);
		pipeCollisionTop.add(new Polygon(new float[]{
			pipeXCoords.get(0), pipeYCoords.get(0),
			pipeXCoords.get(0) + pipeImageDown.getWidth(), pipeYCoords.get(0),
			pipeXCoords.get(0) + pipeImageDown.getWidth(), pipeYCoords.get(0) + pipeImageDown.getHeight(),
			pipeXCoords.get(0), pipeYCoords.get(0) + pipeImageDown.getHeight(),
		}));
		pipeCollisionBottom.add(new Polygon(new float[]{
			pipeXCoords.get(0), pipeYCoords.get(0) + 600,
			pipeXCoords.get(0) + pipeImageUp.getWidth(), pipeYCoords.get(0) + 600,
			pipeXCoords.get(0) + pipeImageUp.getWidth(), pipeYCoords.get(0) + pipeImageUp.getHeight() + 600,
			pipeXCoords.get(0), pipeYCoords.get(0) + pipeImageUp.getHeight() + 600,
		}));
		
		flappyCollisionPolygon = new Polygon(new float[]{
			50, height,
			50 + flappyBird.getWidth(), height,
			50 + flappyBird.getWidth(), height + flappyBird.getHeight(),
			50, height + flappyBird.getHeight(),
		});
		height = 100;
		velocity = 0;
		score = 0;
	}

	@Override
	public int getID() {
		return stateID;
	}

}
