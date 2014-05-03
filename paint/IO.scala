package paint
import java.io._
import java.util.Scanner
import java.awt.Color
import scala.swing.Dialog
import javax.swing.JFileChooser

class IO (main: Paint) {

  def save: Unit = {
    val fileChooser = new JFileChooser
    fileChooser.showOpenDialog(null)
    val saveFile = fileChooser.getSelectedFile
    try {
      if (saveFile == null) return
      if (!saveFile.canWrite) throw new IOException("Unable to write to file")
      saveFile.createNewFile
      val writer = new PrintWriter(saveFile)
      main.getObjects.foreach(obj => writer.println(obj.toString))
      writer.flush()
      writer.close()
    }
    catch {
      case e: Exception => {
        Dialog.showMessage(null, "Error: " + e.getMessage, "Error", Dialog.Message.Error, null)
        saveFile.delete
      }
    }
  }
  
  def load: Unit = {
    
    val fileChooser = new JFileChooser
    fileChooser.showOpenDialog(null)
    val file = fileChooser.getSelectedFile
    if (file == null) return
    if(!file.canRead()) {
      Dialog.showMessage(null, "Unable to read from file", "Error", Dialog.Message.Error, null)
      return
    }
    val scanner = new Scanner(file)
    main.clear
    while (scanner.hasNextLine()) {
      try {
        var arr = scanner.nextLine().split(",")
        arr(0) match {
          case "line" => {
            main.setCurrentColor(new Color(arr(5).toInt))
            main.setCurrentTool("line")
            main.draw(arr(1).toInt, arr(3).toInt, arr(2).toInt, arr(4).toInt)
          }
          case "text" => {
            main.setCurrentColor(new Color(arr(4).toInt))
            main.setCurrentTool("text")
            main.draw(arr(1).toInt, 0, arr(2).toInt, 0, arr(3), arr(5).toInt)
          }
          case "square" => {
            main.setCurrentColor(new Color(arr(4).toInt))
            main.setCurrentTool("square")
            main.setFill(arr(5).toBoolean)
            main.draw(arr(1).toInt, arr(1).toInt + arr(3).toInt,
                arr(2).toInt, arr(2).toInt + arr(3).toInt)
          }
          case "rectangle" => {
            main.setCurrentColor(new Color(arr(5).toInt))
            main.setCurrentTool("rectangle")
            main.setFill(arr(6).toBoolean)
            main.draw(arr(1).toInt, arr(1).toInt + arr(3).toInt,
                arr(2).toInt, arr(2).toInt + arr(4).toInt)
          }
          case "circle" => {
            main.setCurrentColor(new Color(arr(4).toInt))
            main.setCurrentTool("circle")
            main.setFill(arr(5).toBoolean)
            main.draw(arr(1).toInt, arr(1).toInt + arr(3).toInt,
                arr(2).toInt, arr(2).toInt + arr(3).toInt)
          }
          case "ellipse" => {
            main.setCurrentColor(new Color(arr(5).toInt))
            main.setCurrentTool("ellipse")
            main.setFill(arr(6).toBoolean)
            main.draw(arr(1).toInt, arr(1).toInt + arr(3).toInt,
                arr(2).toInt, arr(2).toInt + arr(4).toInt)
          }
          case _ =>
        }
      }
      catch {
        case e: Exception => e.printStackTrace()
      }
    }
    scanner.close()
  }
  
}