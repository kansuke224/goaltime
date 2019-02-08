package domain.repository

import domain.entity.ProjectProperty
import scalikejdbc._

import scala.concurrent.Future

trait ProjectPropertyRepository {

  def store(projectProperty: ProjectProperty): Unit

  def find(twitterId: Long): List[ProjectProperty]

  def updateStartTime(startTimeMillis: BigInt, projectId: String): Unit

  def initializeStartTime(projectId: String): Unit

  def updateWeekSum(projectId: String, weekSum: BigInt): Unit

  def findProject(projectId: String): ProjectProperty

  def updateProject(projectId: String, projectName: String, weekGoal: Long): Unit

  def deleteProject(projectId: String): Unit

  def findArchivedProject(): List[ProjectProperty]

  def updateLastWeekInfo(projectId: String, weekSum: Long, weekGoal: Long)

  def resetWeekSum(twitterId: Long, updatedTime: String): Unit

  def comment(projectId: String, commentUser: String, commentContent: String): Unit

  def updateArchivedTime(projectId: String, archivedTime: BigInt): Unit

}
