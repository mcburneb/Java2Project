package characters;

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
    public int damage(Player player, Monster monster);
}
