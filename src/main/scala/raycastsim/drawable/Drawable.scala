package raycastsim.drawable

import java.awt.geom.{Ellipse2D, Line2D}

import raycastsim.drawable.CoordSys.SingleAxis
import raycastsim.math.Math._
import scala.swing.Graphics2D

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */

trait Drawable {
  def draw(g: Graphics2D): Unit = {}
}

trait Line extends Drawable {
  var begin: Point[Double]
  var end: Point[Double]
  var line = new Line2D.Double(CoordSys.c2p(begin.x, SingleAxis.X), CoordSys.c2p(begin.y, SingleAxis.Y), CoordSys.c2p(end.x, SingleAxis.X), CoordSys.c2p(end.y, SingleAxis.Y))

  def m = (begin.y - end.y) / (begin.x - end.x)
  def b = begin.y - (m*begin.x)

  def intersection(line: Line): Point[Double] = {
    val x = (line.b - b) / (m - line.m)
    val y = m * x + b
    Point[Double](x, y)
  }

  def extendAlong(axis: SingleAxis.Value, length: Double): Unit = {
    val m = this.m
    val b = this.b
    var end1 = Point(0.0,0.0)
    if(axis == SingleAxis.X) {
      //returns + if begin > end - if begin < end
      val sign = (end.x - begin.x)/math.abs(end.x - begin.x)
      /*if (begin.x > end.x) {
        end.x = end.x + sign * length
      } else if (begin.x < end.x) {
        end.x = end.x + length
      }*/
      end1 = Point(end.x + sign * length, m * end.x + b)
    } else if (axis == SingleAxis.Y){
      /*if (begin.y > end.y) {
        end.y = end.y - length
      } else if (begin.y < end.y) {
        end.y = end.y + length
      }*/
      //returns + if begin > end - if begin < end
      val sign = (end.y - begin.y)/math.abs(end.y - begin.y)
      end1 = Point(end.y + sign * length, (end.y-b)/m)
    }
    val newBegin = CoordSys.c2p(begin)
    val newEnd = CoordSys.c2p(end1)
    line = new Line2D.Double(newBegin.x, newBegin.y, newEnd.x, newEnd.y)
  }

  /**
    * So... If you don't remember this is what this does:
    *
    * by definition l=sqrt(dx^2 + dy^2)
    * and dy = m dx
    * So dx = +- sqrt(l^2/(m^2 +1))
    *
    * @param l the length to be added; l != 0, if it does, the universe will implode.
    */
  def extend(l: Double): Unit = {
    val dx = math.sqrt((l*l)/(m*m+1))
    val dy = m*dx
    val p = Point(dx,dy)
    import CoordSys.c2p
    val begin1 = c2p(if(l>0) begin else begin - p)
    val end1 = c2p(if(l>0) p + end else end)
    line = new Line2D.Double(begin1.x,begin1.y,end1.x,end1.y)
  }

  def intersects(_line: Line2D): Boolean = line.intersectsLine(_line)

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