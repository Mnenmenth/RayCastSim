package raycastsim.drawable

import java.awt.geom.{Ellipse2D, Line2D}

import raycastsim.drawable.CoordSys.SingleAxis

import scala.swing.Graphics2D

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */

case class Point[T:Numeric](x: T, y: T)

trait Drawable {
  def draw(g: Graphics2D): Unit = {}
}

trait Line extends Drawable {
  var begin: Point[Double]
  var end: Point[Double]

  def m = (begin.y - end.y) / (begin.x - end.x)
  def b = begin.y / (m*begin.x)

  def intersection(line: Line): Point[Double] = {
    val x = (line.b - b) / (m - line.m)
    val y = m * x + b
    Point[Double](x, y)
  }

  val line = new Line2D.Double(CoordSys.c2p(begin.x, SingleAxis.X), CoordSys.c2p(begin.y, SingleAxis.Y), CoordSys.c2p(end.x, SingleAxis.X), CoordSys.c2p(end.y, SingleAxis.Y))
  line
  override def draw(g: Graphics2D): Unit = {
    g.draw(line)
  }
}

trait Circle extends Drawable {
  var pos: Point[Double]
  var diameter: Int
  val circle = new Ellipse2D.Double(CoordSys.c2p(pos.x, SingleAxis.X)-(diameter/2), CoordSys.c2p(pos.y, SingleAxis.Y)-(diameter/2), diameter, diameter)
  override def draw(g: Graphics2D) = {
    g.fill(circle)
  }
}