package com.Barwick.FlappyBird;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class FlappyBird extends StateBasedGame{
	
	private final static String NAME = "Flappy Bird";
	private final static int WIDTH = 288;
	private final static int HEIGHT = 512;
	
	private final String[] iconRefs = {"res/icon32.png", "res/icon16.png"};
	
	public static void main(String[] args) {
		try{
			AppGameContainer app = new AppGameContainer(new FlappyBird(NAME));
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}

	public FlappyBird(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setShowFPS(false);
		gc.setIcons(iconRefs);
		gc.setTargetFrameRate(120);
		addState(new Play());
	}

}
