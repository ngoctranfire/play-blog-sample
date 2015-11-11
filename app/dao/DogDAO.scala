package dao

import javax.inject.Inject

import models.Dog
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import slick.driver.JdbcProfile

import scala.concurrent.Future


/**
 * Created by ngoctranfire on 11/10/15.
 */
class DogDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  private val Dogs = TableQuery[DogsTable]

  def all(): Future[Seq[Dog]] = db.run(Dogs.result)

  def insert(dog: Dog): Future[Unit] = db.run(Dogs += dog).map { _ => () }

  private class DogsTable(tag: Tag) extends Table[Dog](tag, "DOG") {

    def name = column[String]("NAME", O.PrimaryKey)
    def color = column[String]("COLOR")

    def * = (name, color) <> (Dog.tupled, Dog.unapply _)
  }
}
