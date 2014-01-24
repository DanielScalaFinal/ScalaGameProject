package main.scala.mahjonggamecontroller

import main.scala.mahjonggameview.TextUI
import main.scala.mahjonggameview.SwingView
import main.scala.mahjonggamemodel.GameImplementation

object Mahjong {
  
  	val game = GameImplementation.create()
	val controller = new MahjongController(game)
	
  	def main (args: Array[String])	{
  	// create and attach new swing view
  	controller.attachView(new SwingView)
  	// create and attach new textual view
    val tui = new TextUI
    controller.attachView(tui)
    // run application until game exit
    while (tui.readCommand) {}
	}
  
}