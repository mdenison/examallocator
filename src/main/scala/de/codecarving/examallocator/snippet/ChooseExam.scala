package de.codecarving.examallocator.snippet

import de.codecarving.examallocator.model._
import net.liftweb.common.Full._
import net.liftweb.http.SHtml._
import net.liftweb.common.{Full, Loggable}
import xml.Text
import de.codecarving.fhsldap.model.User

class ChooseExam extends Loggable with GlobalRequests {

  def render = {
    if(User.isStudent) {
      Exam.findAll.filter(_.released == true).flatMap {
        exam =>
      <table style="border:1">
			  <thead>
				  <tr>
					  <th width="100%" colspan="4">Klausur: { exam.title }</th>
				  </tr>
			  </thead>
			  <tbody>
				  <tr>
            <td colspan="4">Optionen: {link("/exam/participate", () => currentExamTitle(exam.title), Text("Anmelden/Abmelden"))}
            </td>
          </tr>
    	  </tbody>
		    </table>
      }
    } else {
      <div></div>
    }
  }
}
