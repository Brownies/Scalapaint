package paint

import scala.collection.mutable.Buffer
import java.awt.Color
import java.awt.Shape
import java.awt.geom._
import scala.math._

class Paint {
  private var objects = Buffer[Shape]()
  
  //defaults
  private var currentColor = Color.BLACK
  private var currentTool = "square"
  private var fill = false

  def getObjects = objects
  def setCurrentColor(newColor: Color) = {currentColor = newColor}
  def setCurrentTool(newTool: String) = {currentTool = newTool}
  def setFill(a: Boolean) = {fill = a}
  
  
  def draw(x1: Double, x2: Double, y1: Double, y2: Double): Unit = {
    currentTool match {
      case "square" => drawSquare(min(x1,x2), min(y1,y2), max(abs(x1-x2), abs(y1-y2)))
      case "rectangle" => drawRectangle(min(x1,x2), min(y1,y2), abs(x1-x2), abs(y1-y2))
      case "circle" => drawCircle(min(x1,x2), min(y1,y2), max(abs(x1-x2), abs(y1-y2)))
      case "ellipse" => drawEllipse(min(x1,x2), min(y1,y2), abs(x1-x2), abs(y1-y2))
      case _ =>
    }
  }
  
  
  //helpers for draw()
  private def drawSquare(x: Double, y: Double, a: Double) = objects += new Square(x, y, a, currentColor, fill)
  private def drawRectangle(x: Double, y: Double, width: Double, height: Double) = objects += new Rectangle(x, y, width, height, currentColor, fill)
  private def drawCircle(x: Double, y: Double, d: Double) = objects += new Circle(x, y, d, currentColor, fill)
  private def drawEllipse(x: Double, y: Double, width: Double, height: Double) = objects += new Ellipse(x, y, width, height, currentColor, fill)
}

class Circle(x: Double, y: Double, d: Double, color: Color, fill: Boolean) extends Ellipse2D.Double(x, y, d, d) {}
class Ellipse(x: Double, y: Double, width: Double, height: Double, color: Color, fill: Boolean) extends Ellipse2D.Double(x, y, width, height) {}
class Square(x: Double, y: Double, a: Double, color: Color, fill: Boolean) extends Rectangle2D.Double(x, y, a, a) {}
class Rectangle(x: Double, y: Double, width: Double, height: Double, color: Color, fill: Boolean) extends Rectangle2D.Double(x, y, width, height) {}