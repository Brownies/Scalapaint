package paint
import scala.swing._
import java.awt.Color
import javax.swing.border._
class PaintPanel(x: Int, y: Int, main: Paint) extends GridPanel(1,1) {
  preferredSize = new Dimension(x,y)
  override def paintComponent(g: Graphics2D): Unit = {
    main.getObjects.foreach(elem => {
      g.setColor(elem.asInstanceOf[Colored].getColor)
      if(elem.asInstanceOf[Colored].getFill) g.fill(elem)
      else g.draw(elem)
    })
  }
}