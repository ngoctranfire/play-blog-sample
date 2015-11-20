package controllers

import javax.inject.Inject

import dao.{CatDAO, DogDAO}
import models.{Cat, Dog}
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{Action, Controller}


class Application @Inject() (var catDao: CatDAO, var dogDao: DogDAO) extends Controller{

  def index = Action.async {
    catDao.all().zip(dogDao.all()).map {case (cats, dogs) => Ok(views.html.index(cats, dogs)) }
  }

  val catForm = Form(
    mapping(
      "name" -> text(),
      "color" -> text()
    )(Cat.apply)(Cat.unapply)
  )

  val dogForm = Form(
    mapping(
      "name" -> text(),
      "color" -> text()
    )(Dog.apply)(Dog.unapply)
  )

  def insertCat = Action.async { implicit request =>
    val cat: Cat = catForm.bindFromRequest.get
    catDao.insert(cat).map(_ => Redirect(routes.Application.index))
  }

  def insertDog = Action.async { implicit request =>
    val dog: Dog = dogForm.bindFromRequest.get
    dogDao.insert(dog).map(_ => Redirect(routes.Application.index))
  }
}