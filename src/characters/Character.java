package characters;
/**
 * 
 * @author Brianna McBurney
 */
public abstract class Character implements Attack{

    private final String name;

    /**
     * 
     * @param name The name of the Characters
     */
    public Character(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }    
}