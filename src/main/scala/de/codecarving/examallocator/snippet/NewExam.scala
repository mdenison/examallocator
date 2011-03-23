package de.codecarving.examallocator
package snippet

import net.liftweb.common.Loggable
import de.codecarving.examallocator.model._
import net.liftweb.mapper.By
import net.liftweb.http.{S, SHtml}
import net.liftweb.util.BindHelpers._
import de.codecarving.fhsldap.model.User

/**
 * NewExam renders a UI for Employees to create Exams.
 */
class NewExam extends Loggable {

  val exam = Exam.create

  def render = {

    def process() = {

      if(Exam.findAll(By(Exam.title,exam.title)).isEmpty) {
        exam.user.set(User.currentUserId.open_!)
        exam.save
        S redirectTo "/exam/choose"
      } else {
        S warning "Exam allready exists"
        S redirectTo "/exam/newExam"
      }
    }

    "name=title" #> exam.title.toForm.open_! &
    "name=released" #> (exam.released.toForm.open_! ++ SHtml.hidden(process))
  }
}
