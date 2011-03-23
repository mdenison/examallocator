package bootstrap.liftweb

import net.liftweb._
import sitemap._
import util._
import Helpers._

import common._
import http._
import Loc._
import mapper._

import de.codecarving.examallocator.model._
import de.codecarving.fhsldap.fhsldap
import de.codecarving.fhsldap.model._

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = 
	new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
			     Props.get("db.url") openOr 
			     "jdbc:h2:lift_examallocator.db;AUTO_SERVER=TRUE",
			     Props.get("db.user"), Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)

      Schemifier.schemify(true, Schemifier.infoF _, Student, Exam)
    }

    lazy val loggedInEmployeeorAdmin = If(() => User.isEmployee || User.isAdmin,
                                   () => RedirectResponse("/index"))

    lazy val loggedInEmployee = If(() => User.isEmployee,
                            () => RedirectResponse("/index"))

    lazy val loggedInStudent = If(() => User.isStudent,
                           () => RedirectResponse("/index"))

    lazy val loggedInAdmin = If(() => User.isAdmin,
                         () => RedirectResponse("/index"))

    lazy val loggedInStudentorAdmin = If(() => User.isEmployee || User.isAdmin,
                                   () => RedirectResponse("/index"))

    lazy val loggedIn = If(() => User.loggedIn_?, () => RedirectResponse("/index"))


    val sitemap = List(
      Menu.i("Home") / "index",
      Menu.i("Prüfung") / "exam" / "choose" >> loggedIn submenus (
        Menu.i("An Prüfung Teilnehmen") / "exam" / "participate" >> loggedInStudent >> Hidden,
        Menu.i("Prüfung Freischalten") / "exam" / "changeExam" >> loggedInEmployeeorAdmin,
        Menu.i("Prüfung Freigeben/Sperren") / "exam" / "editExam" >> loggedInEmployeeorAdmin >> Hidden,
        Menu.i("Prüfung Anlegen") / "exam" / "newExam" >> loggedInEmployeeorAdmin
        ),
      Menu.i("Auswertung") / "exam" / "evaluate" >> loggedInEmployeeorAdmin
    )

    LiftRules.addToPackages("de.codecarving.examallocator")

    LiftRules.setSiteMap(SiteMap(sitemap:_*))

    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    // Starting the FhS LDAP Module
    fhsldap.init

  }
}
