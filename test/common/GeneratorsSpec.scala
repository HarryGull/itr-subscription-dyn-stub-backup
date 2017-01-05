/*
 * Copyright 2017 HM Revenue & Customs
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

import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}
import common.Generators._
import common.Regex._

class GeneratorsSpec extends UnitSpec with WithFakeApplication {

  "Repeatedly calling the generateCapitalLetter method" should {

    "generate a valid Upper Case Character" when {

      val validCapitalCharRange: List[Char] = List('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z')

      (1 to 50) foreach { i =>
        s"executing call number ${i}" in {
          validCapitalCharRange.contains(generateCapitalLetter) shouldBe true
        }
      }
    }
  }

  "Repeatedly calling the generateTavcReference method" should {

    """generate a TAVC reference matching the regex "^X[A-Z]TAVC000[0-9]{6}$" """ when {
      (1 to 50) foreach { i =>
        s"executing call number ${i}" in {
          tavcReferenceRegex.pattern.matcher(generateTavcReference).matches shouldBe true
        }
      }
    }
  }
}
