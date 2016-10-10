package raycastsim.math

import scala.math._
/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */
object Math {
  def converg(pos:(Double, Double), f:Double):(Double,Double)={
    //gets the focal point on the side of the source
    val g = f * pos._1 / abs(pos._1)
    //sets the position to nice values
    val l = pos._1
    val h = pos._2
    //finds point
    val x = g*l/(g-l)
    val y = (h/l)*x
    //returns point
    (x,y)
  }
  def diverg(pos:(Double,Double), f:Double):(Double,Double)={
    //gets the focal point on the side of the source
    val g = f * pos._1 / abs(pos._1)
    //sets the position to nice values
    val l = pos._1
    val h = pos._2
    //finds point
    val x = g*l/(h*(l-f))
    val y = (h/l)*x
    //returns point
    (x,y)
  }
}
