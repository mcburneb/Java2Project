package controller;

import model.Player;
import model.Monster;

/**
 *
 * @author Mostafa Allahmoradi
 */
public interface Attack {

    /**
     * Cause damage to the monster
     * 
     * @param player Player chosen by the user
     * @param monster Current monster of each level
     * @return The new health of the monster
     */
    public int damage(Player player, Monster monster);
}
