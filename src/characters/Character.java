package characters;
/**
 * 
 * @author Brianna McBurney
 */
public abstract class Character{

    private String name;
    private String imagePath;

    public Character(String name, String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }
    
    public Character() {
        
    }

    public String getName() {
        return name;
    }    

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }
}