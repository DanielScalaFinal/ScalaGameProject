package main.scala.mahjonggamecontroller

/*
 * imports needed for viewer synchronisation matters 
 */
import scala.util.Random
import scala.actors._
import scala.actors.Actor._
import main.scala.mahjonggamemodel._
import main.scala.mahjonggameview.View
import main.scala.mahjonggameutil.SimplePublisher
import main.scala.mahjonggamemodel.TilePair
import main.scala.mahjonggamemodel.Tile
import main.scala.mahjonggamemodel.Game
import main.scala.mahjonggamemodel.Setup

/*
 * Controller Pattern of the mahjong game
 */
class MahjongController(val game:Game) extends SimplePublisher {
  
  private var views = List[View]()		// contains all views
  private var selected:Tile = null		// outlook of the pawns in the game
  
  /*
   * attach a new view into the list
   */
  def attachView(view:View) {
    this.addSubscriber(view)
    view.startView(this)
    views = view :: views
  }
  
  /*
   * detach a view from the list
   */
  def detachView(view:View) {
    view.stopView(this)
    this.remSubscriber(view)
    views = views.filter(v => v != view)
    val withoutAutoClose = views.filter(!_.autoClose)
    if (withoutAutoClose.length == 0) {
      views.foreach(_.stopView(this))
      closeApplication
    }
  }
  /* get scores */
  def scores = game.scores
  /* get a tile */
  def tiles = game.tiles
  /* get playing field */ 
  def setups = game.setups
  /* get types of tiles */
  def tileTypes = game.tileTypes
  /* get field height */
  def fieldHeight = game.height
  /* get field width */
  def fieldWidth = game.width
  /* check move */
  def canMove(tile:Tile) = game.canMove(tile)
  /* compute tile index */
  def calcTileIndex(tile:Tile) = game.calcTileIndex(tile)
  /* set setup */
  def setupById(id:String) = game.setupById(id)
  /* check tile */
  def topmostTile(x:Int, y:Int) = game.topmostTile(x, y)
  /* support feature hint */
  def hint = game.hint
  /* sort tiles */
  def sortedTiles = game.sortedTiles
  /* find tile */
  def findTile(x:Int, y:Int, z:Int) = game.findTile(x, y, z)
  
  /* 
   * start a new game 
   */
  def startNewGame(setup:Setup) {
    game.startNewGame(setup)
    sendNotification(new CreatedGameNotification)
    sendNotification(new StopHintNotification)
    sendNotification(new StopMoveablesNotification)
  }
  
  /* 
   * scramble the setup of the tiles on the playing field 
   */
  def scramble {
    game.scramble
    sendNotification(new ScrambledNotification)
  }
  
  /* 
   * give a hint for next possible move 
   */
  def requestHint {
    val hint = game.hint
    if (hint != null) {
      game.addHintPenalty
      sendNotification(new StartHintNotification(hint))
      new Actor {
        def act {
          reactWithin(Game.HintTimeout) {
            case TIMEOUT => sendNotification(new StopHintNotification)
          }
        }
      }.start();
    }
  }
  
  /* 
   * check move 
   */
  def requestMoveables {
    game.addMoveablesPenalty
    sendNotification(new StartMoveablesNotification)
    new Actor {
      def act {
        reactWithin(Game.MoveablesTimeout) {
          case TIMEOUT => sendNotification(new StopMoveablesNotification)
        }
      }
    }.start();
  }

  /* 
   * select the tile
   */
  def selectTile(newSelectedTile:Tile) {
    if (newSelectedTile == null) {
      if (selected != null) {
        selected = null
        sendNotification(new TileSelectedNotification(selected))
      }
    } else if (newSelectedTile == selected) {
      // Nothing to do!
    } else if (game.canMove(newSelectedTile)) {
      if (selected != null && selected.tileType == newSelectedTile.tileType) {
        val selectedTile = selected
        selected = null
        sendNotification(new TileSelectedNotification(selected))
        playTilePair(selectedTile, newSelectedTile)
      } else {
        selected = newSelectedTile
        sendNotification(new TileSelectedNotification(selected))
      }
    }
  }
  
  /* 
   * check if both picked tiles have the same id 
   */
  private def playTilePair(tile1:Tile, tile2:Tile) {
    if (game.play(tile1, tile2)) {
      sendNotification(new TilesRemovedNotification(new TilePair(tile1, tile2)))
      if (game.tiles.size == 0) {
        val time = game.gameTime
        val inScoreBoard = game.scores.isInScoreboard(game.setup, time)
        sendNotification(new WonNotification(game.setup, time, inScoreBoard))
      } else if (!game.nextMovePossible) {
        sendNotification(new NoFurtherMovesNotification)
      }
    }
  }

  /* 
   * save score 
   */
  def addScore(setup:Setup, playerName:String, ms:Int) {
    val position = game.scores.addScore(setup, playerName, ms)
    sendNotification(new NewScoreBoardEntryNotification(setup, position))
  }
  
  /* 
   * exit game 
   */
  protected def closeApplication {
    System.exit(0)
  }
}
