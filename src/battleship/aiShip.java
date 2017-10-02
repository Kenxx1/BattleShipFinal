
package battleship;
import java.awt.Color;

public class aiShip {
    enum Direction {
        NONE,
        LEFT,
        RIGHT,
        UP,
        DOWN     
    }
    private Color color = Color.pink;
    private int lastMap;
    
    aiShip(int _lastMap)
    {
        lastMap=_lastMap;
    }
    public int getShipNum()
    {
        return(lastMap);
    }
    Color getColor()
    {
        return (color);
    }
    int randomShips()
    {
       
        int choose = (int) (Math.random() * 4 +1);
        while(choose == lastMap)
        {
            choose = (int) (Math.random() * 4 +1);
        }
        return(choose);
    }
}