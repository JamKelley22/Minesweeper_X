package other;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class Config {
	
	public static final Random RANDOM = new Random();
	
	public static final int CELL_DISTANCE = 20;//In pixels
	
	public static final Color CELL_COLOR_CLOSED = Color.GRAY;
	
	public static Point subtract(Point p1, Point p2) {
	    return new Point(p1.x - p2.x, p1.y - p2.y - 25);//-25 to adjust for close screen offset
	}
}
