package com.mygdx.game.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.projectile.Projectile;

public abstract class Character extends Sprite
{
	public static final int NORTH = 0;
	public static final int NORTHEAST = 1;
	public static final int EAST = 2;
	public static final int SOUTHEAST = 3;
	public static final int SOUTH = 4;
	public static final int SOUTHWEST = 5;
	public static final int WEST = 6;
	public static final int NORTHWEST = 7;
	public static final int NUMDIRECTIONS = 8;

	private int maxHp; // max health
	private int currentHp; // current health
	private int baseDmg; // base damage
	private int randMod; // random damage modifier
	private int direction = -1;
	private int reloadSpeed = 20;
	private int shotCounter = reloadSpeed;

	private TextureAtlas textureAtlas;

	/**
	 * TODO: animations, sprites, coordinates
	 * */
	public Character(String filePath, String frame)
	{
		textureAtlas = new TextureAtlas( Gdx.files.internal( filePath ) );
		set( new Sprite( textureAtlas.findRegion( frame ) ) );

	}

	abstract void move();

	abstract void strafe();

	public void increaseMaxHp(int i)
	{
		maxHp += i;
	}

	public void increaseCurrentHp(int i)
	{
		if ( currentHp + i > maxHp )
		{
			currentHp = maxHp;
		} else
		{
			currentHp += i;
		}
	}

	public void increaseBdmg(int i)
	{
		baseDmg += i;
	}

	public void setDirection(int dir)
	{
		if ( dir >= 0 || dir < NUMDIRECTIONS )
		{
			direction = dir;
		}
	}
	
	public void setReloadSpeed(int newReloadSpeed)
	{
		reloadSpeed = newReloadSpeed;
	}

	public void takeDamage(int damage)
	{
		currentHp -= damage;
		if ( currentHp <= 0 )
		{
			die();
		}
	}

	abstract void die();

	public Projectile shoot(float xDistance, float yDistance)
	{
		if (shotCounter >= reloadSpeed)
		{
			shotCounter = 0;
			return new Projectile( getDirection(), getDamage(), "fireball", xDistance, yDistance );
		}
		else
		{
			shotCounter++;
			return null;
		}
	}
	
	public int getReloadSpeed()
	{
		return reloadSpeed;
	}

	public TextureAtlas getAtlas()
	{
		return textureAtlas;
	}

	public int getMaxHp()
	{
		return maxHp;
	}

	public int getCurrentHp()
	{
		return currentHp;
	}

	public int getDamage()
	{
		return baseDmg + (int) ( randMod * Math.random() );
	}

	public int getDirection()
	{
		return direction;
	}
}
