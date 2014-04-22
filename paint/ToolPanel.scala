package paint
import scala.swing._
import java.awt.Color._
import java.awt.Rectangle


class ToolPanel(x: Int, y: Int/*, paint: Paint*/) extends GridPanel(x, y){
  focusable = false
  preferredSize = new Dimension(100, 300)
  minimumSize = new Dimension(100, 300)
  maximumSize = new Dimension(100, 300)
  val tools = Vector("square", "rectangle", "circle", "ellipse")//TODO: change strings to images
  val toolButtons = scala.collection.mutable.Buffer[Button]()
  tools.foreach(tool => toolButtons += new Button {
    text = tool
    focusable = false
    preferredSize = new Dimension(50,50)
//    minimumSize = new Dimension(50,50)
//    maximumSize = new Dimension(50,50)
  })
  
  val colors = Vector(BLACK, WHITE, GRAY, DARK_GRAY, GREEN, RED, BLUE, YELLOW)
  colors.foreach(color => toolButtons += new Button {
    background = color
    focusable = false
    preferredSize = new Dimension(50,50)
//    minimumSize = new Dimension(50,50)
//    maximumSize = new Dimension(50,50)
  })
  
  contents ++= toolButtons
}