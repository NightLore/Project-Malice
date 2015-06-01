package com.mygdx.game.testers;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import com.mygdx.game.player.Player;
import com.mygdx.game.MimicGdx;

public class JUnitPlayerTest {

	/**
	 * Tests three initializations inside Player's constructor
	 */
	@Test
	public void testPlayer() {
		Player p = new Player();
		assertNotNull(p.getSpeed());
		assertNotNull(p.getExpToLevel());
		assertNotNull(p.getCurrentLevel());
	}
	
	/**
	 * Tests Player's increasePoints() method for when increasePoints()
	 * increases playerPoints by 10
	 */
	@Test
	public void testIncreasePoints() {
		Player p = new Player();
		int points = p.getPoints();
		p.increasePoints();
		assertEquals(p.getPoints(), points + 10);
	}

	/**
	 * Tests Player's increaseExp() method for when it doesn't exceed expToLevel and
	 * when it does. expToLevel should equal 100 and player's level should equal 1.
	 */
	@Test
	public void testIncreaseExp() {
		MimicGdx.isTesting = true;
		Player p = new Player();
		// increaseExp() doesn't exceed expToLevel
		p.setExperience(0);
		p.increaseExp(10);
		assertEquals(p.getExperience(), 10);
		// increaseExp() exceeds expToLevel
		p.setExperience(95);
		p.increaseExp(10);
		assertEquals(p.getExperience(), 5);
		assertEquals(p.getCurrentLevel(), 2);
		assertEquals(p.getExpToLevel(), 100);
		MimicGdx.isTesting = false;
	}
}