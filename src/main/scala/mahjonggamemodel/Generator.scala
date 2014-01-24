package main.scala.mahjonggamemodel




trait Generator {
  def generate(game:Game, setupFile:String)
  def scramble(game:Game)
}