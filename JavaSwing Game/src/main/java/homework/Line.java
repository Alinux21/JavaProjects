package homework;

import java.io.Serializable;
import java.awt.Point;

public record Line(Point start,Point end) implements Serializable{
}
