package paint
import javax.swing.KeyStroke
import java.awt.event.KeyEvent
import scala.swing._
import java.awt.Color

object UI extends SimpleSwingApplication {
  
  /*pohjana käytetty Otfried Cheongin tutoriaalia,
    eritoten http://otfried-cheong.appspot.com/scala/index_40.html
   */
  def top = new MainFrame () {
    title = "ScalaPaint"
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

    val toolbar = new ToolPanel(6, 2)
    add(toolbar, constraints(0, 0, gridheight=2))
    
    add(new ScrollPane(new PaintPanel(400, 400)),
	constraints(1, 0, gridwidth=2, gridheight=4, weightx = 1.0, weighty = 1.0, 
		    fill=GridBagPanel.Fill.Both))
  }
  }

}