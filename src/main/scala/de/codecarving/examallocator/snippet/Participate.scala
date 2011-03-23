package de.codecarving.examallocator
package snippet

import de.codecarving.examallocator.model._
import net.liftweb.mapper.By
import de.codecarving.fhsldap.model.User
import net.liftweb.common.{Full, Empty, Loggable}
import net.liftweb.http.{S, SHtml}
import net.liftweb.util.BindHelpers._

/**
 * Participate renders the UI for Students to register/deregister for an Exam.
 */
class Participate extends Loggable with GlobalRequests {

  val exam = currentExamTitle.get

  val student: Student =
    if(Student.findAll(By(Student.fhsid,User.currentUserId.openOr(""))).filter(_.exam == exam).isEmpty) {
      val newStudent = Student.create
      newStudent.fhsid.set(User.currentUserId.open_!)
      newStudent.displayName.set(User.ldapAttributes.displayName.open_!)
      newStudent

    } else {
      Student.findAll(By(Student.fhsid,User.currentUserId.openOr(""))).filter(_.exam == exam).head
  }

  def render = {

    def process() = {
      student.exam.set(exam)
      student.save
      S redirectTo "/exam/choose"
    }

    "name=displayName" #> student.displayName &
    "name=examTitle" #> exam &
    "name=attends" #> (student.attends.toForm.open_! ++ SHtml.hidden(process))
  }
}
