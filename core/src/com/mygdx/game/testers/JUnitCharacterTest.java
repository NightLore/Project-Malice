package com.mygdx.game.testers;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import com.mygdx.game.player.Character;
import com.mygdx.game.MimicGdx;

public class JUnitCharacterTest {
	/**
	 * finds the distance between the coordinate (x, y) and (0, 0)
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @return the distance from the origin to (x, y)
	 */
	public double distanceFormula(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}

	@Test
	public void testMove() {
		Character c = new Character();
		c.setDirection(0);
	}

	/**
	 * initializes a character at the origin and translates it dx across the x
	 * axis and dy across the y axis. Margin of error is set at .0001
	 */
	@Test
	public void testTranslate() {
		// initializes new Character and moves it
		Character c = new Character();
		float moveSpeed = c.getSpeed();
		int dx = 1;
		int dy = 1;
		c.translate(dx, dy);
		assertEquals("Character: translate(" + dy + ", " + dx
				+ ") should result in the x-coordinate of the Character at "
				+ (moveSpeed * dx / Math.sqrt(2)),
				moveSpeed * dx / Math.sqrt(2), c.getX(), .0001);
		assertEquals("Character: translate(" + dy + ", " + dx
				+ ") should result in the y-coordinate of the Character at "
				+ (moveSpeed * dy / Math.sqrt(2)),
				moveSpeed * dy / Math.sqrt(2), c.getY(), .0001);
	}

	/**
	 * tests Character's increaseCurrentLevel() method
	 */
	@Test
	public void testIncreaseCurrentLevel() {
		MimicGdx.isTesting = true;
		Character c = new Character();
		c.takeDamage(25);
		int level = c.getCurrentLevel() + 1;
		int BDmg = c.getBDmg() + 2;
		int increasedHp = c.getCurrentHp()
				+ (int) (10 * (c.getCurrentHp() / c.getMaxHp() + 1));
		int maxHp = c.getMaxHp() + 10;
		c.increaseCurrentLevel();
		assertEquals(c.getCurrentLevel(), level);
		assertEquals(c.getBDmg(), BDmg);
		assertEquals(c.getMaxHp(), maxHp);
		assertEquals(c.getCurrentHp(), increasedHp);
		MimicGdx.isTesting = false;
	}

}
