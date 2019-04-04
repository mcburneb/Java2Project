package characters;
/**
 * 
 * @author Brianna McBurney
 */
public abstract class Character implements Attack{

    private String name;

    /**
     * 
     * @param name The name of the Characters
     */
    public Character(String name) {
        this.name = name;
    }
    
    public Character() {
        
    }

    public String getName() {
        return name;
    }    

    public void setName(String name) {
        this.name = name;
    }
}