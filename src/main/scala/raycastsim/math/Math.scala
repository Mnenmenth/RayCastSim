package raycastsim.math

import scala.math._
/**
  * Created by Creatorri
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Creatorri
  */
object Math {
  case class Point[T:Numeric](var x: T, var y: T)

  def converg(pos:(Double, Double), f:Double):Point[Double]={
    //gets the focal point on the side of the source
    val g = f * pos._1 / abs(pos._1)
    //sets the position to nice values
    val l = pos._1
    val h = pos._2
    //finds point
    val x = g*l/(g-l)
    val y = (h/l)*x
    //returns point
    Point(x,y)
  }

  def diverg(pos:Point[Double], f:Double):Point[Double]={
    //gets the focal point on the side of the source
    val g = f * pos.x / abs(pos.y)
    //sets the position to nice values
    val l = pos.x
    val h = pos.y
    //finds point
    val x = g*l/(h*(l-f))
    val y = (h/l)*x
    //returns point
    Point(x,y)
  }
}
