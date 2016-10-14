package raycastsim.ui

import raycastsim.core.RayCastSim
import scala.swing.event.{Key, KeyPressed}
import scala.swing.BorderPanel

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */

/**
  * Root pane for contents
  */
class ContentPane extends BorderPanel {
  val renderPane = new RenderPane
  layout(renderPane) = BorderPanel.Position.Center

  listenTo(keys)
  reactions += {
    case KeyPressed(_, Key.Escape, _, _) =>
      RayCastSim.quit()
  }

  focusable = true
  requestFocus()

}
