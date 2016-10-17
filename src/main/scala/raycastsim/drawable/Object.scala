package raycastsim.drawable

import java.awt.image.BufferedImage

import raycastsim.drawable.CoordSys.FocalPoint
import raycastsim.drawable.Ray.DottedRay.DotLoc
import raycastsim.math.Math.{Point, converg, diverg}

import scala.swing.Graphics2D

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */

/** Drawable object; Origin and refracted object
  *
  * @constructor Image to be drawn as object and lens type
  * @param image Image drawn as object
  * @param _lensType Type of lens
  */

class Object(image: BufferedImage, graph: CoordSys, private var _lensType: Lens.Type.Value) extends Drawable {

  private var _top: Point[Double] = Point(-50.0, image.getHeight())
  def top: Point[Double] = _top
  def top_=(x: Double):Unit={
    _top = Point(x, image.getHeight())
    _pos = Point(x, _pos.y)
  }

  private var _pos: Point[Double] = Point(-50.0, 0.0)
  def pos: Point[Double] = _pos
  def pos_=(x: Double):Unit={
    _pos = Point(x, 0.0)
    _top = Point(x, _top.y)
  }

  //val refraction = new Object(image, _lensType)

  var ray1Before: Ray = new Ray(Point(0.0,0.0), Point(0.0,0.0))
  var ray1After: Ray = new Ray(Point(0.0,0.0), Point(0.0,0.0))

  var ray2Before: Ray = new Ray(Point(0.0,0.0), Point(0.0,0.0))
  var ray2After: Ray = new Ray(Point(0.0,0.0), Point(0.0,0.0))

  var ray3Before: Ray = new Ray(Point(0.0,0.0), Point(0.0,0.0))
  var ray3After: Ray = new Ray(Point(0.0,0.0), Point(0.0,0.0))

  def lensType = _lensType
  def lensType_=(l: Lens.Type.Value):Unit={
    _lensType = l
    if (lensType == Lens.Type.CONVERGING) {
      ray1Before = new Ray(Point(0.0,0.0), Point(0.0,0.0))
      ray1After = new Ray(Point(0.0,0.0), Point(0.0,0.0))
      ray2Before = new Ray(Point(0.0,0.0), Point(0.0,0.0))
      ray2After = new Ray(Point(0.0,0.0), Point(0.0,0.0))
      ray3Before = new Ray(Point(0.0,0.0), Point(0.0,0.0))
      ray3After = new Ray(Point(0.0,0.0), Point(0.0,0.0))
    } else if (lensType == Lens.Type.DIVERGING) {
      ray1Before = new Ray.DottedRay(Point(0.0,0.0), Point(0.0,0.0), DotLoc.BOTH)
      ray1After = new Ray.DottedRay(Point(0.0,0.0), Point(0.0,0.0), DotLoc.BOTH)
      ray2Before = new Ray.DottedRay(Point(0.0,0.0), Point(0.0,0.0), DotLoc.BOTH)
      ray2After = new Ray.DottedRay(Point(0.0,0.0), Point(0.0,0.0), DotLoc.BOTH)
      ray3Before = new Ray.DottedRay(Point(0.0,0.0), Point(0.0,0.0), DotLoc.BOTH)
      ray3After = new Ray.DottedRay(Point(0.0,0.0), Point(0.0,0.0), DotLoc.BOTH)
    }
  }

  lensType = _lensType

  def calculateRefraction(): Unit = {

    if (lensType == Lens.Type.CONVERGING) {
      ray1Before.begin = Point(top.x, top.y)
      ray1Before.end = Point(0.0, top.y)
      ray1After.begin = Point(0.0, top.y)
      ray1After.end = Point(graph.farF.pos.x, graph.farF.pos.y)
      ray1After.extend()

      ray2Before.begin = Point(top.x, top.y)
      ray2Before.end = Point(graph.nearF.pos.x, graph.nearF.pos.y)
      ray2Before.extend()

      ray2After.begin = Point(0.0, ray2Before.b)
      ray2After.end = Point(20.0, ray2Before.b)
      //ray2After.extend()

      /*ray3Before.begin = Point(top.x, top.y)
      ray3Before.end = Point(0.0, 0.0)
      ray3After.begin = ray3Before.end
      ray3After.end = intersection*/

    } else if (lensType == Lens.Type.DIVERGING) {

    }

  }

  val c = new FocalPoint(converg(top, graph.nearF.pos.x*(-1)), 20)

  override def draw(g: Graphics2D): Unit = {
    //rays.foreach(_.draw(g))
    c.draw(g)
    ray1Before.draw(g)
    ray1After.draw(g)

    ray2Before.draw(g)
    ray2After.draw(g)

    //ray3Before.draw(g)
    //ray3After.draw(g)

    val pos = CoordSys.c2p(this.pos)
    g.drawImage(image, pos.x, pos.y, image.getWidth(), -image.getHeight()*CoordSys.oneCoord, null)
  }

}
