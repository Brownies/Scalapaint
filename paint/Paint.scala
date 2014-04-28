package paint

import scala.collection.mutable.Buffer
import scala.collection.mutable.Stack
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
  def clear = objects.clear
  
  def draw(x1: Double, x2: Double, y1: Double, y2: Double, stringForText: String = ""): Unit = {
    currentTool match {
      case "square" => drawSquare(min(x1,x2), min(y1,y2), max(abs(x1-x2), abs(y1-y2)))
      case "rectangle" => drawRectangle(min(x1,x2), min(y1,y2), abs(x1-x2), abs(y1-y2))
      case "circle" => drawCircle(min(x1,x2), min(y1,y2), max(abs(x1-x2), abs(y1-y2)))
      case "ellipse" => drawEllipse(min(x1,x2), min(y1,y2), abs(x1-x2), abs(y1-y2))
      case "line" => drawLine(x1, y1, x2, y2)
      case "text" => drawText(x1, y1, stringForText)
      case _ =>
    }
  }
  
  
  //helpers for draw()
  private def drawLine(x1: Double, y1: Double, x2: Double, y2: Double) = {
    objects += new Line(x1, y1, x2, y2, currentColor)
    redoStack.clear
  }
  
  private def drawText(x: Double, y: Double, s: String) = {
    if (s.isEmpty()) {
      var string = Dialog.showInput(null, "Please enter the the text",
        "Text", Dialog.Message.Question, null, initial = "").getOrElse("")
      if (string != "") {
        objects += new Text(x.toFloat, y.toFloat, string, currentColor)
        redoStack.clear
      }
    }
    else objects += new Text(x.toFloat, y.toFloat, s, currentColor); redoStack.clear
  }
  
  private def drawSquare(x: Double, y: Double, a: Double) = {
    objects += new Square(x, y, a, currentColor, fill)
    redoStack.clear
  }
  
  private def drawRectangle(x: Double, y: Double, width: Double, height: Double) = {
    objects += new Rectangle(x, y, width, height, currentColor, fill)
    redoStack.clear
  }
  
  private def drawCircle(x: Double, y: Double, d: Double) = {
    objects += new Circle(x, y, d, currentColor, fill)
    redoStack.clear
  }
  
  private def drawEllipse(x: Double, y: Double, width: Double, height: Double) = {
    objects += new Ellipse(x, y, width, height, currentColor, fill)
    redoStack.clear
  }
  

  private var redoStack = Stack[Colored]()
  def undo = {
    if (objects.nonEmpty) {
      redoStack.push(objects.last)
      objects.trimEnd(1)
    }
  }
  
  def redo = {
    if (redoStack.nonEmpty) objects += redoStack.pop
  }
}

trait Colored {def getColor: Color; def getFill: Boolean}

class Line(x1: Double, y1: Double, x2: Double, y2: Double, color: Color)
           extends Line2D.Double(x1, y1, x2, y2) with Colored {
  def getColor = color; def getFill = false
  override def toString: String = {
    "line," + Array[Int](x1.toInt,y1.toInt,x2.toInt,y2.toInt,color.getRGB).mkString(",")
  }
}


class Text(val x: Float, val y: Float, val string: String, color: Color) extends Colored {
  def getColor = color; def getFill = false;
  override def toString = {
    "text," + Array[AnyVal](x.toInt, y.toInt, string, color.getRGB).mkString(",")
  }
}


class Circle(x: Double, y: Double, d: Double, color: Color, fill: Boolean) 
             extends Ellipse2D.Double(x, y, d, d) with Colored {
  def getColor = color; def getFill = fill
  override def toString = {
    "circle," + Array[AnyVal](x.toInt, y.toInt, d.toInt, color.getRGB, fill.toString).mkString(",")
  }
}


class Ellipse(x: Double, y: Double, width: Double, height: Double, color: Color, fill: Boolean)
              extends Ellipse2D.Double(x, y, width, height) with Colored {
  def getColor = color; def getFill = fill
  override def toString = {
    "ellipse," + Array[AnyVal](x.toInt, y.toInt, width.toInt, height.toInt, color.getRGB, fill.toString).mkString(",")
  }
}


class Square(x: Double, y: Double, a: Double, color: Color, fill: Boolean)
             extends Rectangle2D.Double(x, y, a, a) with Colored {
  def getColor = color; def getFill = fill
  override def toString = {
    "square," + Array[AnyVal](x.toInt, y.toInt, a.toInt, color.getRGB, fill.toString).mkString(",")
  }
}


class Rectangle(x: Double, y: Double, width: Double, height: Double, color: Color, fill: Boolean)
                extends Rectangle2D.Double(x, y, width, height) with Colored {
  def getColor = color; def getFill = fill
  override def toString = {
    "rectangle," + Array[AnyVal](x.toInt, y.toInt, width.toInt, height.toInt, color.getRGB, fill.toString).mkString(",")
  }
}