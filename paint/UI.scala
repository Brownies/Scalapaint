package paint
import javax.swing.SwingUtilities
import javax.swing.JComponent
import javax.swing.KeyStroke
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import scala.swing._
import java.awt.Color

object UI extends SimpleSwingApplication {
  val main = new Paint
  
  
  
  /*Based on Otfried Cheong's tutorial
    http://otfried-cheong.appspot.com/scala/index_40.html
   */
  def top = new MainFrame () {
    title = "ScalaPaint"
    
    menuBar = new MenuBar() {
      contents += new Menu("File") {
        contents += new MenuItem(new Action("Quit") {
          def apply = {System.exit(0)}
          accelerator = Some(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0))
        })
        contents += new MenuItem(new Action("menu button") {
          def apply = println("foobar")
        })
      }
      
      contents += new Menu("Edit") {
        contents += new MenuItem(new Action("Undo") {
          def apply = main.undo
          accelerator = Some(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK))
        })
        contents += new MenuItem(new Action("Redo") {
          def apply = main.redo
          accelerator = Some(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK))
        })
      }
    }
    
    contents = new GridBagPanel {
      def constraints(x: Int, y: Int, gridwidth: Int = 1, gridheight: Int = 1,
                      weightx: Double = 0.0, weighty: Double = 0.0, fill: GridBagPanel.Fill.Value = GridBagPanel.Fill.None): Constraints = {
      val c = new Constraints
      c.gridx = x; c.gridy = y
      c.gridwidth = gridwidth; c.gridheight = gridheight
      c.weightx = weightx; c.weighty = weighty
      c.fill = fill
      c
    }

    val toolbar = new ToolPanel(9, 2, main)
    add(toolbar, constraints(0, 0, gridheight=2))
    
    val asd = new ScrollPane(new PaintPanel(400, 400, main))
    add(new ScrollPane(new PaintPanel(400, 400, main)),
	constraints(1, 0, gridwidth=2, gridheight=4, weightx = 1.0, weighty = 1.0, 
		    fill=GridBagPanel.Fill.Both))

  }
  centerOnScreen
  }
}