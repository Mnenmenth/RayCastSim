package core

import java.awt.{Dimension, Toolkit}

import ui.ContentPane

import scala.swing.{MainFrame, SimpleSwingApplication}

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
object RayCastSim extends SimpleSwingApplication {

  val width = Toolkit.getDefaultToolkit.getScreenSize.width
  val height = Toolkit.getDefaultToolkit.getScreenSize.height

  val windowSize = new Dimension(width, height)

  /*private var _contentPane = new ContentPane
  def contentPane = _contentPane
  def contentPane_=(c: ContentPane)={
    _contentPane = c
    top.contents = contentPane
  }*/

  val contentPane = new ContentPane

  def top = new MainFrame {
    size = windowSize
    minimumSize = windowSize
    peer.setUndecorated(true)
    contents = contentPane
  }

}
