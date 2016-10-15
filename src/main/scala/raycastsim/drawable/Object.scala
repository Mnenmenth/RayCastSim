package raycastsim.drawable

import java.awt.image.BufferedImage

import raycastsim.drawable.CoordSys.SingleAxis
import raycastsim.drawable.Ray.DottedRay.DotLoc
import raycastsim.math.Math.Point

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
  private val height = CoordSys.p2c(image.getHeight, SingleAxis.Y)
  private var _top = Point(-50.0, height)
  def top = _top
  def top_=(x: Double):Unit={
    _top = Point(x, height)
    pos = x
  }

  private var _pos = Point(-50.0, 0.0)
  def pos = _pos
  def pos_=(x: Double):Unit={
    _pos = Point(x, 0.0)
    top = x
  }

  //val refraction = new Object(image, _lensType)

  var ray1Before: Ray = new Ray.BeginEnd(Point(0.0,0.0), Point(0.0,0.0))
  var ray1After: Ray = new Ray.BeginEnd(Point(0.0,0.0), Point(0.0,0.0))

  var ray2Before: Ray = new Ray.BeginEnd(Point(0.0,0.0), Point(0.0,0.0))
  var ray2After: Ray = new Ray.BeginEnd(Point(0.0,0.0), Point(0.0,0.0))

  var ray3Before: Ray = new Ray.BeginEnd(Point(0.0,0.0), Point(0.0,0.0))
  var ray3After: Ray = new Ray.BeginEnd(Point(0.0,0.0), Point(0.0,0.0))

  val rays = List(ray1Before, ray1After, ray2Before, ray2After, ray3Before, ray3After)

  def lensType = _lensType
  def lensType_(l: Lens.Type.Value):Unit={
    _lensType = l
    if (lensType == Lens.Type.CONVERGING) {
      ray1Before = new Ray.BeginEnd(Point(0.0,0.0), Point(0.0,0.0))
      ray1After = new Ray.BeginEnd(Point(0.0,0.0), Point(0.0,0.0))
      ray2Before = new Ray.BeginEnd(Point(0.0,0.0), Point(0.0,0.0))
      ray2After = new Ray.BeginEnd(Point(0.0,0.0), Point(0.0,0.0))
      ray3Before = new Ray.BeginEnd(Point(0.0,0.0), Point(0.0,0.0))
      ray3After = new Ray.BeginEnd(Point(0.0,0.0), Point(0.0,0.0))
    } else if (lensType == Lens.Type.DIVERGING) {
      ray1Before = new Ray.DottedRay(Point(0.0,0.0), Point(0.0,0.0), DotLoc.BOTH)
      ray1After = new Ray.DottedRay(Point(0.0,0.0), Point(0.0,0.0), DotLoc.BOTH)
      ray2Before = new Ray.DottedRay(Point(0.0,0.0), Point(0.0,0.0), DotLoc.BOTH)
      ray2After = new Ray.DottedRay(Point(0.0,0.0), Point(0.0,0.0), DotLoc.BOTH)
      ray3Before = new Ray.DottedRay(Point(0.0,0.0), Point(0.0,0.0), DotLoc.BOTH)
      ray3After = new Ray.DottedRay(Point(0.0,0.0), Point(0.0,0.0), DotLoc.BOTH)
    }
  }

  def calculateRefraction(): Unit ={

    if (lensType == Lens.Type.CONVERGING) {
      ray1Before.begin = Point(top.x, top.y)
      ray1Before.end = Point(0.0, top.y)
      ray1After.begin = Point(0.0, top.y)
      ray1After.end = Point(graph.farF.pos.x, graph.farF.pos.y)
      //ray1After.extend()
    } else if (lensType == Lens.Type.DIVERGING) {

    }

  }

  override def draw(g: Graphics2D): Unit = {
    rays.foreach(_.draw(g))
    val pos = CoordSys.c2p(this.pos)
    g.drawImage(image, pos.x, pos.y, image.getWidth(), image.getHeight(), null)
  }

}
