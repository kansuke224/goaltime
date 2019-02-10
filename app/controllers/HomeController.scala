package controllers

import domain.entity.ProjectProperty
import domain.repository.ProjectPropertyRepository
import javax.inject._
import play.api._
import play.api.cache.SyncCacheApi
import play.api.mvc._
import twitter4j.{RateLimitStatus, Relationship, TwitterFactory}
import java.lang.System.currentTimeMillis
import java.time.LocalDate

import play.api.data.Forms._
import play.api.data.Form
import play.api.data.Forms.mapping

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val cache: SyncCacheApi,
                               projectPropertyRepository: ProjectPropertyRepository,
                               cc: ControllerComponents,
                               configuration: Configuration
                              ) extends TwitterLoginController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = TwitterLoginAction { implicit request: TwitterLoginRequest[AnyContent] =>
    val ConsumerKey = configuration.get[String]("goaltime.consumerkey")
    val ConsumerSecret = configuration.get[String]("goaltime.consumersecret")

    if(request.accessToken.isDefined) {
      val twitter = new TwitterFactory().getInstance()
      twitter.setOAuthConsumer(ConsumerKey, ConsumerSecret)
      twitter.setOAuthAccessToken(request.accessToken.get)
      val user = twitter.verifyCredentials()
      val twitterId = user.getId
      // weekSumをリセットする処理
      val dat = LocalDate.now()
//      val dat = LocalDate.of(2019, 2, 28)
      val week = dat.getDayOfWeek()
      val preProjectList = projectPropertyRepository.find(twitterId)
      if (preProjectList.length != 0) {
        if(week.toString == "MONDAY" && preProjectList(0).updatedTime != dat.toString) {
          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.toString)

        } else if(week.toString == "TUESDAY" && preProjectList(0).updatedTime != dat.minusDays(1).toString){

          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.minusDays(1).toString)

        } else if(week.toString == "WEDNESDAY" && preProjectList(0).updatedTime != dat.minusDays(2).toString){

          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.minusDays(2).toString)

        } else if(week.toString == "THURSDAY" && preProjectList(0).updatedTime != dat.minusDays(3).toString){

          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.minusDays(3).toString)

        } else if(week.toString == "FRIDAY" && preProjectList(0).updatedTime != dat.minusDays(4).toString){

          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.minusDays(4).toString)

        } else if(week.toString == "SATURDAY" && preProjectList(0).updatedTime != dat.minusDays(5).toString){

          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.minusDays(5).toString)

        } else if(week.toString == "SUNDAY" && preProjectList(0).updatedTime != dat.minusDays(6).toString){

          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.minusDays(6).toString)

        }
      }


      val projectList: List[ProjectProperty] = projectPropertyRepository.find(twitterId)

      val startTimeArr: Array[String] = projectList.toArray.map(projectProperty => {
        projectProperty.startTime.toString()
      }) :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0"

      val weekSumArr: Array[String] = projectList.toArray.map(projectProperty => {
        val sts = projectProperty.weekSum
        val sh = Math.round(sts / 3600)
        val sm = Math.round((sts - sh * 3600) / 60)
        val ss = sts - sh * 3600 - sm * 60
        sh.toString + "：" + sm.toString + "：" + ss.toString
      }) :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0"

      val weekGoalArr: Array[String] = projectList.toArray.map(projectProperty => {
        val gts = projectProperty.weekGoal - projectProperty.weekSum
        val gh = Math.round(gts / 3600)
        val gm = Math.round((gts - gh * 3600) / 60)
        val gs = gts - gh * 3600 - gm * 60
        gh.toString + "：" + gm.toString + "：" + gs.toString
      }) :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0"

      val archivedProjectList = projectPropertyRepository.findArchivedProject()
      val preCommentContentList = archivedProjectList.map(p => {
        p.commentContent
      })
      val commentContentList: List[Array[String]] = preCommentContentList.map(str => {
        str.split(",/%abba%/,")
      })

      val preCommentUserList = archivedProjectList.map(p => {
        p.commentUser
      })
      val commentUserList: List[Array[String]] = preCommentUserList.map(str => {
        str.split(",/%abba%/,")
      })

      Ok(views.html.index(request.accessToken, user.getName, projectList, startTimeArr, weekSumArr, weekGoalArr, archivedProjectList, commentContentList, commentUserList))
    } else {
      val ProjectList = List()
      val StartTimeArr = Array("")
      val WeekSumArr = Array("")
      val WeekGoalArr = Array("")

      val archivedProjectList = projectPropertyRepository.findArchivedProject()
      val preCommentContentList = archivedProjectList.map(p => {
        p.commentContent
      })
      val commentContentList: List[Array[String]] = preCommentContentList.map(str => {
        str.split(",/%abba%/,")
      })

      val preCommentUserList = archivedProjectList.map(p => {
        p.commentUser
      })
      val commentUserList: List[Array[String]] = preCommentUserList.map(str => {
        str.split(",/%abba%/,")
      })
      Ok(views.html.index(request.accessToken, "a", ProjectList, StartTimeArr, WeekSumArr, WeekGoalArr, archivedProjectList, commentContentList, commentUserList))
    }

  }

  def newProject() = TwitterLoginAction { implicit request: TwitterLoginRequest[AnyContent] =>
    Ok(views.html.newPeoject(request.accessToken))
  }


  def startProject() = TwitterLoginAction{implicit request: TwitterLoginRequest[AnyContent] =>
    case class ProjectId(projectId: String)

    val form = Form(mapping("projectId" -> text)(ProjectId.apply)(ProjectId.unapply))
    //　データベースにstartTimeを保存する処理
    val projectId = form.bindFromRequest().get

    val projectProperty = projectPropertyRepository.findProject(projectId.projectId)

    if(projectProperty.startTime == 0) {
      projectPropertyRepository.updateStartTime(currentTimeMillis(), projectId.projectId)
    }

    Redirect("/")
  }

  def stopProject() = TwitterLoginAction{implicit request: TwitterLoginRequest[AnyContent] =>
    case class ProjectId(projectId: String)

    val form = Form(mapping("projectId" -> text)(ProjectId.apply)(ProjectId.unapply))
    // startTimeを0にupdateし、week_sumを更新する処理
    val projectId = form.bindFromRequest().get
    val projectProperty = projectPropertyRepository.findProject(projectId.projectId)
    if(projectProperty.startTime == 0) {

    } else if(projectProperty.weekSum >= projectProperty.weekGoal) {
      val weekSum = projectProperty.weekSum + ((currentTimeMillis() - projectProperty.startTime) / 1000)

      projectPropertyRepository.updateWeekSum(projectId.projectId, weekSum)
      projectPropertyRepository.initializeStartTime(projectId.projectId)
    } else {
      val weekSum = projectProperty.weekSum + ((currentTimeMillis() - projectProperty.startTime) / 1000)

      projectPropertyRepository.updateWeekSum(projectId.projectId, weekSum)
      projectPropertyRepository.initializeStartTime(projectId.projectId)

      val updatedProject = projectPropertyRepository.findProject(projectId.projectId)
      if (updatedProject.weekSum >= updatedProject.weekGoal) {
        projectPropertyRepository.updateArchivedTime(projectId.projectId, currentTimeMillis())
      }
    }

    Redirect("/")
  }

  def projectConf() = TwitterLoginAction { implicit request: TwitterLoginRequest[AnyContent] =>
    case class ProjectId(projectId: String)

    val form = Form(mapping("projectId" -> text)(ProjectId.apply)(ProjectId.unapply))
    val projectId = form.bindFromRequest().get
    val projectProperty = projectPropertyRepository.findProject(projectId.projectId)
    val weekGoalHour = projectProperty.weekGoal / 3600

    val lsts = projectProperty.lastWeekSum
    val lsh = Math.round(lsts / 3600)
    val lsm = Math.round((lsts - lsh * 3600) / 60)
    val lss = lsts - lsh * 3600 - lsm * 60
    val lastWeekSumTime = lsh.toString + ":" + lsm.toString + ":" + lss.toString

    val lgts = projectProperty.lastWeekGoal
    val lgh = Math.round(lgts / 3600)
    val lgm = Math.round((lgts - lgh * 3600) / 60)
    val lgs = lgts - lgh * 3600 - lgm * 60
    val lastWeekGoalTime = lgh.toString + ":" + lgm.toString + ":" + lgs.toString

    Ok(views.html.projectConf(request.accessToken, projectProperty, weekGoalHour, lastWeekSumTime, lastWeekGoalTime))
  }

  def updateProject() = TwitterLoginAction { implicit request: TwitterLoginRequest[AnyContent] =>
    case class Project(projectName: String, weekGoalHour: Int, projectId: String)
    val form = Form(mapping("projectName" -> text, "weekGoalHour" -> number, "projectId" -> text)(Project.apply)(Project.unapply))
    val project = form.bindFromRequest().get
    val weekGoal: Long = project.weekGoalHour * 3600
    projectPropertyRepository.updateProject(project.projectId, project.projectName, weekGoal)

    Redirect("/")
  }

  def deleteProject() =  TwitterLoginAction { implicit request: TwitterLoginRequest[AnyContent] =>
    case class ProjectId(projectId: String)

    val form = Form(mapping("projectId" -> text)(ProjectId.apply)(ProjectId.unapply))
    val projectId = form.bindFromRequest().get
    projectPropertyRepository.deleteProject(projectId.projectId)

    Redirect("/")
  }

  def comment() = TwitterLoginAction { implicit request: TwitterLoginRequest[AnyContent] =>
    case class Comment(commentContent: String, commentUser: String, projectId: String)
    val form = Form(mapping("comment" -> text, "commentUser" -> text, "projectId" -> text)(Comment.apply)(Comment.unapply))
    val comment = form.bindFromRequest().get

    val project = projectPropertyRepository.findProject(comment.projectId)
    val commentContent = project.commentContent + ",/%abba%/," + comment.commentContent
    val commentUser = project.commentUser + ",/%abba%/," + comment.commentUser

    projectPropertyRepository.comment(comment.projectId, commentUser, commentContent)

    Redirect("/")
  }

  // archivedListの取得についてのみindexとは違う処理にする
  def secondIndex() = TwitterLoginAction { implicit request: TwitterLoginRequest[AnyContent] =>
    val ConsumerKey = configuration.get[String]("goaltime.consumerkey")
    val ConsumerSecret = configuration.get[String]("goaltime.consumersecret")

    if(request.accessToken.isDefined) {
      val twitter = new TwitterFactory().getInstance()
      twitter.setOAuthConsumer(ConsumerKey, ConsumerSecret)
      twitter.setOAuthAccessToken(request.accessToken.get)
      val user = twitter.verifyCredentials()
      val twitterId = user.getId
      // weekSumをリセットする処理
      val dat = LocalDate.now()
      //      val dat = LocalDate.of(2019, 2, 20)
      val week = dat.getDayOfWeek()
      val preProjectList = projectPropertyRepository.find(twitterId)
      if (preProjectList.length != 0) {
        if(week.toString == "MONDAY" && preProjectList(0).updatedTime != dat.toString) {
          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.toString)

        } else if(week.toString == "TUESDAY" && preProjectList(0).updatedTime != dat.minusDays(1).toString){

          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.minusDays(1).toString)

        } else if(week.toString == "WEDNESDAY" && preProjectList(0).updatedTime != dat.minusDays(2).toString){

          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.minusDays(2).toString)

        } else if(week.toString == "THURSDAY" && preProjectList(0).updatedTime != dat.minusDays(3).toString){

          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.minusDays(3).toString)

        } else if(week.toString == "FRIDAY" && preProjectList(0).updatedTime != dat.minusDays(4).toString){

          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.minusDays(4).toString)

        } else if(week.toString == "SATURDAY" && preProjectList(0).updatedTime != dat.minusDays(5).toString){

          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.minusDays(5).toString)

        } else if(week.toString == "SUNDAY" && preProjectList(0).updatedTime != dat.minusDays(6).toString){

          preProjectList.foreach(projectProperty => {
            projectPropertyRepository.updateLastWeekInfo(projectProperty.projectId, projectProperty.weekSum, projectProperty.weekGoal)
          })
          projectPropertyRepository.resetWeekSum(twitterId, dat.minusDays(6).toString)

        }
      }


      val projectList: List[ProjectProperty] = projectPropertyRepository.find(twitterId)

      val startTimeArr: Array[String] = projectList.toArray.map(projectProperty => {
        projectProperty.startTime.toString()
      }) :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0"

      val weekSumArr: Array[String] = projectList.toArray.map(projectProperty => {
        val sts = projectProperty.weekSum
        val sh = Math.round(sts / 3600)
        val sm = Math.round((sts - sh * 3600) / 60)
        val ss = sts - sh * 3600 - sm * 60
        sh.toString + "：" + sm.toString + "：" + ss.toString
      }) :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0"

      val weekGoalArr: Array[String] = projectList.toArray.map(projectProperty => {
        val gts = projectProperty.weekGoal - projectProperty.weekSum
        val gh = Math.round(gts / 3600)
        val gm = Math.round((gts - gh * 3600) / 60)
        val gs = gts - gh * 3600 - gm * 60
        gh.toString + "：" + gm.toString + "：" + gs.toString
      }) :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0" :+ "0"

      val preArchivedProjectList = projectPropertyRepository.findArchivedProject()
      val archivedProjectList: List[ProjectProperty] = preArchivedProjectList.filter(p => {
        if (twitterId == p.twitterId) {
          true
        } else {
          twitter.showFriendship(twitterId, p.twitterId).isTargetFollowedBySource && twitter.showFriendship(twitterId, p.twitterId).isSourceFollowedByTarget
        }
      })



      val preCommentContentList = archivedProjectList.map(p => {
        p.commentContent
      })
      val commentContentList: List[Array[String]] = preCommentContentList.map(str => {
        str.split(",/%abba%/,")
      })

      val preCommentUserList = archivedProjectList.map(p => {
        p.commentUser
      })
      val commentUserList: List[Array[String]] = preCommentUserList.map(str => {
        str.split(",/%abba%/,")
      })

      Ok(views.html.index(request.accessToken, user.getName, projectList, startTimeArr, weekSumArr, weekGoalArr, archivedProjectList, commentContentList, commentUserList))
    } else {
      val ProjectList = List()
      val StartTimeArr = Array("")
      val WeekSumArr = Array("")
      val WeekGoalArr = Array("")

      val archivedProjectList = projectPropertyRepository.findArchivedProject()
      val preCommentContentList = archivedProjectList.map(p => {
        p.commentContent
      })
      val commentContentList: List[Array[String]] = preCommentContentList.map(str => {
        str.split(",/%abba%/,")
      })

      val preCommentUserList = archivedProjectList.map(p => {
        p.commentUser
      })
      val commentUserList: List[Array[String]] = preCommentUserList.map(str => {
        str.split(",/%abba%/,")
      })
      Ok(views.html.index(request.accessToken, "a", ProjectList, StartTimeArr, WeekSumArr, WeekGoalArr, archivedProjectList, commentContentList, commentUserList))
    }

  }

}
