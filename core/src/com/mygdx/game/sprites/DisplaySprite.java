package com.mygdx.game.sprites;

import java.util.ArrayList;
import java.util.EnumMap;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.Malice;
import com.mygdx.game.sprites.SpriteData.Stats;

public class DisplaySprite extends StatsSprite
{
    private Skin skin;
    private int delay;
    private float currentTime;
    private ArrayList<Label> labels;
    
    public DisplaySprite( Skin s, int delay, Animation... a )
    {
        this( s, delay );
        this.initializeAnimations( a );
    }
    public DisplaySprite( Skin s, int delay ) {
        skin = s;
        this.delay = delay;
        this.labels = new ArrayList<Label>();
    }
    
    public void render( float delta ) {
        currentTime += delta;
        if ( currentTime > delay ) {
            currentTime -= delay;
            setDirection( getDirection() + 1 );
        }
        setAnimations();
    }
    
    public void createLabels() {
        int x = Malice.GAME_WIDTH * 3 / 4;
        int y = Malice.GAME_HEIGHT / 2;
        SpriteData data = this.getSpriteData();
        Label l = new Label( data.getName(), skin, "label" );
        l.setPosition( x, Malice.GAME_HEIGHT * 7 / 8 );
        l.setVisible( false );
        labels.add( l );

        EnumMap<Stats, Integer> startingStats = data.getStartStats();
        Stats[] stats = Stats.values();
        for ( int i = 0; i < stats.length; i++ ) {
            Stats s = stats[i];
            l = new Label( s + " " + startingStats.get( s ), skin, "label" );
            l.setPosition( x, y );
            labels.add( l );
            y -= l.getPrefHeight() - 5;
        }
    }
    
    public void setVisible( boolean isVisible ) {
        for ( Label l : labels ) {
            l.setVisible( isVisible );
        }
    }
    
    public Label[] getLabels() {
        return (Label[])labels.toArray( new Label[labels.size()]);
    }
}