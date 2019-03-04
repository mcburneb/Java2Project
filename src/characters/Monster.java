package characters;

import java.util.ArrayList;

public class Monster extends Character {

    private int level;

    /**
     * Creates a Monster for the Player to fight.
     * @param level The level in which the Monster will attack the Player
     * @param name The name of the Monster
     * @param regeneration How much health the Monster can regenerate at a time
     * @param attackStrength How much damage the Monster's attack can do
     * @param blockStrength How strong the Monster's block is
     */
    public Monster(int level, String name, int regeneration, int attackStrength, int blockStrength) {
        super(name, regeneration, attackStrength, blockStrength);
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    /**
     * Generate the Monsters for each level
     */
    public void renderMonsters() {        
        ArrayList<Monster> monsterList = new ArrayList<>();
        // TODO - implement Game.createMonster
    }

}