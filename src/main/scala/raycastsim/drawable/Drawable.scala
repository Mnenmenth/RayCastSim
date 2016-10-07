package raycastsim.drawable

import java.awt.geom.Ellipse2D

import scala.swing.{Dimension, Graphics2D, Point}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */

trait Drawable {
  def draw(g: Graphics2D): Unit = {}
}

trait PermLine extends Drawable {
  val begin: Point
  val end: Point
}

trait Line extends Drawable {
  var begin: Point
  var end: Point
}

trait PermCircle extends Drawable {
  val pos: Point
  val diameter: Int
  val circle: Ellipse2D
  override def draw(g: Graphics2D) = {
    g.fill(circle)
  }
}

trait Circle extends Drawable {
  var pos: Point
  var diameter: Int
  var circle: Ellipse2D
  override def draw(g: Graphics2D) = {
    g.fill(circle)
  }
}