package paint
import scala.swing._

class ToolPanel(x: Int, y: Int/*, paint: Paint*/) extends GridPanel(x, y){
  focusable = false
  val tools = Vector("square", "rectangle", "circle", "ellipsis")
  val toolButtons = scala.collection.mutable.Buffer[Button]()
  
  tools.foreach(tool => toolButtons += new Button {text = tool; focusable = false})
  
  toolButtons.foreach(button => contents += button)
}