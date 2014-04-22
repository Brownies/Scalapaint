package paint
import scala.swing._
import java.awt.Color
import javax.swing.border._
class PaintPanel(x: Int, y: Int/*paint: Paint*/) extends GridPanel(1,1) {
  preferredSize = new Dimension(x,y)
  override def paintComponent(g: Graphics2D) = {
    g.setColor(Color.BLACK)
    g.fillRect(0, 0, x, y)
  }
}