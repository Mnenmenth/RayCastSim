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

object Ray {

  class BeginEnd(var begin: Point[Double], var end: Point[Double]) extends Line {

  }

  class Through(var begin: Point[Double], through: Point[Double], axis: SingleAxis.Value, length: Double = 0) extends Line {
    var end: Point[Double] = through
    if(length == 0) extendAlong(axis, 100)
    else extendAlong(axis, length)
  }

  object DottedRay {
    object DotLoc extends Enumeration {
      val BEGIN, END, BOTH = Value
    }
  }
  //Make the ray extendAlong infinitly in any of the specified directions to the end of the screen
  class DottedRay(var begin: Point[Double], var end: Point[Double], dotLoc: DotLoc.Value) extends Line {

    var dottedBegin = begin
    var dottedEnd = end
    var dottedLine = new Line2D.Double(CoordSys.c2p(begin.x, SingleAxis.X), CoordSys.c2p(begin.y, SingleAxis.Y), CoordSys.c2p(end.x, SingleAxis.X), CoordSys.c2p(end.y, SingleAxis.Y))

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

    def extendDotted(length: Double): Unit = {
      val l = if(dotLoc == DotLoc.END) -math.abs(length) else math.abs(length)

      val m = this.m
      val b0 = this.b

      var x = begin.x
      var y = begin.y
      if (l > 0) {
        x = end.x
        y = end.y
      }

      def getExtends(x:Double,y:Double,l:Double): (Point[Double],Point[Double]) ={
        import scala.math._
        val x1 = (sqrt(4*(m*m+1)*(-pow(y-b,2)+l*l-x*x)+pow(2*m*(y-b)+2*x,2))+2*m*(y-b)+2*x)/(2*m*m+2)
        val x2 = (-sqrt(4*(m*m+1)*(-pow(y-b,2)+l*l-x*x)+pow(2*m*(y-b)+2*x,2))+2*m*(y-b)+2*x)/(2*m*m+2)

        val y1 = m*x1+b0
        val y2 = m*x2+b0

        (Point(x1,y1),
        Point(x2,y2))
      }

      import CoordSys.c2p
      import math._
      val ps = getExtends(x,y,l)
      if(dotLoc==DotLoc.BOTH){
        val cp1 = c2p(getExtends(begin.x,begin.y,-abs(length))._1)
        val cp2 = c2p(getExtends(end.x,end.y,abs(length))._2)
        dottedLine = new Line2D.Double(cp1.x,cp1.y,cp2.x,cp2.y)
      }else if(l>0){
        val begin1 = c2p(begin)
        val cp = c2p(ps._1)
        dottedLine = new Line2D.Double(begin1.x,begin1.y,cp.x,cp.y)
      }else{
        val end1 = c2p(end)
        val cp = c2p(ps._2)
        dottedLine = new Line2D.Double(cp.x,cp.y,end1.x,end1.y)
      }
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