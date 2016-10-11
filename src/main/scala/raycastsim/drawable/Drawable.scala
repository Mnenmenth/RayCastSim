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
    if(axis == SingleAxis.X) {
      //returns + if begin > end - if begin < end
      val sign = (end.x - begin.x)/math.abs(end.x - begin.x)
      /*if (begin.x > end.x) {
        end.x = end.x + sign * length
      } else if (begin.x < end.x) {
        end.x = end.x + length
      }*/
      end.x = end.x + sign * length
      end.y = m * end.x + b
    } else if (axis == SingleAxis.Y){
      /*if (begin.y > end.y) {
        end.y = end.y - length
      } else if (begin.y < end.y) {
        end.y = end.y + length
      }*/
      //returns + if begin > end - if begin < end
      val sign = (end.y - begin.y)/math.abs(end.y - begin.y)
      end.y = end.y + sign * length
      end.x = (end.y-b)/m
    }
    val newBegin = CoordSys.c2p(begin)
    val newEnd = CoordSys.c2p(end)
    line = new Line2D.Double(newBegin.x, newBegin.y, newEnd.x, newEnd.y)
  }

  /**
    * So... If you don't remember this is what this does:
    *
    * let the point we are extending from be (m,n) (either the begining or end)
    * by definition l=sqrt((m-x)^2 +(n-y)^2)
    * and y = mx + b
    * So, solving these you get a messy quadratic that I'm not going to type out
    * Which gives two answers, one for when l>0 and one for when l<0 (p1 and p2, respectively)
    * So yeah. Have fun
    * If it breaks, it means that l = 0 probably. or that math broke. take your pick.
    *
    * @param l the length to be added; l != 0, if it does, the universe will implode.
    */
  def extend(l: Double): Unit = {
    val m = this.m
    val b0 = this.b

    var x = begin.x
    var y = begin.y
    if (l > 0) {
      x = end.x
      y = end.y
    }

    import scala.math._
    val x1 = (sqrt(4*(m*m+1)*(-pow(y-b,2)+l*l-x*x)+pow(2*m*(y-b)+2*x,2))+2*m*(y-b)+2*x)/(2*m*m+2)
    val x2 = (-sqrt(4*(m*m+1)*(-pow(y-b,2)+l*l-x*x)+pow(2*m*(y-b)+2*x,2))+2*m*(y-b)+2*x)/(2*m*m+2)

    val y1 = m*x1+b0
    val y2 = m*x2+b0

    val p1 = Point(x1,y1)
    val p2 = Point(x2,y2)

    import CoordSys.c2p
    if(l>0){
      val begin1 = c2p(begin)
      val cp = c2p(p1)
      line = new Line2D.Double(begin1.x,begin1.y,cp.x,cp.y)
    }else{
      val end1 = c2p(end)
      val cp = c2p(p2)
      line = new Line2D.Double(cp.x,cp.y,end1.x,end1.y)
    }
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