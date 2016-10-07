package raycastsim.render

import java.awt.Dimension
import java.awt.geom.Ellipse2D

import scala.swing.{Graphics2D, Point}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
class CoordSys(windowSize: Dimension) {

  val yLength = windowSize.height
  val xLength = windowSize.width

  val fDiameter = 10

  val nearFPos = new Point((xLength / 2 ) - (xLength / 8), (yLength / 2) - fDiameter / 2)
  val nearF = new Ellipse2D.Double(nearFPos.x, nearFPos.y, fDiameter, fDiameter)

  val nearF2Pos = new Point((xLength / 2) - ((xLength / 8)*3), (yLength / 2) - fDiameter / 2)
  val nearF2 = new Ellipse2D.Double(nearF2Pos.x, nearFPos.y, fDiameter, fDiameter)

  val farFPos = new Point((xLength / 2) + (xLength / 8), (yLength / 2) - fDiameter / 2)
  val farF = new Ellipse2D.Double(farFPos.x, farFPos.y, fDiameter, fDiameter)

  val farF2Pos = new Point((xLength / 2) + ((xLength / 8)*3), (yLength / 2) - fDiameter / 2)
  val farF2 = new Ellipse2D.Double(farF2Pos.x, farF2Pos.y, fDiameter, fDiameter)

  val focalPoints = List(nearF, nearF2, farF, farF2)

  def draw(g: Graphics2D): Unit = {
    g.drawLine(xLength / 2, 0, xLength / 2, yLength)
    g.drawLine(0, yLength / 2, xLength, yLength / 2)
    focalPoints.foreach(f=>g.fill(f))
  }

}
