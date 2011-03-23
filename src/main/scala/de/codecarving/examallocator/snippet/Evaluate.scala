package de.codecarving.examallocator
package snippet

import de.codecarving.examallocator.model._
import de.codecarving.fhsldap.model.User
import net.liftweb.mapper.By
import net.liftweb.http.SHtml._
import xml.Text._
import net.liftweb.common.{Full, Loggable}

/**
 * Evaluate generates a list with all Students which will participate in an Exam.
 */
class Evaluate extends Loggable {

  def render = {

    Exam.findAll(By(Exam.user,User.currentUserId.open_!)).flatMap {
      exam =>
      var counter = 0
      <table style="border:1">
			<thead>
				<tr>
					<th width="100%" colspan="4">Klausur: {exam.title}</th>
				</tr>
			</thead>
			<tbody>
				<tr>
          <td>Anzahl: { Student.findAll(By(Student.exam,exam.title)).filter(_.attends == true).size }</td>
          <td>Angemeldete Studenten: </td>
				</tr>
        { Student.findAll(By(Student.exam,exam.title)).filter(_.attends == true).flatMap {
        student =>
          counter += 1
          <tr>
            <td>Nr. {counter}</td>
            <td>{student.fhsid + " : " + student.displayName}</td>
          </tr>
        }}
			</tbody>
		  </table>
    }
  }
}
