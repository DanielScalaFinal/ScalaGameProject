package main.scala.mahjonggameview
import main.scala.mahjonggamemodel._
import main.scala.mahjonggamecontroller._


//class TextUI(var controller: MahjongController) extends View {
//  
//    val column =  Array("A ", "B ", "C ", "D ", "E ", "F ", "G ", "H ", "I ", "J ", "K ", "L ", "M ", "N ", "O ", "P ")
//    
//
//  
// def showGame(board: Board): Unit  =
//{  
//   print("   |" )
//   for(i <- 0 until column.size){
//   print("" + column(i)+"|")
//   }
//   println()
//   
//   
//	for (i <- 0 until 16){
//	  if((i) >= 9 ) {
//		  print(i+1 + " " + "|")
//	  }else{
//		  print(i+1 + "  " + "|")
//	  }
//	  for(j <- 0 until 16){
//	    if(board.area(i)(j).getId == 0 && board.area(i)(j).isValide) 
//	    	print("  |")
//	    else if(board.area(i)(j).getId < 10 && board.area(i)(j).isValide) {
//	     print(" " + board.area(i)(j).getId+"|")
//	    }else if (board.area(i)(j).isValide) {
//		  print(board.area(i)(j).getId+"|")
//	    }
//	    else{
//	      print("  " + "|")
//	    }
//	  }
//	    
//	  	  println("")
//	}
//
//}
//
//    def processInputLine() = 
//    {
//      
//      var entry: String = readLine()
//      var columnPosition1: Int = 0
//      var rowPosition1: Int = 0
//     
//      var columnPosition2: Int = 0
//      var rowPosition2: Int = 0 
//      var firstInputOK: Boolean = false
//      var secoundInputOK: Boolean = false
//      while(!firstInputOK && !secoundInputOK) 
//      {
//	      firstInputOK = false
//	      secoundInputOK = false
//	      var inputArray = entry.toCharArray()
//	      columnPosition1 = matchColumn(inputArray(0))
//	      rowPosition1 = matchRowOne(inputArray(1), inputArray(2))
//	      
//	      columnPosition2 = matchColumn(inputArray(4))
//	      rowPosition2 = matchRowOne(inputArray(5), inputArray(6))
//	      var figureNeighbour = controller.checkFigurePosition(rowPosition1, columnPosition1)
//	      
//	      if(!figureNeighbour._1 || !figureNeighbour._2){
//	        firstInputOK = true
//	      }
//	      figureNeighbour = controller.checkFigurePosition(rowPosition2, columnPosition2)
//	       if(!figureNeighbour._1 || !figureNeighbour._2){
//	        secoundInputOK = true
//	        }
//	      if(!firstInputOK || !secoundInputOK)
//	      {  
//	    	  entry = readLine()
//	      }
//      }
//      println("Output1: " + columnPosition1 +"Row: " +  rowPosition1)
//      println("Output2: " + columnPosition2 + "Row: " +  rowPosition2)
//      controller.setInputParameter(columnPosition1, rowPosition1, columnPosition2, rowPosition2)  
//     
//  }
//
//    
//    def matchColumn(indexValue: Char) : Int = {
//      var result: Int = 0
//      indexValue match 
//      {
//        case 'a' => result = 0
//        case 'A' => result = 0
//        case 'b' => result = 1
//        case 'B' => result = 1
//        case 'c' => result = 2
//        case 'C' => result = 2
//        case 'd' => result = 3
//        case 'D' => result = 3
//        case 'e' => result = 4
//        case 'E' => result = 4
//        case 'f' => result = 5
//        case 'F' => result = 5
//        case 'g' => result = 6
//        case 'G' => result = 6
//        case 'h' => result = 7
//        case 'H' => result = 7
//        case 'i' => result = 8
//        case 'I' => result = 8
//        case 'j' => result = 9
//        case 'J' => result = 9
//        case 'k' => result = 10
//        case 'K' => result = 10
//        case 'l' => result = 11
//        case 'L' => result = 11
//        case 'm' => result = 12
//        case 'M' => result = 12
//        case 'n' => result = 13
//        case 'N' => result = 13
//        case 'o' => result = 14
//        case 'O' => result = 14
//        case 'p' => result = 15
//        case 'P' => result = 15
//        case _ => result = -1    
//    }
//      result
//    }
//    
//    
//    def matchRowOne(indexValue: Char, secondValue: Char) : Int = 
//    {
//      var result: Int = 0
//       indexValue match 
//     {
//       case '0' => result = 0
//       case '1' => result = 10
//       case _ => result = -1
//     }
//      matchRowTwo(result,secondValue)
//    }
//    
//     def matchRowTwo(indexOne: Int, indexTwo: Char) : Int = 
//    {
//      var result: Int = indexOne
//       indexTwo match {
//       case '0' => result += 0
//       case '1' => result += 1
//       case '2' => result += 2
//       case '3' => result += 3
//       case '4' => result += 4
//       case '5' => result += 5
//       case '6' => result += 6
//       case '7' => result += 7
//       case '8' => result += 8
//       case '9' => result += 9
//       case _ => result = -1
//      }
//      result
//    }
//     
//     def endGame() 
//     {
//       println("Congratulation you have won")
//     }
//    
//     def printHelp {
//	    println("help: Show this help")
//	    println("q: Quit the game")
//	    println("h: Show hint")
//	    println("m: Show all moveable tiles")
//	    println("scramble: Scramble tiles and print field")
//	    println("start <setupid>: Start a new game")
//	    println("scores <setupid>: Show scoreboard for a setup")
//     }
//
//} 

import scala.actors.Actor
import util.matching.Regex

class TextUI() extends View {

  private var run = true
  private var controller:MahjongController = null

  printHelp
  
  override def autoClose = true
  
  notificationProcessor = {
    case StartHintNotification(hint) => println("Hint: " + controller.calcTileIndex(hint.tile1) + " " + controller.calcTileIndex(hint.tile2))
    case StartMoveablesNotification() => printField(true)
    case CreatedGameNotification() => printField(false)
    case WonNotification(setup, ms, inScoreBoard) => println("You cleared the field in " + (ms/1000.0) + " seconds!")
    case NoFurtherMovesNotification() => println("There are no further moves! Scramble?")
    case TilesRemovedNotification(tiles) => println("Updated field:"); printField(false)
    case ScrambledNotification() => println("Scrambled field:"); printField(false)
  }
  
  override def startView(controller:MahjongController) {
    this.controller = controller;
  }
  
  def playTiles(a:Int, b:Int) {
    if (!controller.tiles.contains(a))
      println("Could not find tile with id " + a + "!")
    else if (!controller.tiles.contains(b))
      println("Could not find tile with id " + b + "!")
    else {
      val tile1 = controller.tiles(a)
      val tile2 = controller.tiles(b)
      if (tile1 == tile2) {
        println("You have to selected two different tiles!")
      } else if (tile1.tileType.id != tile2.tileType.id) {
        println("The tiles must be of the same type!")
      } else if (!controller.canMove(tile1) || !controller.canMove(tile2)) {
        println("At least one the two selected tiles is not moveable!")
      } else {
        controller.selectTile(null)
        controller.selectTile(tile1)
        controller.selectTile(tile2)
      }
    }
  }
  
  def scramble {
    controller.scramble
  }
  
  def startGame(setupId:String) {
    val setup = controller.setupById(setupId)
    if (setup != null) {
      controller.startNewGame(setup)
    } else println("Invalid setup id!")
  }
  
  def showScores(setupId:String) {
    val setup = controller.setupById(setupId)
    if (setup != null) {
      val scores = controller.scores.getScores(setup)
      var i = 1
      for (score <- scores) {
        val time = (score.ms/1000.0)
        println("Pos: " + i + ", Seconds: " + time + ", Name: " + score.name)
        i += 1
      }
    } else println("Invalid setup id!")
  }

  def readCommand : Boolean = {
	val playRegex = new Regex("^(\\d+) (\\d+)$", "a", "b")
	val startRegex = new Regex("^start ([a-z]+?)$", "setupId")
	val scoresRegex = new Regex("^scores ([a-z]+?)$", "setupId")
	var continue = true
	readLine match {
	  case "help" => printHelp
	  case "p" => printField(false)
	  case "q" => close; continue = false
	  case "h" => printHint
	  case "m" => printMoveables
	  case "scramble" => scramble
	  case "setups" => printSetups
	  case playRegex(a, b) => playTiles(a.toInt, b.toInt)
	  case startRegex(setupId) => startGame(setupId)
	  case scoresRegex(setupId) => showScores(setupId)
	  case _ => println("Unknown command!")
	}
	continue
  }

  def printHelp {
    println("help: Show this help")
    println("q: Quit the game")
    println("p: Print game field with tiles")
    println("h: Show hint")
    println("m: Show all moveable tiles")
    println("setups: List all available setups with name and id")
    println("scramble: Scramble tiles and print field")
    println("start <setupid>: Start a new game")
    println("scores <setupid>: Show scoreboard for a setup")
    println("<Tile-Id> <Tile-Id>: Select the two tiles for removing")
    println("|-----|  Legend:")
    println("|aa bb|  aa = Tile Type")
    println("|ccccc|  bb = Height")
    println("|-----|  ccccc = Tile Id")
  }

  def splitTileIdIntoStrings(id:Int) = {
    val ifull = id.toString
    var i1:String = "  "
    var i2:String = " "
    var i3:String = "  "
      
    if (ifull.length == 1)
      i1 = ifull + " "
    else if (ifull.length == 2)
      i1 = ifull
    else if (ifull.length == 3) {
      i1 = ifull.substring(0, 2)
      i2 = ifull.substring(2, 3)
    } else if (ifull.length == 4) {
      i1 = ifull.substring(0, 2)
      i2 = ifull.substring(2, 3)
      i3 = ifull.substring(3, 4) + " "
    } else if (ifull.length >= 5) {
      i1 = ifull.substring(0, 2)
      i2 = ifull.substring(2, 3)
      i3 = ifull.substring(3, 5)
    }
    (i1,i2,i3)
  }
  
  def close {
    controller.detachView(this)
  }

  def printHint {
    controller.requestHint
  }

  def printMoveables {
    controller.requestMoveables
  }
  
  def printSetups {
    for (setup <- controller.setups) {
      println("Id: " + setup.id + ", Name: " + setup.name)
    }
  }
  
  def printField(moveablesOnly : Boolean) {
    // TODO: Split into smaller methods
    val line = "-" * (controller.fieldWidth * 3 + 2)
    println(line)
    var leftTile:Tile = null
    for (y <- 0 until controller.fieldHeight; x <- 0 until controller.fieldWidth) {
      if (x == 0) print("|")
      
      val tile = controller.topmostTile(x, y)
      if (tile == null) {
        if (leftTile == null) print("   ") else print("|  ")
      } else {
        val moveable = controller.canMove(tile)
        val id = controller.calcTileIndex(tile)
        val (i1, i2, i3) = splitTileIdIntoStrings(id)
        
        var center = " "
        if (y == tile.y || y == tile.y + Tile.Height-1)
          center = "-"
        else if (moveablesOnly && !moveable)
          center = " "
        else if (tile != null && tile.y == y-2)
          center = i2
        
        if (leftTile == tile)
          print(center)
        else
          print("|")
        
        if ((tile.x == x || tile.x + Tile.Width-1 == x) && (tile.y == y || tile.y + Tile.Height - 1 == y))
          print("--")
        else if (moveablesOnly && !moveable)
          print("  ")
        else if (tile.y == y-1 && tile.x == x-1)
          print("%2d".format(tile.z))
        else if (tile.y == y-1 && tile.x == x)
          print("%2d".format(tile.tileType.id))
        else if (tile.y == y-2 && tile.x == x)
          print(i1)
        else if (tile.y == y-2 && tile.x == x-1)
          print(i3)
        else
          print("  ")
      }
      
      if (x == controller.fieldWidth - 1) {
        print("|\n")
        leftTile = null
      } else {
        leftTile = tile
      }
    }
    println(line)
  }
}