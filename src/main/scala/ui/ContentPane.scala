package ui

import core.RayCastSim
import scala.swing.event.{Key, KeyPressed}
import scala.swing.{BorderPanel, Graphics2D, Label}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
class ContentPane extends BorderPanel {
  //val renderPane = new RenderPane
  //layout(renderPane) = BorderPanel.Position.Center
  //layout(new Label("Eyy")) = BorderPanel.Position.North

  //listenTo(keys)
  //reactions += {
  //  case KeyPressed(_, Key.Escape, _, _) =>
  //    RayCastSim.quit()
  //}

  override def paint(g: Graphics2D): Unit = {
    super.paint(g)
    g.drawLine(0, 0, 400, 400)
    println("hello anyone there?")
    repaint()
  }

}
