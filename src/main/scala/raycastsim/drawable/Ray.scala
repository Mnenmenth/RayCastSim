package raycastsim.drawable

import raycastsim.drawable.CoordSys.SingleAxis

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

}