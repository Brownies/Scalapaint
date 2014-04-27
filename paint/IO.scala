package paint
import java.io.File
import scala.swing.Dialog

class IO (main: Paint) {

  def save: Unit = {
    val filename = Dialog.showInput(null, "Enter the name of the file to be saved", "Filename", Dialog.Message.Question, null, initial = ".txt").getOrElse("")
    if (filename == "") return
    val saveFile = new File(filename)
    saveFile.createNewFile()
    
    
    main.getObjects.foreach(obj => {
      var saveString = ""
      
      //couldn't figure how to match types
      //so match to strings
      obj.getClass().toString() match {
        case "class paint.Square" => {
          val square = obj.asInstanceOf[Square]
          
        }
        case _ =>
      }
    })
  }
  
  def load = ???
  
}