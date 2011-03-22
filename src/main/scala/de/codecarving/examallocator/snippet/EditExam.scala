package de.codecarving.examallocator.snippet

import net.liftweb.common.Loggable
import de.codecarving.examallocator.model._
import net.liftweb.mapper.By
import net.liftweb.http.{S, SHtml}
import net.liftweb.util.BindHelpers._
import de.codecarving.fhsldap.model.User

class EditExam extends Loggable with GlobalRequests {

  val exam = currentExam.open_!

  def render = {

    def process() = {
      exam.save
      S redirectTo "/exam/choose"
      }

    "name=title" #> exam.title &
    "name=released" #> (exam.released.toForm.open_! ++ SHtml.hidden(process))
  }
}
