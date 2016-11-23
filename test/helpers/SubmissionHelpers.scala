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

package helpers

import play.api.libs.json.Json

object SubmissionHelpers {

  val subscriptionRequest = (reference: String) =>
  Json.parse(s"""{
    "acknowledgementReference": "ABC1234567890",
    "subscriptionType": {
      "correspondenceDetails": {
        "contactName": {
          "name1": "$reference",
          "name2": "Test"
        },
        "contactDetails": {
          "phoneNumber": "000000000000",
          "mobileNumber": "000000000000",
          "faxNumber": "000000000000",
          "emailAddress": "test@test.com"
        },
        "contactAddress": {
          "addressLine1": "Line 1",
          "addressLine2": "Line 2",
          "addressLine3": "Line 3",
          "addressLine4": "Line 4",
          "countryCode": "GB",
          "postalCode": "AA1 1AA"
        }
      }
    }
  }""")

  val validSafeId = "XA0001234567890"
  val invalidSafeId = "INVALID123"

}
