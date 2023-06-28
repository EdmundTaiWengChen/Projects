package game.enums;

/**
 * This class represents all the Elements of the Pokemon and items
 * <p>
 * Created by:
 *  * @author Riordan D. Alfredo
 *  * Modified by:
 *  * @author Chew Jia Hong
 */
public enum Element {
    WATER("Water"),
    FIRE("Fire"),
    GRASS("Grass"),
    DRAGON("Dragon");

    private final String label;

    Element(String label) {
        this.label = label;
    }

    /**
     * @return the label text
     */
    @Override
    public String toString() {
        return label;
    }
}
