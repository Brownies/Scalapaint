package paint
import scala.swing._
import java.awt.Color
import javax.swing.border._
import java.awt.Shape
import java.awt.event.MouseEvent

//a PaintPanel is the canvas that the user will paint on
class PaintPanel(x: Int, y: Int, main: Paint) extends GridPanel(1,1) {
  
  preferredSize = new Dimension(x,y)
  listenTo(mouse.clicks, mouse.moves)
  var x1, x2, y1, y2 = 0D
  var lmb = false
  this.reactions += {
    case a: event.MousePressed => {
      if(a.peer.getButton() == MouseEvent.BUTTON1) {
      this.x1 = a.point.getX; this.y1 = a.point.getY; main.setPreview(true)
      lmb = true
      }
      else lmb = false
    }
    
    
    case b: event.MouseDragged => if(lmb) {
      this.x2 = b.point.getX(); this.y2 = b.point.getY()
      main.draw(this.x1, this.x2, this.y1, this.y2)
      this.repaint
    }
      
    case c: event.MouseReleased => {
      if (c.peer.getButton() == MouseEvent.BUTTON1) {
        main.setPreview(false)
        this.x2 = c.point.getX; this.y2 = c.point.getY
        main.draw(this.x1, this.x2, this.y1, this.y2)
        this.repaint
      }
    }
  }
  
  override def paintComponent(g: Graphics2D): Unit = {
    g.setColor(Color.WHITE)
    g.fillRect(0, 0, this.size.getWidth.toInt, this.size.getHeight.toInt)
    
    main.getObjects.foreach(elem => {
      g.setColor(elem.getColor)
      if(elem.isInstanceOf[Shape]) {
        if(elem.getFill) g.fill(elem.asInstanceOf[Shape])
        else g.draw(elem.asInstanceOf[Shape])
      }
      
      else {
        var elem2 = elem.asInstanceOf[Text]
        var f = g.getFont()
        g.setFont(new Font(f.getName, f.getStyle, elem2.size))
        g.drawString(elem2.string, elem2.x, elem2.y)
      }
    })
    
    if (main.isPreview){
      g.setColor(main.getPreviewElement.getColor)
      if (main.getPreviewElement.getFill) {
        g.fill(main.getPreviewElement.asInstanceOf[Shape])
      }
      else g.draw(main.getPreviewElement.asInstanceOf[Shape])
    }
  }
}