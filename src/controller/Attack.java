package controller;

import model.Player;
import model.Monster;

/**
 *
 * @author Mostafa
 */
public interface Attack {

    /**
     * 
     * @param player 
     * @param monster
     * @return The new health of the monster
     */
    public String damage(Player player, Monster monster);
}
