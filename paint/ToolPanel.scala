package paint
import scala.swing._
import java.awt.Color._
import javax.swing.ImageIcon
import javax.swing.JColorChooser


class ToolPanel(x: Int, y: Int, main: Paint) extends GridPanel(x, y){
  focusable = false
  preferredSize = new Dimension(100, 500)
  minimumSize = new Dimension(100, 500)
  maximumSize = new Dimension(100, 500)
  //buttons for selecting different tools
  val tools = Vector("line", "text", "square", "square_fill", "rectangle", "rectangle_fill", "circle", "circle_fill", "ellipse", "ellipse_fill")
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
  
  //buttons for colors
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
  
  //the color chooser
  toolButtons += new Button {
    icon = new ImageIcon("src/paint/pics/color_chooser.png")//upscaled and sharpened from GIMP's select by color tool
    focusable = false
    preferredSize = new Dimension(50,50)
    listenTo(this)
    reactions += {
      case event.ButtonClicked(_) => {
        var newColor = JColorChooser.showDialog(null, "Pick a color", main.getCurrentColor)
        if (newColor != null) main.setCurrentColor(newColor)
      }
    }
  }
  contents ++= toolButtons
}