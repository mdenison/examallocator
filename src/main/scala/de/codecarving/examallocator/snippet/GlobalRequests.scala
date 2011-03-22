package de.codecarving.examallocator.snippet

import net.liftweb.http.{SessionVar, RequestVar}
import de.codecarving.examallocator.model.Exam
import net.liftweb.common.{Empty, Box}

trait GlobalRequests {

  object currentExam extends SessionVar[Box[Exam]](Empty)
  object currentExamTitle extends RequestVar[String]("")
}