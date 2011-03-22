package de.codecarving.examallocator.snippet

import de.codecarving.examallocator.model.Exam
import net.liftweb.mapper.By
import de.codecarving.fhsldap.model.User
import net.liftweb.http.SHtml._
import xml.Text._
import xml.Text
import net.liftweb.common.{Full, Loggable}


class ChangeExam extends Loggable with GlobalRequests {

  def render = {
    Exam.findAll(By(Exam.user,User.currentUserId.open_!)).flatMap {
      exam =>
    <table style="border:1">
			  <thead>
				  <tr>
					  <th width="100%" colspan="4">Klausur: { exam.title }</th>
				  </tr>
			  </thead>
			  <tbody>
				  <tr>
            <td colspan="4">Optionen: {link("/exam/editExam", () => currentExam(Full(exam)), Text("Freischalten/Sperren"))}
            </td>
          </tr>
    	  </tbody>
		    </table>
    }
  }
}
