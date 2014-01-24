/*
 * Observer pattern for synchronisation  
 */
package main.scala.mahjonggameview

import main.scala.mahjonggamecontroller._
import main.scala.mahjonggamemodel._
import main.scala.mahjonggameutil._
import main.scala.mahjonggameutil.SimpleSubscriber
import main.scala.mahjonggameutil.SimplePublisher

trait View extends SimplePublisher with SimpleSubscriber{
  def startView(controller:MahjongController)	{}
  def stopView(controller:MahjongController)	{}
  def autoClose = false
}