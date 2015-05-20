package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Malice;
import com.mygdx.game.player.Player;
import com.mygdx.game.world.Map;

public class GameScreen implements Screen
{

	private SpriteBatch batch;

	private final Malice game;


    private Map map;
    private OrthographicCamera cam;
	private Player player;
	
	Music music;

	public GameScreen(Malice g, Music m)
	{
		game = g;
		music = m;
	}

	@Override
	public void show()
	{
		player = new Player();
		batch = new SpriteBatch();
		
		cam = new OrthographicCamera();
        cam.setToOrtho( false, 960, 720 );
        
        map = new Map( 50, 50 );
        map.generate( Map.DUNGEON );
        
//		float w = Gdx.graphics.getWidth();
//		float h = Gdx.graphics.getHeight();
//		player.setBounds( map.getSpawnX(), map.getSpawnY(), 60, 60 );
        player.setPosition( map.getSpawnX(), map.getSpawnY());
		player.scale( 0.5f );
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor( 0, 0, 0, 1 );
		Gdx.gl.glClear( GL30.GL_COLOR_BUFFER_BIT );


        cam.position.x = player.getX();
        cam.position.y = player.getY();
        cam.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        batch.setProjectionMatrix(cam.combined);
		
		batch.begin();
        map.draw( batch );
        float x = player.getX();
        float y = player.getY();
		if ( Gdx.input.isKeyPressed( Input.Keys.CONTROL_LEFT ) )
		{
			player.strafe();
		} else
		{
			player.move();
		}
		if ( map.isCollidingWithWall( player )) player.setPosition( x, y );
		player.draw( batch );
		batch.end();
	}

	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose()
	{
	    map.dispose();
		batch.dispose();
	}

}
