package domain.entity

import java.time.LocalDateTime
import java.util.UUID

case class ProjectProperty (projectId: String,
                            twitterId: Long,
                           projectName: String,
                           weekGoal: Long,
                           weekSum: Long,
                           createdTime: Long,
                           updatedTime: String,
                           startTime: BigInt,
                           lastWeekSum: Long,
                           lastWeekGoal: Long,
                            userName: String,
                            commentUser: String,
                            commentContent: String,
                            archivedTime: BigInt
                           )





