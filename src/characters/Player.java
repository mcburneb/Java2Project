package characters;

public class Player extends Character {

    private final String weapon;

    /**
     * Creates a Player for the user to control.
     * @param weapon The weapon that the Player will wield
     * @param name The name of the Player
     * @param health How much health the Player has
     * @param regeneration How much health the Player can regenerate at a time
     * @param attackStrength How much damage the Player's attack can do
     * @param blockStrength How strong the Player's block is
     */
    public Player(String weapon, String name, int health, int regeneration, int attackStrength, int blockStrength) {
        super(name, health, regeneration, attackStrength, blockStrength);
        this.weapon = weapon;
    }

    public String getWeapon() {
        return this.weapon;
    }

}