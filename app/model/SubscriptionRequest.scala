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

package model
import play.api.libs.json.Json

//class SubscriptionRequest {
//  {
//    "acknowledgementReference": "ABCD123",
//    "subscriptionType": {
//      "correspondenceDetails": {
//      "contactName": {
//      "name1": "John",
//      "name2": "Smith"
//    },
//      "contactDetails": {
//      "phoneNumber": "01214567896",
//      "mobileNumber": "07999056789",
//      "faxNumber": "01216754321",
//      "emailAddress": "john.smith@gmail.com"
//    },
//      "contactAddress": {
//      "addressLine1": "38 UpperMarshall Street",
//      "addressLine2": "Post Box Aptms",
//      "addressLine3": "Birmingham",
//      "addressLine4": "WarwickShire",
//      "countryCode": "GB",
//      "postalCode": "B1 1LA"
//    }
//    }
//    }
//  }


  case class SubscriptionRequest (acknowledgementReference: String, subscriptionType: SubscriptionType)

  object SubscriptionRequest {
    implicit val format = Json.format[SubscriptionRequest]
    implicit val writes = Json.writes[SubscriptionRequest]
  }


