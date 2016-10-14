package raycastsim.math

import scala.math._
/**
  * Created by Creatorri
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Creatorri
  */
object Math {

  /**
    * Point
    * @param x X Coordinate
    * @param y Y Coordinate
    * @param numT Implicit numeric type
    * @tparam T Implicit numeric type
    */
  case class Point[T](var x: T, var y: T)(implicit numT:Numeric[T]){
    def +(p:Point[T]): Point[T] ={
      Point(numT.plus(x,p.x),numT.plus(y,p.y))
    }
    def -(p:Point[T]): Point[T]={
      Point(numT.minus(x,p.x),numT.minus(y,p.y))
    }
  }

  /**
    * Calculates point of ray intersection through converging lens
    * @param pos Origin point
    * @param f Focal length
    * @return Point of intersection
    */
  def converg(pos:Point[Double], f:Double):Point[Double]={
    //gets the focal point on the side of the source
    val g = f * pos.x / abs(pos.x)
    //sets the position to nice values
    val l = pos.y
    val h = pos.y
    //finds point
    val x = g*l/(g-l)
    val y = (h/l)*x
    //returns point
    Point(x,y)
  }

  /**
    * Calculates point of ray intersection through diverging lens
    * @param pos Origin point
    * @param f Focal length
    * @return Point of intersection
    */
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
