package de.codecarving.examallocator.model

import net.liftweb.mapper._

class Student extends LongKeyedMapper[Student] with IdPK {
  def getSingleton = Student
  object fhsid extends MappedString(this,100)
  object displayName extends MappedString(this,100)
  object attends extends MappedBoolean(this)
  object exam extends MappedString(this, 100)

}

object Student extends Student with LongKeyedMetaMapper[Student] {
  override def dbTableName = "students"

}
