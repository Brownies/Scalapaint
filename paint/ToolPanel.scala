package paint
import scala.swing._
import java.awt.Color._
import javax.swing.ImageIcon


class ToolPanel(x: Int, y: Int, main: Paint) extends GridPanel(x, y){
  focusable = false
  preferredSize = new Dimension(100, 400)
  minimumSize = new Dimension(100, 400)
  maximumSize = new Dimension(100, 400)
  val tools = Vector("square", "square_fill", "rectangle", "rectangle_fill", "circle", "circle_fill", "ellipse", "ellipse_fill")
  val toolButtons = scala.collection.mutable.Buffer[Button]()
  tools.foreach(tool => toolButtons += new Button {
    icon = new ImageIcon("src/paint/pics/" + tool + ".png")//this must be changed if not using eclipse
    focusable = false
    preferredSize = new Dimension(50,50)
    val tooltext = tool.split('_')
    listenTo(this)
    reactions += {
      case event.ButtonClicked(_) => {
        main.setCurrentTool(tooltext(0))
        if(tooltext.size == 1) main.setFill(false)
        else main.setFill(true)
      }
    }
  })
  
  val colors = Vector(BLACK, WHITE, GRAY, DARK_GRAY, GREEN, RED, BLUE, YELLOW)
  colors.foreach(color => toolButtons += new Button {
    background = color
    focusable = false
    preferredSize = new Dimension(50,50)
    listenTo(this)
    reactions += {
      case event.ButtonClicked(_) => main.setCurrentColor(color)
    }
  })
  
  contents ++= toolButtons
}