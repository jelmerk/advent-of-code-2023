package util.geometry

case class Area(topLeft: Coordinates, bottomRight: Coordinates) {

  def intersects(other: Area): Boolean = {
    !(topLeft.x > other.bottomRight.x || bottomRight.x < other.topLeft.x ||
      topLeft.y > other.bottomRight.y || bottomRight.y < other.topLeft.y)
  }

  def isAdjacentTo(other: Area): Boolean = {
    // TODO implement this properly instead of bruteforcing it
    val candidates = for {
      xx <- topLeft.x - 1 to bottomRight.x + 1
      yy <- topLeft.y - 1 to bottomRight.y + 1
    } yield Coordinates(xx, yy)

    candidates.toSet.contains(other.topLeft) || candidates.contains(other.bottomRight)
  }
}
