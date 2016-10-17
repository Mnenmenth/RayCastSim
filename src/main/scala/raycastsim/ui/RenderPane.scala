package raycastsim.ui

import java.awt.MouseInfo
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import raycastsim.core.RayCastSim
import raycastsim.drawable.CoordSys.{FocalPoint, SingleAxis}
import raycastsim.drawable.Ray.DottedRay.DotLoc
import raycastsim.drawable.{CoordSys, Lens, Object, Ray}
import raycastsim.math.Math.Point

import scala.swing.{Graphics2D, Panel}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
/**
  * Panel containing all draw commands
  */
class RenderPane extends Panel {

  val graph = new CoordSys(RayCastSim.windowSize)

  val ray1 = new Ray(
    Point[Double](40, 30),
    Point[Double](80, 60)
  )
  val ray2 = new Ray(
    Point[Double](61, 31),
    Point[Double](41, 81)
  )

  val ray3 = new Ray(
    Point[Double](-40, 30),
    Point[Double](-80, 60)
  )
  val ray4 = new Ray(
    Point[Double](-61, 31),
    Point[Double](-41, 81)
  )

  val ray5 = new Ray(
    Point[Double](-40, -30),
    Point[Double](-80, -60)
  )

  val ray6 = new Ray(
    Point[Double](-61, -31),
    Point[Double](-41, -81)
  )

  val ray7 = new Ray.DottedRay(
    Point[Double](30, -30),
    Point[Double](80, -60),
    DotLoc.BOTH
  )

  val ray8 = new Ray(
    Point[Double](61, -31),
    Point[Double](41, -81)
  )

  val rayT = new Ray.DottedRay(
    Point[Double](30, -30),
    Point[Double](80, -60),
    DotLoc.BOTH
  )
  //rayT.extend(30)
  rayT.extendDotted(1000)

  ray1.extendAlong(SingleAxis.X, 40)
  ray3.extendAlong(SingleAxis.X, 40)
  ray5.extendAlong(SingleAxis.X, 40)
  //ray7.extendAlong(SingleAxis.X, 40)
  //ray7.extendDotted(10)
  val circ = new FocalPoint(ray1.intersection(ray2), 10)
  val circ1 = new FocalPoint(ray3.intersection(ray4), 10)
  val circ2 = new FocalPoint(ray5.intersection(ray6), 10)
  val circ3 = new FocalPoint(ray7.intersection(ray8), 10)
  val circdusoleil = new FocalPoint(raycastsim.math.Math.diverg(Point(-50,20), 25), 30)

  val img = ImageIO.read(getClass.getClassLoader.getResourceAsStream("object.png"))
  val origin = new Object(img, graph, Lens.Type.CONVERGING)
  origin.calculateRefraction()

  override def paint(g: Graphics2D): Unit = {
    super.paint(g)
    val mousePos = MouseInfo.getPointerInfo.getLocation
    g.drawString(s"Screen - X: ${mousePos.x}, Y: ${mousePos.y}", 10, 10)
    g.drawString(s"Cartesian - X: ${"%.2f".format(CoordSys.p2c(mousePos.x, SingleAxis.X))}, " +
      s"Y: ${"%.2f".format(CoordSys.p2c(mousePos.y, SingleAxis.Y))}", 10, 30)
    graph.draw(g)
    //rayT.draw(g)
    //ray7.draw(g)
    origin.draw(g)
    circdusoleil.draw(g)
    /*ray1.draw(g)
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
    circ3.draw(g)*/
    repaint()
  }

}
