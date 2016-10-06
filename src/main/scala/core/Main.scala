package core

import java.awt.Dimension

import scala.swing.{MainFrame, SimpleSwingApplication}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
object Main extends SimpleSwingApplication {

  val windowSize = new Dimension(800, 600)

  def top = new MainFrame {
    size = windowSize
  }

}
