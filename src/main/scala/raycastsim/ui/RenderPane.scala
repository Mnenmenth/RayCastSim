package raycastsim.ui

import java.awt.MouseInfo

import raycastsim.core.RayCastSim
import raycastsim.drawable.CoordSys.{FocalPoint, SingleAxis}
import raycastsim.drawable.{CoordSys, Point, Ray}

import scala.swing.{Graphics2D, Panel}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
class RenderPane extends Panel {

  val graph = new CoordSys(RayCastSim.windowSize)

  val ray1 = new Ray.BeginEnd(
    Point[Double](40, 30),
    Point[Double](80, 60)
  )
  val ray2 = new Ray.BeginEnd(
    Point[Double](61, 31),
    Point[Double](41, 81)
  )

  val ray3 = new Ray.BeginEnd(
    Point[Double](-40, 30),
    Point[Double](-80, 60)
  )
  val ray4 = new Ray.BeginEnd(
    Point[Double](-61, 31),
    Point[Double](-41, 81)
  )

  val ray5 = new Ray.BeginEnd(
    Point[Double](-40, -30),
    Point[Double](-80, -60)
  )

  val ray6 = new Ray.BeginEnd(
    Point[Double](-61, -31),
    Point[Double](-41, -81)
  )

  val ray7 = new Ray.BeginEnd(
    Point[Double](30, -30),
    Point[Double](80, -60)
  )

  val ray8 = new Ray.BeginEnd(
    Point[Double](61, -31),
    Point[Double](41, -81)
  )

  ray1.continue(40, SingleAxis.X)
  ray3.continue(40, SingleAxis.X)
  ray5.continue(40, SingleAxis.X)
  ray7.continue(40, SingleAxis.X)
  val circ = new FocalPoint(ray1.intersection(ray2), 10)
  val circ1 = new FocalPoint(ray3.intersection(ray4), 10)
  val circ2 = new FocalPoint(ray5.intersection(ray6), 10)
  val circ3 = new FocalPoint(ray7.intersection(ray8), 10)
  override def paint(g: Graphics2D): Unit = {
    super.paint(g)
    val mousePos = MouseInfo.getPointerInfo.getLocation
    g.drawString(s"Screen - X: ${mousePos.x}, Y: ${mousePos.y}", 10, 10)
    g.drawString(s"Cartesian - X: ${CoordSys.p2c(mousePos.x, SingleAxis.X)}, Y: ${CoordSys.p2c(mousePos.y, SingleAxis.Y)}", 10, 30)
    graph.draw(g)
    ray1.draw(g)
    ray2.draw(g)
    ray3.draw(g)
    ray4.draw(g)
    ray5.draw(g)
    ray6.draw(g)
    ray7.draw(g)
    ray8.draw(g)
    circ.draw(g)
    circ1.draw(g)
    circ2.draw(g)
    circ3.draw(g)
    repaint()
  }

}
