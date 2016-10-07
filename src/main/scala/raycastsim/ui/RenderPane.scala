package raycastsim.ui

import java.awt.geom.Point2D

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

  val ray1 = new Ray(Point[Double](CoordSys.Axis.pixelToCoord(400, SingleAxis.X), 300.0), Point[Double](800, 600))
  val ray2 = new Ray(Point[Double](610, 310), Point[Double](410, 810))
  val circ = new FocalPoint(ray1.intersection(ray2), 20)
  //val test = new FocalPoint(new Point(800, 400), 20)

  override def paint(g: Graphics2D): Unit = {
    super.paint(g)
    graph.draw(g)
    ray1.draw(g)
    ray2.draw(g)
    circ.draw(g)
    repaint()
  }

}
