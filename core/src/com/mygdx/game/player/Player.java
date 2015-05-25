package com.mygdx.game.player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.projectile.Projectile;

public class Player extends Character {

	private String projectile;
	
	private int playerLevel = 1;
	
	
	public Player(String file, String proj) {
		super(new Array<AtlasRegion>(new AtlasRegion[] { // up animation new Array
				new TextureAtlas(Gdx.files.internal(file)).findRegion("0"),
				new TextureAtlas(Gdx.files.internal(file)).findRegion("1") }),
				new Array<AtlasRegion>( // right animation new Array
						new AtlasRegion[] {
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("2"),
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("3") }),
				new Array<AtlasRegion>( // down animation new Array
						new AtlasRegion[] {
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("4"),
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("5") }),
				new Array<AtlasRegion>( // left animation new Array
						new AtlasRegion[] {
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("6"),
								new TextureAtlas(Gdx.files.internal(file))
										.findRegion("7") }));
		setSpeed(5);
		setExpToLevel( 100 );
		projectile = proj;
	}
	
	@Override
	public void drawBars( Batch batch, ShapeRenderer renderer )
	{
	    super.drawBars( batch, renderer );
	    getFont().setColor( Color.CYAN );
	    getFont().draw( batch, "Level " + playerLevel, getX(), getY() + getHeight() );
	}

    @Override
    public void move( Character character, 
                      ArrayList<Projectile> projectiles, 
                      long time )
    {
        int dir = getInputDirection();
        if (dir != -1) {
            move(dir);
        }
        
        if (Gdx.input.isButtonPressed( Input.Buttons.LEFT ))
        {
            this.shoot(projectiles, 
                Gdx.input.getX() - Gdx.graphics.getWidth() / 2, 
                Gdx.graphics.getHeight() / 2 - Gdx.input.getY(), 
                System.currentTimeMillis(), projectile );
        }
    }
    
    public int getCurrentLevel()
    {
    	return playerLevel;
    }
    
    public void increaseCurrentLevel()
    {
    	// might need balancing
    	playerLevel++;
    	Sound sound = Gdx.audio.newSound( Gdx.files.internal( "audio/sound/levelup.wav" ) );
		sound.play();
		double temp = getCurrentHp() / getMaxHp();
		increaseBdmg( 2 );
		increaseMaxHp( 10 );
		increaseCurrentHp( (int) ( 10 * temp ) );
    }
    
    public void increaseExp( int exp )
    {
        this.setExp( getExp() + exp );
        if ( getExp() >= getExpToLevel() )
        {
            setExp( getExp() - getExpToLevel() );
            increaseCurrentLevel();
        }
    }

	/**
	 * Returns direction to go based on key input
	 * @return direction or -1 if no direction
	 */
	private int getInputDirection() {
        int dirY = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            dirY = NORTH;
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            dirY = ( dirY == NORTH ) ? -1 : SOUTH;
        
	    int dirX = -1;
	    if (Gdx.input.isKeyPressed(Input.Keys.D))
	        dirX = EAST;
	    if (Gdx.input.isKeyPressed(Input.Keys.A))
	        dirX = ( dirX == EAST ) ? -1 : WEST;
	    
	    if ( dirY == -1 )
	        return dirX;
	    if ( dirX == -1 )
	        return dirY;
	    if ( dirY == NORTH && dirX == WEST )
	        return NORTHWEST;
		return ( dirY + dirX ) / 2;
	}

}
