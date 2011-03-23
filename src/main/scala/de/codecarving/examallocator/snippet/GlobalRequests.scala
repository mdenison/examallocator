package de.codecarving.examallocator
package snippet

import net.liftweb.http.{SessionVar, RequestVar}
import de.codecarving.examallocator.model.Exam
import net.liftweb.common.{Empty, Box}

/**
 * GlobalRequests holds RequestVars/SessionVars in order to
 * send anything around between snippets.
 */
trait GlobalRequests {

  object currentExam extends SessionVar[Box[Exam]](Empty)
  object currentExamTitle extends RequestVar[String]("")
}
