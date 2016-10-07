package raycastsim.drawable

import java.awt.geom.{Ellipse2D, Line2D}

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
    Point((line.b - b) / (m  - line.m), begin.y)
  }

  val line = new Line2D.Double(begin.x, begin.y, end.x, end.y)

  override def draw(g: Graphics2D): Unit = {
    g.draw(line)
  }
}

trait Circle extends Drawable {
  var pos: Point[Double]
  var diameter: Int
  val circle = new Ellipse2D.Double(pos.x, pos.y, diameter, diameter)
  override def draw(g: Graphics2D) = {
    g.fill(circle)
  }
}