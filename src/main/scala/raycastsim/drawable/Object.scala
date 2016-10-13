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
class Object(image: BufferedImage, private var _lensType: Lens.Type.Value) extends Drawable {

  private var _top = Point(50.0, CoordSys.p2c(image.getHeight, SingleAxis.Y))
  def top = _top
  def top_=(x: Double):Unit={
    _top = Point(x, CoordSys.p2c(image.getHeight, SingleAxis.Y))
    pos = x
  }

  private var _pos = Point(50.0, 0.0)
  def pos = _pos
  def pos_=(x: Double):Unit={
    _pos = Point(x, 0.0)
    top = x
  }

  val refraction = new Object(image, _lensType)

  //Before and after lens

  var ray1Before: Ray = _
  var ray1After: Ray = _

  var ray2Before: Ray = _
  var ray2After: Ray = _

  var ray3Before: Ray = _
  var ray3After: Ray = _

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

  def calculateRefraction(lens: Lens.Type.Value): Unit ={

    if (lens == Lens.Type.CONVERGING) {

    } else if (lens == Lens.Type.DIVERGING) {

    }

  }

  override def draw(g: Graphics2D): Unit = {
    rays.foreach(_.draw(g))
    val pos = CoordSys.c2p(pos)
    g.drawImage(image, pos.x.toInt, pos.y.toInt, image.getWidth(), image.getHeight(), null)
  }

}
