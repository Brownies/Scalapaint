package paint
import javax.swing.KeyStroke
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent
import scala.swing._

object UI extends SimpleSwingApplication {

  val main = new Paint
  val io = new IO(main)
  
  def top = frame.pack
  val frame: MainFrame = new MainFrame () {
    title = "ScalaPaint"
    
    menuBar = new MenuBar() {
      contents += new Menu("File") {
        contents += new MenuItem(new Action("New") {
          def apply = {
            main.clear
            //repaint the canvas
            frame.contents.head.asInstanceOf[GridBagPanel].contents(1).repaint
          }
          accelerator = Some(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK))
        })
        contents += new MenuItem(new Action("Save") {
          def apply = {
            io.save
            frame.contents.head.asInstanceOf[GridBagPanel].contents(1).repaint
          }
        })
        contents += new MenuItem(new Action("Load") {
          def apply = {
            io.load
            frame.contents.head.asInstanceOf[GridBagPanel].contents(1).repaint
          }
        })
        contents += new MenuItem(new Action("Quit") {
          def apply = {System.exit(0)}
          accelerator = Some(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0))
        })
      }
      
      contents += new Menu("Edit") {
        contents += new MenuItem(new Action("Undo") {
          def apply = {
            main.undo
            frame.contents.head.asInstanceOf[GridBagPanel].contents(1).repaint
          }
          accelerator = Some(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK))
        })
        contents += new MenuItem(new Action("Redo") {
          def apply = {
            main.redo
            frame.contents.head.asInstanceOf[GridBagPanel].contents(1).repaint
          }
          accelerator = Some(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK))
        })
      }
    }

    /*Based on Otfried Cheong's tutorial
    http://otfried-cheong.appspot.com/scala/index_40.html
    */
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

    val toolbar = new ToolPanel(10, 2, main)
    add(toolbar, constraints(0, 0, gridheight=2))
    
    
    add(new ScrollPane(new PaintPanel(800, 600, main)),
	constraints(1, 0, gridwidth=2, gridheight=4, weightx = 1.0, weighty = 1.0, 
		    fill=GridBagPanel.Fill.Both))

  }
  centerOnScreen
  }
}