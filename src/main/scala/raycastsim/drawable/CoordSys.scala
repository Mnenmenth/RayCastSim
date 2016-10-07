package raycastsim.drawable

import java.awt.Dimension
import java.awt.geom.Ellipse2D

import scala.swing.{Graphics2D, Point}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
class CoordSys(windowSize: Dimension) extends Drawable {

  class Axis(var begin: Point, var end: Point) extends Line {
    override def draw(g: Graphics2D): Unit = {
      g.drawLine(begin.x, begin.y, end.x, end.y)
    }
  }

  class FocalPoint(override val pos: Point, override val diameter: Int) extends PermCircle {
    override val circle = new Ellipse2D.Double(pos.x, pos.y, diameter, diameter)
  }


  val yLength = windowSize.height
  val xLength = windowSize.width

  val yAxis = new Axis(new Point(xLength / 2, 0), new Point(xLength / 2, yLength))
  var xAxis = new Axis(new Point(0, yLength / 2), new Point(xLength, yLength / 2))

  val fDiameter = 10

  val nearFPos = new Point((xLength / 2 ) - (xLength / 8), (yLength / 2) - fDiameter / 2)
  val nearF = new FocalPoint(nearFPos, fDiameter)

  val nearF2Pos = new Point((xLength / 2) - ((xLength / 8)*3), (yLength / 2) - fDiameter / 2)
  val nearF2 = new FocalPoint(nearF2Pos, fDiameter)

  val farFPos = new Point((xLength / 2) + (xLength / 8), (yLength / 2) - fDiameter / 2)
  val farF = new FocalPoint(farFPos, fDiameter)

  val farF2Pos = new Point((xLength / 2) + ((xLength / 8)*3), (yLength / 2) - fDiameter / 2)
  val farF2 = new FocalPoint(farF2Pos, fDiameter)
  val focalPoints: List[FocalPoint] = List(nearF, nearF2, farF, farF2)

  override def draw(g: Graphics2D): Unit = {
    yAxis.draw(g)
    xAxis.draw(g)
    focalPoints.foreach(f=>f.draw(g))
  }

}
