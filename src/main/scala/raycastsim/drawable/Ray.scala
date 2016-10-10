package raycastsim.drawable

/**
  * Created by Mnenmenth Alkaborin
  * Please refer to LICENSE file if included
  * for licensing information
  * https://github.com/Mnenmenth
  */

object Ray {

  class BeginEnd(var begin: Point[Double], var end: Point[Double]) extends Line {

  }

  class Through(var begin: Point[Double], through: Point[Double], length: Double = 0) extends Line {
    var end: Point[Double] = through
    //if(length == 0) continue(0)
  }

}