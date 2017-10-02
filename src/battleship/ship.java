
package battleship;
import java.awt.Color;

public class ship {
    enum Direction {
        NONE,
        LEFT,
        RIGHT,
        UP,
        DOWN     
    }
    private Color color = Color.GRAY;
    private int shipNum;
    
    ship(int _shipNum)
    {
        shipNum=_shipNum;
        if(shipNum > 4)
        {
            color = color.white;
        }
         if(shipNum >= 100)
        {
            color = color.blue;
        }
    }
    public int getShipNum()
    {
        return(shipNum);
    }
    Color getColor()
    {
        return (color);
    }
    void setColor(Color _color)
    {
        color = _color;
    }
    public void setShipDirection(Direction _direction)
    {
        
            
    }         
}





