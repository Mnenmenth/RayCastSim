package raycastsim.drawable

import java.awt.Dimension

import raycastsim.core.RayCastSim
import raycastsim.drawable.CoordSys.{Axis, FocalPoint}
import raycastsim.math.Math._
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

  //Y Axis has to be minus rather than double since everything is drawn from top left -> down instead of bottom left -> up
  //Coordinate to pixel
  def c2p(coord: Double, axis: SingleAxis.Value): Double = {
    if(axis == SingleAxis.X) {
      (100+coord)*RayCastSim.windowSize.width/200
    } else if(axis == SingleAxis.Y) {
      (100-coord)*RayCastSim.windowSize.height/200
    } else {
      0.0
    }
  }

  def c2p(coord: Point[Double]): Point[Double] = {
    val x = (100+coord.x)*RayCastSim.windowSize.width/200
    val y = (100-coord.y)*RayCastSim.windowSize.height/200
    Point[Double](x, y)
  }

  //Pixel to Coordinate
  def p2c(coord: Double, axis: SingleAxis.Value): Double = {
    if(axis == SingleAxis.X) {
      (coord/RayCastSim.windowSize.width)*200-100
    } else if(axis == SingleAxis.Y) {
      -1*((coord/RayCastSim.windowSize.height)*200-100)
    } else {
      0.0
    }
  }

  def p2c(coord: Point[Double]): Point[Double] = {
    val x = (coord.x/RayCastSim.windowSize.width)*200-100
    val y = -1*((coord.y/RayCastSim.windowSize.height)*200-100)
    Point[Double](x, y)
  }

}

class CoordSys(windowSize: Dimension) extends Drawable {

  val yAxis = new Axis(Point[Double](0, -100), Point[Double](0, 100))
  val xAxis = new Axis(Point[Double](-100, 0), Point[Double](100, 0))

  val fDiameter = 10

  val nearFPos = Point[Double](-25, 0)
  val nearF = new FocalPoint(nearFPos, fDiameter)

  val nearF2Pos = Point[Double](-75, 0)
  val nearF2 = new FocalPoint(nearF2Pos, fDiameter)

  val farFPos = Point[Double](25, 0)
  val farF = new FocalPoint(farFPos, fDiameter)

  val farF2Pos = Point[Double](75, 0)
  val farF2 = new FocalPoint(farF2Pos, fDiameter)
  val focalPoints: List[FocalPoint] = List(nearF, nearF2, farF, farF2)

  override def draw(g: Graphics2D): Unit = {
    yAxis.draw(g)
    xAxis.draw(g)
    focalPoints.foreach(f=>f.draw(g))
  }

}
