package controllers

import java.lang.System.currentTimeMillis
import java.time.LocalDate
import java.util.UUID

import domain.entity.ProjectProperty
import domain.repository.ProjectPropertyRepository
import javax.inject.Inject
import play.api.Configuration
import play.api.cache.SyncCacheApi
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import twitter4j.TwitterFactory

class ProjectController @Inject()(
                       cc: ControllerComponents,
                       val cache: SyncCacheApi,
                       projectPropertyRepository: ProjectPropertyRepository,
                       configuration: Configuration
                       ) extends TwitterLoginController(cc) {
  case class Project(projectName: String, weekGoal: Int)

  val form = Form(mapping("projectName" -> text, "weekGoal" -> number)(Project.apply)(Project.unapply))

  def createProject() =  TwitterLoginAction {implicit request: TwitterLoginRequest[AnyContent] =>

    val ConsumerKey = configuration.get[String]("goaltime.consumerkey")
    val ConsumerSecret = configuration.get[String]("goaltime.consumersecret")

    val twitter = new TwitterFactory().getInstance()
    twitter.setOAuthConsumer(ConsumerKey, ConsumerSecret)
    twitter.setOAuthAccessToken(request.accessToken.get)
    val user = twitter.verifyCredentials()

    val project = form.bindFromRequest().get

    var weekGoal: Int = 0
    if(project.weekGoal == 31) {
      weekGoal = 31
    } else {
      weekGoal = project.weekGoal * 60 * 60
    }

    // formからのデータをうけとる
    val twitterId = user.getId
    val projectName: String = project.projectName
    val weekSum = 0
    val userName = user.getName
    val dat = LocalDate.now
//    val dat = LocalDate.of(2019, 2, 10)
    val week = dat.getDayOfWeek()
    var updatedTime = "0"

    if (week.toString == "MONDAY") {
      updatedTime = dat.toString
    } else if (week.toString == "TUESDAY") {
      updatedTime = dat.minusDays(1).toString
    } else if (week.toString == "WEDNESDAY") {
      updatedTime = dat.minusDays(2).toString
    } else if (week.toString == "THURSDAY") {
      updatedTime = dat.minusDays(3).toString
    } else if (week.toString == "FRIDAY") {
      updatedTime = dat.minusDays(4).toString
    } else if (week.toString == "SATURDAY") {
      updatedTime = dat.minusDays(5).toString
    } else if (week.toString == "SUNDAY") {
      updatedTime = dat.minusDays(6).toString
    }

    val projectList: List[ProjectProperty] = projectPropertyRepository.find(twitterId)
    if (projectList.length == 0) {
      val projectId= twitterId.toString + "1"

      // ProjectPropertyをつくる
      val projectProperty = createProjectProperty(
        projectId,
        twitterId,
        projectName,
        weekGoal,
        weekSum,
        updatedTime,
        userName
      )

      // MySqlに保存
      projectPropertyRepository.store(projectProperty)
    } else {
      val lastProjectId = projectList(projectList.length - 1).projectId.toString
      val number = lastProjectId.split(twitterId.toString)(1).toInt + 1
      val projectId= twitterId.toString + number.toString


      // ProjectPropertyをつくる
      val projectProperty = createProjectProperty(
        projectId,
        twitterId,
        projectName,
        weekGoal,
        weekSum,
        updatedTime,
        userName
      )

      // MySqlに保存
      projectPropertyRepository.store(projectProperty)

    }

    Redirect("/")
  }


  private def createProjectProperty(
                                     projectId: String,
                                     twitterId: Long,
                                     projectName: String,
                                     weekGoal: Long,
                                     weekSum: Long,
                                     updatedTime: String,
                                     userName: String
                                    ): ProjectProperty = {
    ProjectProperty(
      projectId,
      twitterId,
      projectName,
      weekGoal,
      weekSum,
      currentTimeMillis(),
      updatedTime,
      0,
      0,
      0,
      userName,
      "",
      "",
      0
    )

  }

}
