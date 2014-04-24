package paint

import scala.collection.mutable.Buffer
import java.awt.Color
import java.awt.Shape
import java.awt.geom._
import scala.math._
import scala.swing.Dialog

class Paint {
  private var objects = Buffer[Colored]()
  
  //defaults
  private var currentColor = Color.BLACK
  private var currentTool = "square"
  private var fill = false

  def getObjects = objects
  def setCurrentColor(newColor: Color) = {currentColor = newColor}
  def setCurrentTool(newTool: String) = {currentTool = newTool}
  def setFill(a: Boolean) = {fill = a}
  
  def getTool = currentTool
  def draw(x1: Double, x2: Double, y1: Double, y2: Double): Unit = {
    currentTool match {
      case "square" => drawSquare(min(x1,x2), min(y1,y2), max(abs(x1-x2), abs(y1-y2)))
      case "rectangle" => drawRectangle(min(x1,x2), min(y1,y2), abs(x1-x2), abs(y1-y2))
      case "circle" => drawCircle(min(x1,x2), min(y1,y2), max(abs(x1-x2), abs(y1-y2)))
      case "ellipse" => drawEllipse(min(x1,x2), min(y1,y2), abs(x1-x2), abs(y1-y2))
      case "line" => drawLine(x1, y1, x2, y2)
      case "text" => drawText(x1, y1)
      case _ =>
    }
  }
  
  
  //helpers for draw()
  private def drawLine(x1: Double, y1: Double, x2: Double, y2: Double) = {objects += new Line(x1, y1, x2, y2, currentColor)}
  private def drawText(x: Double, y: Double) = {
    val string = Dialog.showInput(null, "Please enter the the text", "Text", Dialog.Message.Question, null, initial = "3").get
    objects += new Text(x.toFloat, y.toFloat, string, currentColor)
  }
  private def drawSquare(x: Double, y: Double, a: Double) = {objects += new Square(x, y, a, currentColor, fill)}
  private def drawRectangle(x: Double, y: Double, width: Double, height: Double) = {objects += new Rectangle(x, y, width, height, currentColor, fill)}
  private def drawCircle(x: Double, y: Double, d: Double) = {objects += new Circle(x, y, d, currentColor, fill)}
  private def drawEllipse(x: Double, y: Double, width: Double, height: Double) = {objects += new Ellipse(x, y, width, height, currentColor, fill)}
}

trait Colored {def getColor: Color; def getFill: Boolean}

class Line(x1: Double, y1: Double, x2: Double, y2: Double, color: Color)
           extends Line2D.Double(x1, y1, x2, y2) with Colored {
  def getColor = color; def getFill = false
}


class Text(val x: Float, val y: Float, val string: String, color: Color) extends Colored {
  def getColor = color; def getFill = false;
}


class Circle(x: Double, y: Double, d: Double, color: Color, fill: Boolean) 
             extends Ellipse2D.Double(x, y, d, d) with Colored {
  def getColor = color; def getFill = fill
}


class Ellipse(x: Double, y: Double, width: Double, height: Double, color: Color, fill: Boolean)
              extends Ellipse2D.Double(x, y, width, height) with Colored {
  def getColor = color; def getFill = fill
}


class Square(x: Double, y: Double, a: Double, color: Color, fill: Boolean)
             extends Rectangle2D.Double(x, y, a, a) with Colored {
  def getColor = color; def getFill = fill
}


class Rectangle(x: Double, y: Double, width: Double, height: Double, color: Color, fill: Boolean)
                extends Rectangle2D.Double(x, y, width, height) with Colored {
  def getColor = color; def getFill = fill
}