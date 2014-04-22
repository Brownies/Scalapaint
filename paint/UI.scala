package paint
import javax.swing.KeyStroke
import java.awt.event.KeyEvent
import scala.swing._

object UI extends SimpleSwingApplication {
  
  def top = new MainFrame () {
    title = "ScalaPaint"
    preferredSize = new Dimension(800, 600)
    
    contents = new BorderPanel() {
      preferredSize = new Dimension(615, 600)
      val testLabel = new Label("test")
      add(testLabel, BorderPanel.Position.Center)
      val toolbar = new ToolPanel(2,2)
      add(toolbar, BorderPanel.Position.West)
    }
    
    

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
    }
  }

}