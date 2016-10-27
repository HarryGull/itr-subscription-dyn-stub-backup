/*
 * Copyright 2016 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import scala.util.Random

object Generators {

  val capitalLetterCharRange = 65 to 90
  val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

  def generateCapitalLetter: Char = capitalLetterCharRange(Random.nextInt(capitalLetterCharRange.length)).toChar

  //def generateTavcReference: String = s"X${generateCapitalLetter}TAVC000${"%06d" format Random.nextInt(999999)}"
  // TEMPORARY GENERATOR

  def generateTavcReference: String = s"$generateCapitalLetter$generateCapitalLetter${"%013d" format Random.nextInt(999999999)}"

  def currentDateTime: String = LocalDateTime.now().format(dateFormatter)
}
