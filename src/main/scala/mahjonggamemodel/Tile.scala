package main.scala.mahjonggamemodel


object Tile {
  val Width = 2
  val Height = 4
  val Depth = 1
}

class Point(val x:Int, val y:Int, val z:Int)

class TilePair(val tile1:Tile, val tile2:Tile)

class Tile(val x:Int, val y:Int, val z:Int, var tileType:TileType) {
  
  def isInside(xf:Double, yf:Double) : Boolean = {
    xf >= x && yf >= y && xf < x + Tile.Width && yf < y + Tile.Height
  }

  def isInside(xf:Double, yf:Double, zf:Double) : Boolean = {
    isInside(xf, yf) && zf >= z && zf < z + Tile.Depth
  }

  def testPoints : Array[Point] = {
    var points : Array[Point] = new Array(Tile.Width * Tile.Height * Tile.Depth)
    var p = 0
    for (xp <- 0 until Tile.Width; yp <- 0 until Tile.Height; zp <- 0 until Tile.Depth) {
      points(p) = new Point(x + xp, y + yp, z + zp);
      p += 1
    }
    points
  }

  override def toString = {
    "Tile[" + x + "," + y + "," + z + "," + tileType + "]"
  }
}