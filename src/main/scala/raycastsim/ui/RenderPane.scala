package raycastsim.ui

import java.awt.MouseInfo
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import raycastsim.core.RayCastSim
import raycastsim.drawable.CoordSys.{FocalPoint, SingleAxis}
import raycastsim.drawable.Ray.DottedRay.DotLoc
import raycastsim.drawable._
import raycastsim.math.Math.Point

import scala.swing.event.{MouseClicked, MouseDragged}
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

  val img = ImageIO.read(getClass.getClassLoader.getResourceAsStream("object.png"))
  val origin = new Origin(img, graph, Lens.Type.CONVERGING)
  origin.calculateRefraction()

  listenTo(mouse.moves)
  reactions += {
    case MouseDragged(_, point, _) =>
      origin.top = Point(CoordSys.p2c(point.x, SingleAxis.X), CoordSys.p2c(point.y, SingleAxis.Y))
      origin.calculateRefraction()
  }

  override def paint(g: Graphics2D): Unit = {
    super.paint(g)
    val mousePos = MouseInfo.getPointerInfo.getLocation
    g.drawString(s"Screen - X: ${mousePos.x}, Y: ${mousePos.y}", 10, 10)
    g.drawString(s"Cartesian - X: ${"%.2f".format(CoordSys.p2c(mousePos.x, SingleAxis.X))}, " +
      s"Y: ${"%.2f".format(CoordSys.p2c(mousePos.y, SingleAxis.Y))}", 10, 30)
    graph.draw(g)
    origin.draw(g)
    repaint()
  }

}
