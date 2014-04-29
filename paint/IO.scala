package paint
import java.io._
import java.util.Scanner
import java.awt.Color
import scala.swing.Dialog

class IO (main: Paint) {

  def save: Unit = {
    try {
      val filename = Dialog.showInput(null, "Enter the name of the file to be saved. Warning: this will overwrite any file with the same name",
          "Filename", Dialog.Message.Question, null, initial = ".txt").getOrElse("")
      if (filename.isEmpty()) throw new IOException("Invalid file name")
      val saveFile = new File(filename)
      saveFile.createNewFile()
      val writer = new PrintWriter(saveFile)
    
    
      main.getObjects.foreach(obj => writer.println(obj.toString()))
      writer.flush()
      writer.close()
    }
    catch {
      case e: Exception => println("something went wrong while saving." + e.getMessage())
    }
  }
  
  def load: Unit = {
    
    val filename = Dialog.showInput(null,
        "Enter the name of the file to be loaded",
        "Filename", Dialog.Message.Question, null, initial = ".txt").getOrElse("")
    if (filename.isEmpty()) throw new IOException("Invalid file name")
    val file = new File(filename)
    if(!file.canRead()) throw new IOException("Unable to read from file")
    val scanner = new Scanner(file)
    main.clear
    while (scanner.hasNextLine()) {
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
          main.draw(arr(1).toInt, 0, arr(2).toInt, 0, arr(3))
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
    scanner.close()
  }
  
}