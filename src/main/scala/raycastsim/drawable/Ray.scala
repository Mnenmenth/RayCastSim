package raycastsim.drawable

import java.awt.BasicStroke
import java.awt.geom.Line2D
import raycastsim.math.Math._
import raycastsim.drawable.CoordSys.SingleAxis
import raycastsim.drawable.Ray.DottedRay.DotLoc

import scala.swing.Graphics2D

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
/** Standard Ray
  *
  * @constructor Beginning and Ending points of line
  * @param beginPoint Beginning point of line
  * @param endPoint Ending point of line
  */
abstract class Ray(beginPoint: Point[Double], endPoint: Point[Double]) extends Line {
  private var _begin: Point[Double] = beginPoint
  override def begin_=(p: Point[Double])={
    _begin = p
    val beginConv = CoordSys.c2p(p)
    val endConv = CoordSys.c2p(end)
    line.setLine(beginConv.x, beginConv.y, endConv.x, endConv.y)
  }

  private var _end: Point[Double] = endPoint
  override def end_=(p: Point[Double])={
    _end = p
    val beginConv = CoordSys.c2p(p)
    val endConv = CoordSys.c2p(end)
    line.setLine(beginConv.x, beginConv.y, endConv.x, endConv.y)
  }

}

object Ray {

  /** Ray constructed using two points
    *
    * @constructor Beginning and Ending points of line
    * @param beginPoint Beginning point of line
    * @param endPoint point of line
    */
  class BeginEnd(beginPoint: Point[Double], endPoint: Point[Double]) extends Ray(beginPoint, endPoint) {

  }

  /** Ray extended through point
    *
    * @constructor Beginning point and point to extend through with distance
    * @param beginPoint Beginning point of line
    * @param through Point ot continue through
    * @param length Length to extend along axis
    */
  class Through(beginPoint: Point[Double], through: Point[Double], length: Double = 0) extends Ray(beginPoint, through) {
    end = through
    if(length == 0) extend()
    else extend(length)
  }

  object DottedRay {
    object DotLoc extends Enumeration {
      val BEGIN, END, BOTH = Value
    }
  }
  /** Ray constructed of two point that extends another dotted line
    *
    * @constructor Beginning and ending point and direction of dotted line
    * @param beginPoint Beginning point of line
    * @param endPoint Ending point of line
    * @param dotLoc Direction to continue dotted line
    *               From the beginning, end, or both ways
    */
  class DottedRay(beginPoint: Point[Double], endPoint: Point[Double], dotLoc: DotLoc.Value) extends Ray(beginPoint, endPoint) {

    var dottedBegin = begin
    var dottedEnd = end
    var dottedLine = new Line2D.Double(CoordSys.c2p(begin.x, SingleAxis.X), CoordSys.c2p(begin.y, SingleAxis.Y), CoordSys.c2p(end.x, SingleAxis.X), CoordSys.c2p(end.y, SingleAxis.Y))

    /** Extended dotted line along axis of distance length
      *
      * @param axis Axis to extend along
      * @param length Length to extend along axis
      */
    def extendAlongDotted(axis: SingleAxis.Value, length: Double = 150.0): Unit = {
      val m = this.m
      val b = this.b
      if(axis == SingleAxis.X) {
        if (begin.x > end.x) {
          if(dotLoc == DotLoc.BEGIN) {
            dottedBegin.x = begin.x + length
            dottedBegin.y = m * begin.x + b
          } else if (dotLoc == DotLoc.END) {
            dottedEnd.x = end.x - length
            dottedEnd.y = m * end.x + b
          } else if (dotLoc == DotLoc.BOTH) {
            dottedBegin.x = begin.x + length
            dottedBegin.y = m * begin.x + b
            dottedEnd.x = end.x - length
            dottedEnd.y = m * end.x + b
          }
        } else if (begin.x < end.x) {
          if (dotLoc == DotLoc.BEGIN) {
            dottedBegin.x = begin.x - length
            dottedBegin.y = m * begin.x + b
          } else if (dotLoc == DotLoc.END) {
            dottedEnd.x = end.x + length
            dottedEnd.y = m * end.x + b
          } else if (dotLoc == DotLoc.BOTH) {
            dottedBegin.x = begin.x - length
            dottedBegin.y = m * begin.x + b
            dottedEnd.x = end.x + length
            dottedEnd.y = m * end.x + b
          }
        }
      } else if (axis == SingleAxis.Y){
        if (begin.y > end.y) {
          if(dotLoc == DotLoc.BEGIN) {
            dottedBegin.y = begin.y + length
            dottedBegin.x = (begin.y-b)/m
          } else if (dotLoc == DotLoc.END) {
            dottedEnd.y = end.y - length
            dottedEnd.x = (end.y-b)/m
          } else if (dotLoc == DotLoc.BOTH) {
            dottedBegin.y = begin.y + length
            dottedBegin.x = (begin.y-b)/m
            dottedEnd.y = end.y - length
            dottedEnd.x = (end.y-b)/m
          }
        } else if (begin.y < end.y) {
          if(dotLoc == DotLoc.BEGIN) {
            dottedBegin.y = begin.y - length
            dottedBegin.x = (begin.y-b)/m
          } else if (dotLoc == DotLoc.END) {
            dottedEnd.y = end.y + length
            dottedEnd.x = (end.y-b)/m
          } else if (dotLoc == DotLoc.BOTH) {
            dottedBegin.y = begin.y - length
            dottedBegin.x = (begin.y-b)/m
            dottedEnd.y = end.y + length
            dottedEnd.x = (end.y-b)/m
          }
        }
      }
      val newBegin = CoordSys.c2p(dottedBegin)
      val newEnd = CoordSys.c2p(dottedEnd)
      dottedLine = new Line2D.Double(newBegin.x, newBegin.y, newEnd.x, newEnd.y)
    }

    /**
      * Extend dotted line
      * @param l Length to extend
      */
    def extendDotted(l: Double = 150.0): Unit = {
      val dx = math.sqrt((l*l)/(m*m+1))
      val dy = m*dx
      val p = Point(dx,dy)
      import CoordSys.c2p
      val begin1 = c2p(if(dotLoc==DotLoc.END) begin else begin - p)
      val end1 = c2p(if(dotLoc!=DotLoc.BEGIN) p + end else end)
      dottedLine = new Line2D.Double(begin1.x,begin1.y,end1.x,end1.y)
    }

    val stroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, Array(20.0f), 0.0f)

    override def draw(g: Graphics2D): Unit = {
      val g2 = g.create().asInstanceOf[Graphics2D]
      g2.setStroke(stroke)
      g2.draw(dottedLine)
      g2.dispose()
      g.draw(line)
    }

  }

}