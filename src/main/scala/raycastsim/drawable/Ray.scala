package raycastsim.drawable

import raycastsim.drawable.CoordSys.SingleAxis
import raycastsim.drawable.Ray.DottedRay.Dots

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */

object Ray {

  class BeginEnd(var begin: Point[Double], var end: Point[Double]) extends Line {

  }

  class Through(var begin: Point[Double], through: Point[Double], axis: SingleAxis.Value, length: Double = 0) extends Line {
    var end: Point[Double] = through
    if(length == 0) continue(100, axis)
    else continue(length, axis)
  }

  object DottedRay {
    object Dots extends Enumeration {
      val BEGIN, END, BOTH = Value
    }
  }
  //Make the ray continue infinitly in any of the specified directions to the end of the screen
  class DottedRay(var begin: Point[Double], var end: Point[Double], dots: Dots.Value) extends Line {

  }

}