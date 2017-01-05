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

package common

import play.api.libs.json.Json

object JsonResponses {

  //XZTAVC000187600
  //maximumRegResponse
  val getSubFull = Json.parse(
    """
      |{
      |    "processingDate": "2001-12-17T09:30:47Z",
      |    "subscriptionType": {
      |        "safeId": "XA0004567890123",
      |        "correspondenceDetails": {
      |            "contactName": {
      |                "name1": "Sub",
      |                "name2": "Full"
      |            },
      |            "contactDetails": {
      |                "phoneNumber": "0000 10000",
      |                "mobileNumber": "0000 2000",
      |                "faxNumber": "0000 30000",
      |                "emailAddress": "test@test.com"
      |            },
      |            "contactAddress": {
      |                "addressLine1": "Line 1",
      |                "addressLine2": "Line 2",
      |                "addressLine3": "Line 3",
      |                "addressLine4": "Line 4",
      |                "countryCode": "GB",
      |                "postalCode": "AA1 1AA"
      |            }
      |        }
      |    }
      |}
    """.stripMargin
  )

  //XNTAVC000257565
  //minimumRegResponse
  val getSubNoAddress = Json.parse(
    """
      |{
      |	"processingDate": "2001-12-17T09:30:47Z",
      |	"subscriptionType": {
      |		"safeId": "XA0001234567890",
      |		"correspondenceDetails": {
      |			"contactName": {
      |				"name1": "No",
      |				"name2": "Address"
      |			},
      |			"contactDetails": {
      |				"phoneNumber": "0000 10000",
      |				"mobileNumber": "0000 2000",
      |				"faxNumber": "0000 30000",
      |				"emailAddress": "john.smith@noplace.atall.com"
      |			}
      |		}
      |	}
      |}
    """.stripMargin
  )

  //XWTAVC000435628
  //minimumRegResponse
  val getSubNoContactDetails = Json.parse(
    """
      |{
      |	"processingDate": "2001-12-17T09:30:47Z",
      |	"subscriptionType": {
      |		"safeId": "XA0001234567890",
      |		"correspondenceDetails": {
      |			"contactName": {
      |				"name1": "No",
      |				"name2": "Contact"
      |			},
      |			"contactAddress": {
      |				"addressLine1": "Line 1",
      |				"addressLine2": "Line 2",
      |				"addressLine3": "Line 3",
      |				"addressLine4": "Line 4",
      |				"countryCode": "GB",
      |				"postalCode": "AA1 1AA"
      |			}
      |		}
      |	}
      |}
    """.stripMargin
  )

  //XBTAVC000739704
  //maxContactDetailsRegResponse
  val getSubMinForeignAddressWithDetails = Json.parse(
    """
     {
      |	"processingDate": "2001-12-17T09:30:47Z",
      |	"subscriptionType": {
      |		"safeId": "XA0003456789012",
      |		"correspondenceDetails": {
      |			"contactName": {
      |				"name1": "J"
      |			},
      |			"contactDetails": {
      |
      |			},
      |			"contactAddress": {
      |				"addressLine1": "Line 1",
      |				"addressLine2": "Line 2",
      |				"countryCode": "GG"
      |
      |			}
      |		}
      |	}
      |}
    """.stripMargin
  )

  //XATAVC000421817
  //maxAddressRegResponse
  val getSubMinUkAddressWithDetails = Json.parse(

    """
      |{
      |	"processingDate": "2001-12-17T09:30:47Z",
      |	"subscriptionType": {
      |		"safeId": "XA0002345678901",
      |		"correspondenceDetails": {
      |			"contactName": {
      |				"name1": "J"
      |			},
      |			"contactDetails": {},
      |			"contactAddress": {
      |				"addressLine1": "Line 1",
      |				"addressLine2": "Line 2",
      |				"countryCode": "GB",
      |				"postalCode": "AA1 1AA"
      |			}
      |		}
      |	}
      |}
    """.stripMargin
  )

  //XWTAVC000616234
  //minimumRegResponse
  val getSubMinimum = Json.parse(
    """
       {
      |	"processingDate": "2001-12-17T09:30:47Z",
      |	"subscriptionType": {
      |		"safeId": "XA0001234567890",
      |		"correspondenceDetails": {
      |			"contactName": {
      |				"name1": "J"
      |
      |			}
      |		}
      |	}
      |}
    """.stripMargin
  )

  //XZTAVC000123456
  //submissionErrorResponse
  val getSubmissionErrorSub = Json.parse(
    """
      |{
      |	"processingDate": "2001-12-17T09:30:47Z",
      |	"subscriptionType": {
      |		"safeId": "XA0005678901234",
      |		"correspondenceDetails": {
      |			"contactName": {
      |				"name1": "J"
      |			},
      |			"contactDetails": {},
      |			"contactAddress": {
      |				"addressLine1": "Line 1",
      |				"addressLine2": "Line 2",
      |				"countryCode": "GB",
      |				"postalCode": "AA1 1AA"
      |			}
      |		}
      |	}
      |}
    """.stripMargin
  )

  //XZTAVC000234561
  //resourceNotFoundResponse
  val getResourceNotFoundSub = Json.parse(
    """
      |{
      |	"processingDate": "2001-12-17T09:30:47Z",
      |	"subscriptionType": {
      |		"safeId": "XA0006789012345",
      |		"correspondenceDetails": {
      |			"contactName": {
      |				"name1": "J"
      |			},
      |			"contactDetails": {},
      |			"contactAddress": {
      |				"addressLine1": "Line 1",
      |				"addressLine2": "Line 2",
      |				"countryCode": "GB",
      |				"postalCode": "AA1 1AA"
      |			}
      |		}
      |	}
      |}
    """.stripMargin
  )

  //XZTAVC000345612
  //serverErrorResponse
  val getServerErrorSub = Json.parse(
    """
      |{
      |	"processingDate": "2001-12-17T09:30:47Z",
      |	"subscriptionType": {
      |		"safeId": "XA0007890123456",
      |		"correspondenceDetails": {
      |			"contactName": {
      |				"name1": "J"
      |			},
      |			"contactDetails": {},
      |			"contactAddress": {
      |				"addressLine1": "Line 1",
      |				"addressLine2": "Line 2",
      |				"countryCode": "GB",
      |				"postalCode": "AA1 1AA"
      |			}
      |		}
      |	}
      |}
    """.stripMargin
  )

  //XZTAVC000456123
  //serviceUnavailableResponse
  val getServiceUnavailableSub = Json.parse(
    """
      |{
      |	"processingDate": "2001-12-17T09:30:47Z",
      |	"subscriptionType": {
      |		"safeId": "XA0008901234567",
      |		"correspondenceDetails": {
      |			"contactName": {
      |				"name1": "J"
      |			},
      |			"contactDetails": {},
      |			"contactAddress": {
      |				"addressLine1": "Line 1",
      |				"addressLine2": "Line 2",
      |				"countryCode": "GB",
      |				"postalCode": "AA1 1AA"
      |			}
      |		}
      |	}
      |}
    """.stripMargin
  )

}
