package characters;

public class Player extends Character {

    private final String weapon;

    /**
     * Creates a Player for the user to control.
     * @param weapon The weapon that the Player will wield
     * @param name The name of the Player
     * @param regeneration How much health the Player can regenerate at a time
     * @param attackStrength How much damage the Player's attack can do
     * @param blockStrength How strong the Player's block is
     * @param health How much health the Player has
     */
    public Player(String weapon, String name, int regeneration, int attackStrength, int blockStrength, int health) {
        super(name, regeneration, attackStrength, blockStrength, health);
        this.weapon = weapon;
    }

    public String getWeapon() {
        return this.weapon;
    }
    
    @Override
    public int damage (int characterHealth){
        characterHealth --;
        return characterHealth;
    }

}