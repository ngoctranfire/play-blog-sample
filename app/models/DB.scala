package models

import sorm._
/**
 * Created by ngoctranfire on 10/27/15.
 */
object DB extends Instance(entities = Seq(Entity[Person]()), url="jdbc:h2:mem:test")
