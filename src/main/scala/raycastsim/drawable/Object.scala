package raycastsim.drawable

import java.awt.image.BufferedImage

import raycastsim.drawable.CoordSys.{FocalPoint, SingleAxis}
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
  def top_=(p: Point[Double]):Unit={
    _top = Point(p.x, p.y)
    _pos = Point(p.x, _pos.y)
  }

  private var _pos: Point[Double] = Point(-50.0, 0.0)
  def pos: Point[Double] = _pos
  def pos_=(x: Double):Unit={
    _pos = Point(x, _pos.y)
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

      val f = if (pos.x < 0) graph.nearF else graph.farF

      val refractionPos = converg[Double](top, if(pos.x < 0) -f.pos.x else f.pos.x)

      val toExtend = 20.0

      ray1Before.begin = Point(top.x, top.y)
      ray1Before.end = Point(0.0, top.y)
      ray1After.begin = Point(0.0, top.y)
      ray1After.end = refractionPos
      ray1After.extend(toExtend)

      ray2Before.begin = Point(top.x, top.y)
      ray2Before.end = Point(0.0, refractionPos.y)
      ray2After.begin = Point(0.0, refractionPos.y)
      ray2After.end = Point(refractionPos.x, refractionPos.y)
      ray2After.extend(toExtend)

      ray3Before.begin = Point(top.x, top.y)
      ray3Before.end = Point(0.0, 0.0)
      ray3After.begin = Point(0.0, 0.0)
      ray3After.end = Point(refractionPos.x, refractionPos.y)
      ray3After.extend(toExtend)

    } else if (lensType == Lens.Type.DIVERGING) {

    }

  }


  override def draw(g: Graphics2D): Unit = {
    ray1Before.draw(g)
    ray1After.draw(g)

    ray2Before.draw(g)
    ray2After.draw(g)

    ray3Before.draw(g)
    ray3After.draw(g)

    val pos = CoordSys.c2p(this.pos)
    g.drawImage(image, pos.x, pos.y, image.getWidth(), -top.y.toInt*CoordSys.oneCoord, null)
  }

}
