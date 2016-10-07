package raycastsim.drawable

import java.awt.Dimension

import raycastsim.core.RayCastSim
import raycastsim.drawable.CoordSys.{Axis, FocalPoint, SingleAxis}

import scala.swing.Graphics2D

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */

object CoordSys {
  class FocalPoint(var pos: Point[Double], var diameter: Int) extends Circle
  class Axis(var begin: Point[Double], var end: Point[Double]) extends Line

  object SingleAxis extends Enumeration {
    val X, Y = Value
  }
  object Axis {
    def coordToPixel(coord: Double, axis: SingleAxis.Value): Double = {
      if(axis == SingleAxis.X) {
        (100+coord)*RayCastSim.windowSize.width/200
      } else if(axis == SingleAxis.Y) {
        (100+coord)*RayCastSim.windowSize.height/200
      } else {
        0.0
      }
    }
    def pixelToCoord(coord: Double, axis: SingleAxis.Value): Double = {
      if(axis == SingleAxis.X) {
        (coord*200)/(RayCastSim.windowSize.width-100)
      } else if(axis == SingleAxis.Y) {
        (coord*200)/(RayCastSim.windowSize.width-100)
      } else {
        0.0
      }
    }
  }
}

class CoordSys(windowSize: Dimension) extends Drawable {

  val yLength = windowSize.height
  val xLength = windowSize.width

  val yAxis = new Axis(Point[Double](xLength / 2, 0), Point[Double](xLength / 2, yLength))
  var xAxis = new Axis(Point[Double](0, yLength / 2), Point[Double](xLength, yLength / 2))

  val fDiameter = 10

  //val nearFPos = Point[Double](Axis.pixelToCoord((xLength / 2 ) - (xLength / 8), SingleAxis.X), Axis.pixelToCoord((yLength / 2) - fDiameter / 2, SingleAxis.X))
  val nearFPos = Point[Double]((xLength / 2 ) - (xLength / 8), (yLength / 2) - fDiameter / 2)
  val nearF = new FocalPoint(nearFPos, fDiameter)

  val nearF2Pos = Point[Double]((xLength / 2) - ((xLength / 8)*3), (yLength / 2) - fDiameter / 2)
  val nearF2 = new FocalPoint(nearF2Pos, fDiameter)

  val farFPos = Point[Double]((xLength / 2) + (xLength / 8), (yLength / 2) - fDiameter / 2)
  val farF = new FocalPoint(farFPos, fDiameter)

  val farF2Pos = Point[Double]((xLength / 2) + ((xLength / 8)*3), (yLength / 2) - fDiameter / 2)
  val farF2 = new FocalPoint(farF2Pos, fDiameter)
  val focalPoints: List[FocalPoint] = List(nearF, nearF2, farF, farF2)

  override def draw(g: Graphics2D): Unit = {
    yAxis.draw(g)
    xAxis.draw(g)
    focalPoints.foreach(f=>f.draw(g))
  }

}
