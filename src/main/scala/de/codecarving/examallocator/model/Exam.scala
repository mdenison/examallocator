package de.codecarving.examallocator.model

import net.liftweb.mapper._

class Exam extends LongKeyedMapper[Exam] with IdPK {
  def getSingleton = Exam
  object user extends MappedString(this,100)
  object title extends MappedString(this,100)
  object studentCounter extends MappedInt(this)
  object released extends MappedBoolean(this)

}

object Exam extends Exam with LongKeyedMetaMapper[Exam] {
  override def dbTableName = "exam"

}