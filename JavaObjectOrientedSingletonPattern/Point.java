import java.util.ArrayList;

/**
 * Point class to store the point's id, alias, state and moves.
 */
public class Point {

    // attributes

    private int id; // 0-23, piece id
    private String alias; // e.g. "G1" points to piece id 2
    private String alias2; // e.g. "1G" points to piece id 2
    private int state; // 0=empty, 1=player1, 2=player2

    private ArrayList<Integer> moves = new ArrayList<Integer>();

    // constants

    private final String EMPTY_CHAR = "#"; // empty point
    private final String PLAYER1_CHAR = "X"; // point with player 1 piece
    private final String PLAYER2_CHAR = "O"; // point with player 2 piece

    // constructors

    /**
     * Constructor to create a point with an id and state, also automatically sets alias and moves.
     * @param id id of the point
     * @param state state of the point
     */
    public Point(int id, int state){
        this.id = id;
        this.state = state;

        this.setDetails(id);
        this.alias2 = this.alias.substring(1) + this.alias.charAt(0);
    }

    // setters / mutators

    public void setId(int id) {
        this.id = id;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    // getters / accessors


    public int getId() {
        return id;
    }

    public int getState() {
        return state;
    }

    public ArrayList<Integer> getMoves() {
        return moves;
    }

    public String getAlias() {
        return alias;
    }

    public String getAlias2() {
        return alias2;
    }

    // methods

    public String toString(){
        if(state == 0){
            return EMPTY_CHAR;
        } else if(state == 1){
            return PLAYER1_CHAR;
        } else if(state == 2){
            return PLAYER2_CHAR;
        } else {
            throw new java.lang.Error("[ERROR] Invalid state provided.");
        }
    }

    public void setDetails(int id){
        if (id == 0){
            moves.add(1);
            moves.add(9);
            setAlias("A1");
        }
        else if (id == 1){
            moves.add(0);
            moves.add(2);
            moves.add(4);
            setAlias("D1");
        }
        else if (id == 2){
            moves.add(1);
            moves.add(14);
            setAlias("G1");
        }
        else if (id == 3){
            moves.add(4);
            moves.add(10);
            setAlias("B2");
        }
        else if (id == 4){
            moves.add(1);
            moves.add(3);
            moves.add(5);
            moves.add(7);
            setAlias("D2");
        }
        else if (id == 5){
            moves.add(4);
            moves.add(13);
            setAlias("F2");
        }
        else if (id == 6){
            moves.add(7);
            moves.add(11);
            setAlias("C3");
        }
        else if (id == 7){
            moves.add(4);
            moves.add(6);
            moves.add(8);
            setAlias("D3");
        }
        else if (id == 8){
            moves.add(7);
            moves.add(12);
            setAlias("E3");
        }
        else if (id == 9){
            moves.add(0);
            moves.add(10);
            moves.add(21);
            setAlias("A4");
        }
        else if (id == 10){
            moves.add(3);
            moves.add(9);
            moves.add(11);
            moves.add(18);
            setAlias("B4");
        }
        else if (id == 11){
            moves.add(6);
            moves.add(10);
            moves.add(15);
            setAlias("C4");
        }
        else if (id == 12){
            moves.add(8);
            moves.add(13);
            moves.add(17);
            setAlias("E4");
        }
        else if (id == 13){
            moves.add(5);
            moves.add(12);
            moves.add(14);
            moves.add(20);
            setAlias("F4");
        }
        else if (id == 14){
            moves.add(2);
            moves.add(13);
            moves.add(23);
            setAlias("G4");
        }
        else if (id == 15){
            moves.add(11);
            moves.add(16);
            setAlias("C5");
        }
        else if (id == 16){
            moves.add(15);
            moves.add(17);
            moves.add(19);
            setAlias("D5");
        }
        else if (id == 17){
            moves.add(12);
            moves.add(16);
            setAlias("E5");
        }
        else if (id == 18){
            moves.add(10);
            moves.add(19);
            setAlias("B6");
        }
        else if (id == 19){
            moves.add(16);
            moves.add(18);
            moves.add(20);
            moves.add(22);
            setAlias("D6");
        }
        else if (id == 20){
            moves.add(13);
            moves.add(19);
            setAlias("F6");
        }
        else if (id == 21){
            moves.add(9);
            moves.add(22);
            setAlias("A7");
        }
        else if (id == 22){
            moves.add(19);
            moves.add(21);
            moves.add(23);
            setAlias("D7");
        }
        else if (id == 23){
            moves.add(14);
            moves.add(22);
            setAlias("G7");
        }
    }

}
