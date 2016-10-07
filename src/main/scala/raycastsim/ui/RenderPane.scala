package raycastsim.ui

import raycastsim.core.RayCastSim
import raycastsim.render.CoordSys

import scala.swing._

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
class RenderPane extends FlowPanel {

  val graph = new CoordSys(RayCastSim.windowSize)

  override def paint(g: Graphics2D): Unit = {
    super.paint(g)
    graph.draw(g)
    repaint()
  }

}
