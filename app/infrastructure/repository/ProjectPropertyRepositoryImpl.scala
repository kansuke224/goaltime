package infrastructure.repository

import domain.entity.ProjectProperty
import domain.repository.ProjectPropertyRepository
import scalikejdbc._

class ProjectPropertyRepositoryImpl extends ProjectPropertyRepository {


  override def store(projectProperty: ProjectProperty): Unit = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>

        val sql =
        sql"""INSERT INTO project_properties (
             |project_id,
             |twitter_id,
             |project_name,
             |week_goal,
             |week_sum,
             |created_time,
             |updated_time,
             |start_time,
             |last_week_sum,
             |last_week_goal,
             |user_name,
             |comment_user,
             |comment_content,
             |archived_time
             |) VALUES (
             |${projectProperty.projectId},
             |${projectProperty.twitterId},
             |${projectProperty.projectName},
             |${projectProperty.weekGoal},
             |${projectProperty.weekSum},
             |${projectProperty.createdTime},
             |${projectProperty.updatedTime},
             |${projectProperty.startTime},
             |${projectProperty.lastWeekSum},
             |${projectProperty.lastWeekGoal},
             |${projectProperty.userName},
             |${projectProperty.commentUser},
             |${projectProperty.commentContent},
             |${projectProperty.archivedTime}
             |)
          """.stripMargin
        sql.update().apply()
        // --- transaction scope end ---
      }

    }
  }

  override def find(twitterId: Long): List[ProjectProperty] = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>

        val sql =
          sql"SELECT * FROM project_properties WHERE twitter_id = ${twitterId}"
        sql.map(resultSettoProjectProperty).list().apply()
      }
    }
  }

  private def resultSettoProjectProperty(rs: WrappedResultSet):ProjectProperty = {
    ProjectProperty(
                    rs.string("project_id"),
                    rs.long("twitter_id"),
                    rs.string("project_name"),
                    rs.long("week_goal"),
                    rs.long("week_sum"),
                    rs.long("created_time"),
                    rs.string("updated_time"),
                    rs.bigInt("start_time"),
                    rs.long("last_week_sum"),
                    rs.long("last_week_goal"),
                    rs.string("user_name"),
                    rs.string("comment_user"),
                    rs.string("comment_content"),
                    rs.bigInt("archived_time")
                  )

  }

  override def updateStartTime(startTimeMillis: BigInt, projectId: String): Unit = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>
        val sql =
          sql"UPDATE project_properties SET start_time = ${startTimeMillis} WHERE project_id = ${projectId}"
        sql.update().apply()
      }
    }

  }

  override def initializeStartTime(projectId: String): Unit = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>
        val sql =
          sql"UPDATE project_properties SET start_time = 0 WHERE project_id = ${projectId}"
        sql.update().apply()
      }
    }
  }

  override def updateWeekSum(projectId: String, weekSum: BigInt): Unit = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>
        val sql =
          sql"UPDATE project_properties SET week_sum = ${weekSum}  WHERE project_id = ${projectId}"
        sql.update().apply()
      }
    }
  }

  override def findProject(projectId: String): ProjectProperty = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>
        val sql =
          sql"SELECT *  from project_properties WHERE project_id = ${projectId}"
        sql.map(resultSettoProjectProperty).single().apply().getOrElse(throw new RuntimeException(s"Project is notfound."))
      }
    }
  }

  override def updateProject(projectId: String, projectName: String, weekGoal: Long): Unit = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>
        val sql =
          sql"UPDATE project_properties SET week_goal = ${weekGoal}, project_name = ${projectName} WHERE project_id = ${projectId}"
        sql.update().apply()
      }
    }
  }

  override def deleteProject(projectId: String): Unit = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>
        val sql =
          sql"DELETE FROM project_properties WHERE project_id = ${projectId}"
        sql.update().apply()
      }
    }
  }

  override def findArchivedProject(): List[ProjectProperty] = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>
        val sql =
          sql"SELECT * FROM project_properties WHERE week_sum >= week_goal ORDER BY archived_time DESC"
        sql.map(resultSettoProjectProperty).list().apply()
      }
    }
  }

  override def updateLastWeekInfo(projectId: String, weekSum: Long, weekGoal: Long): Unit = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>
        val sql =
          sql"UPDATE project_properties SET last_week_sum = ${weekSum}, last_week_goal = ${weekGoal} WHERE project_id = ${projectId}"
        sql.update().apply()
      }
    }
  }

  override def resetWeekSum(twitterId: Long, updatedTime: String): Unit = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>
        val sql =
          sql"UPDATE project_properties SET week_sum = 0, updated_time = ${updatedTime}, comment_user = '', comment_content = '', archived_time = 0  WHERE twitter_id = ${twitterId}"
        sql.update().apply()
      }
    }
  }

  override def comment(projectId: String, commentUser: String, commentContent: String): Unit = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>
        val sql =
          sql"UPDATE project_properties SET comment_user = ${commentUser}, comment_content = ${commentContent}  WHERE project_id = ${projectId}"
        sql.update().apply()
      }
    }
  }

  override def updateArchivedTime(projectId: String, archivedTime: BigInt): Unit = {
    using(DB(ConnectionPool.borrow())) { db =>
      db localTx { implicit session =>
        val sql =
          sql"UPDATE project_properties SET archived_time = ${archivedTime}  WHERE project_id = ${projectId}"
        sql.update().apply()
      }
    }
  }

}
