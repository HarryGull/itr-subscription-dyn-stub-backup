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

package controllers

import common.{JsonResponses, TavcReferenceConstants}
import uk.gov.hmrc.play.test.{UnitSpec, WithFakeApplication}
import helpers.AuthHelpers._
import play.api.libs.json.Json
import play.api.test.Helpers._

class SubscriptionStubControllerGetSubSpec extends UnitSpec with WithFakeApplication {

  object TestController extends SubscriptionStubController {
  }

  "SubscriptionStubController.getSubscription with an authorised request" when {
    "tavc id parameter is empty " should {
      val result = TestController.getSubscription("XPTAVC000091314").apply(validRequest)
      "return a Processing Date" which {
        val json = Json.parse(contentAsString(result))
        val surname = (json \ "subscriptionType" \ "correspondenceDetails" \ "contactName" \ "name2").validate[String].get
        "has the expected surname" in {
          surname shouldBe "Brown"
        }
        "Json should be as expected" in {
          json shouldEqual JsonResponses.getSubFull
        }
      }
      "return OK" in {
        status(result) shouldBe OK
      }
    }

    "SubscriptionStubController.getSubscription with an authorised request and a valid tavc Id" when {
      "tavc ID parameter XZTAVC000187600 is passed" should {
        val result = TestController.getSubscription(TavcReferenceConstants.subFullRef).apply(validRequest)
        "return the expected Json as a fully populated response" which {
          val json = Json.parse(contentAsString(result))
          val surname = (json \ "subscriptionType" \ "correspondenceDetails" \ "contactName" \ "name2").validate[String].get
          "has the expected surname" in {
            surname shouldBe "Brown"
          }
          "Json should be as expected" in {
            json shouldEqual JsonResponses.getSubFull
          }
        }
        "return OK" in {
          status(result) shouldBe OK
        }
      }
    }

    "SubscriptionStubController.getSubscription with an authorised request and a valid tavc Id" when {
      "tavc ID parameter XNTAVC000257565 is passed" should {
        val result = TestController.getSubscription(TavcReferenceConstants.subNoAddressRef).apply(validRequest)
        "return the expected Json with no address" which {
          val json = Json.parse(contentAsString(result))
          val surname = (json \ "subscriptionType" \ "correspondenceDetails" \ "contactName" \ "name2").validate[String].get
          "has the expected surname" in {
            surname shouldBe "Green"
          }
          "Json should be as expected" in {
            json shouldEqual JsonResponses.getSubNoAddress
          }
        }
        "return OK" in {
          status(result) shouldBe OK
        }
      }
    }

    "SubscriptionStubController.getSubscription with an authorised request and a valid tavc Id" when {
      "tavc ID parameter XWTAVC000435628 is passed" should {
        val result = TestController.getSubscription(TavcReferenceConstants.subNoContactDetailsRef).apply(validRequest)
        "return the expected Json with no contact details" which {
          val json = Json.parse(contentAsString(result))
          val surname = (json \ "subscriptionType" \ "correspondenceDetails" \ "contactName" \ "name2").validate[String].get
          "has the expected surname" in {
            surname shouldBe "Black"
          }
          "Json should be as expected" in {
            json shouldEqual JsonResponses.getSubNoContactDetails
          }
        }
        "return OK" in {
          status(result) shouldBe OK
        }
      }
    }

    "SubscriptionStubController.getSubscription with an authorised request and a valid tavc Id" when {
      "tavc ID parameter XWTAVC000616234 is passed" should {
        val result = TestController.getSubscription(TavcReferenceConstants.subMinimumRef).apply(validRequest)
        "return the expected Json as the minimum allowed by the schema" which {
          val json = Json.parse(contentAsString(result))
          val forename = (json \ "subscriptionType" \ "correspondenceDetails" \ "contactName" \ "name1").validate[String].get
          "has the expected forename" in {
            forename shouldBe "J"
          }
          "Json should be as expected" in {
            json shouldEqual JsonResponses.getSubMinimum
          }
        }
        "return OK" in {
          status(result) shouldBe OK
        }
      }
    }

    "SubscriptionStubController.getSubscription with an authorised request and a valid tavc Id" when {
      "tavc ID parameter XBTAVC000739704 is passed" should {
        val result = TestController.getSubscription(TavcReferenceConstants.subMinimumForeignAddressAndDetailsRef).apply(validRequest)
        "return the expected Json as the minimum allowed by the schema when foreign address and details present" which {
          val json = Json.parse(contentAsString(result))
          val forename = (json \ "subscriptionType" \ "correspondenceDetails" \ "contactName" \ "name1").validate[String].get
          "has the expected forename" in {
            forename shouldBe "J"
          }
          "Json should be as expected" in {
            json shouldEqual JsonResponses.getSubMinForeignAddressWithDetails
          }
        }
        "return OK" in {
          status(result) shouldBe OK
        }
      }
    }

    "SubscriptionStubController.getSubscription with an authorised request and a valid tavc Id" when {
      "tavc ID parameter XATAVC000421817 is passed" should {
        val result = TestController.getSubscription(TavcReferenceConstants.subMinimumUkAddressAndDetailsRef).apply(validRequest)
        "return the expected Json as the minimum allowed by the schema when UK address and details present" which {
          val json = Json.parse(contentAsString(result))

          val postCode = (json \ "subscriptionType" \ "correspondenceDetails" \ "contactAddress" \ "postalCode").validate[String].get
          "has the expected postcode" in {
            postCode shouldBe "BB1 6AA"
          }
          "Json should be as expected" in {
            json shouldEqual JsonResponses.getSubMinUkAddressWithDetails
          }
        }
        "return OK" in {
          status(result) shouldBe OK
        }
      }
    }

    "SubscriptionStubController.getSubscription with an authorised request and a valid tavc Id" when {
      "tavc ID parameter XZTAVC000123456 is passed" should {
        val result = TestController.getSubscription(TavcReferenceConstants.subSubmissionErrorRef).apply(validRequest)
        "return the expected Json as the minimum allowed by the schema when UK address and details present" which {
          val json = Json.parse(contentAsString(result))

          val postCode = (json \ "subscriptionType" \ "correspondenceDetails" \ "contactAddress" \ "postalCode").validate[String].get
          "has the expected postcode" in {
            postCode shouldBe "BB1 6AA"
          }
          "Json should be as expected" in {
            json shouldEqual JsonResponses.getSubmissionErrorSub
          }
        }
        "return OK" in {
          status(result) shouldBe OK
        }
      }
    }

    "SubscriptionStubController.getSubscription with an authorised request and a valid tavc Id" when {
      "tavc ID parameter XZTAVC000234561 is passed" should {
        val result = TestController.getSubscription(TavcReferenceConstants.subResourceNotFoundRef).apply(validRequest)
        "return the expected Json as the minimum allowed by the schema when UK address and details present" which {
          val json = Json.parse(contentAsString(result))

          val postCode = (json \ "subscriptionType" \ "correspondenceDetails" \ "contactAddress" \ "postalCode").validate[String].get
          "has the expected postcode" in {
            postCode shouldBe "BB1 6AA"
          }
          "Json should be as expected" in {
            json shouldEqual JsonResponses.getResourceNotFoundSub
          }
        }
        "return OK" in {
          status(result) shouldBe OK
        }
      }
    }

    "SubscriptionStubController.getSubscription with an authorised request and a valid tavc Id" when {
      "tavc ID parameter XZTAVC000345612 is passed" should {
        val result = TestController.getSubscription(TavcReferenceConstants.subServerErrorRef).apply(validRequest)
        "return the expected Json as the minimum allowed by the schema when UK address and details present" which {
          val json = Json.parse(contentAsString(result))

          val postCode = (json \ "subscriptionType" \ "correspondenceDetails" \ "contactAddress" \ "postalCode").validate[String].get
          "has the expected postcode" in {
            postCode shouldBe "BB1 6AA"
          }
          "Json should be as expected" in {
            json shouldEqual JsonResponses.getServerErrorSub
          }
        }
        "return OK" in {
          status(result) shouldBe OK
        }
      }
    }

    "SubscriptionStubController.getSubscription with an authorised request and a valid tavc Id" when {
      "tavc ID parameter XZTAVC000456123 is passed" should {
        val result = TestController.getSubscription(TavcReferenceConstants.subServiceUnavailableRef).apply(validRequest)
        "return the expected Json as the minimum allowed by the schema when UK address and details present" which {
          val json = Json.parse(contentAsString(result))

          val postCode = (json \ "subscriptionType" \ "correspondenceDetails" \ "contactAddress" \ "postalCode").validate[String].get
          "has the expected postcode" in {
            postCode shouldBe "BB1 6AA"
          }
          "Json should be as expected" in {
            json shouldEqual JsonResponses.getServiceUnavailableSub
          }
        }
        "return OK" in {
          status(result) shouldBe OK
        }
      }
    }

    "invalid json is sent for XVTAVC000280665" should {
      val result = TestController.getSubscription(TavcReferenceConstants.notFoundRef).apply(validRequest)
      "return an Nolid json response" in {
        contentAsString(result) shouldBe ""
      }
      "return NOT_FOUND" in {
        status(result) shouldBe NOT_FOUND
      }
    }

    "invalid json is sent for XVTAVC000043633" should {
      val result = TestController.getSubscription(TavcReferenceConstants.badRequestRefOneOrMoreErrors).apply(validRequest)
      "return an invalid json response" in {
        contentAsString(result) shouldBe """{"reason" : "Your submission contains one or more errors"}"""
      }
      "return BAD_REQUEST" in {
        status(result) shouldBe BAD_REQUEST
      }
    }

    "invalid json is sent for XLTAVC000453774" should {
      val result = TestController.getSubscription(TavcReferenceConstants.badRequestRefInvalidJsonMessage).apply(validRequest)
      "return an invalid json response" in {
        contentAsString(result) shouldBe """{"reason" : "Invalid JSON message received"}"""
      }
      "return BAD_REQUEST" in {
        status(result) shouldBe BAD_REQUEST
      }
    }

    "invalid json is sent for XZTAVC000655021" should {
      val result = TestController.getSubscription(TavcReferenceConstants.badRequesDuplicateSubmissionRef).apply(validRequest)
      "return an invalid json response" in {
        contentAsString(result) shouldBe """{"reason" : "Error 004"}"""
      }
      "return BAD_REQUEST" in {
        status(result) shouldBe BAD_REQUEST
      }
    }

    "invalid json is sent for XUTAVC000548324" should {
      val result = TestController.getSubscription(TavcReferenceConstants.serverErrorRef).apply(validRequest)
      "return an invalid json response" in {
        contentAsString(result) shouldBe """{"reason" : "Server error"}"""
      }
      "return INTERNAL_SERVER_ERROR" in {
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }

    "invalid json is sent for XLTAVC000657291" should {
      val result = TestController.getSubscription(TavcReferenceConstants.serverErrorRegimeRef).apply(validRequest)
      "return an invalid json response" in {
        contentAsString(result) shouldBe """{"reason" : "Error 001"}"""
      }
      "return INTERNAL_SERVER_ERROR" in {
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }

    "invalid json is sent for XPTAVC000808267" should {
      val result = TestController.getSubscription(TavcReferenceConstants.serverErrorSAPmissingRef).apply(validRequest)
      "return an invalid json response" in {
        contentAsString(result) shouldBe """{"reason" : "Error 002"}"""
      }
      "return INTERNAL_SERVER_ERROR" in {
        status(result) shouldBe INTERNAL_SERVER_ERROR
      }
    }

    "invalid json is sent for XXTAVC000829816" should {
      val result = TestController.getSubscription(TavcReferenceConstants.serviceUnavailableNotRespondingRef).apply(validRequest)
      "return an invalid json response" in {
        contentAsString(result) shouldBe """{"reason" : "Service unavailable"}"""
      }
      "return SERVICE_UNAVAILABLE" in {
        status(result) shouldBe SERVICE_UNAVAILABLE
      }
    }

    "invalid json is sent for XWTAVC000321083" should {
      val result = TestController.getSubscription(TavcReferenceConstants.serviceUnavailable003Ref).apply(validRequest)
      "return an invalid json response" in {
        contentAsString(result) shouldBe """{"reason" : "Error 003"}"""
      }
      "return SERVICE_UNAVAILABLE" in {
        status(result) shouldBe SERVICE_UNAVAILABLE
      }
    }

    "invalid json is sent for XYTAVC000960695" should {
      val result = TestController.getSubscription(TavcReferenceConstants.serviceUnavailable999Ref).apply(validRequest)
      "return an invalid json response" in {
        contentAsString(result) shouldBe """{"reason" : "Error 999"}"""
      }
      "return SERVICE_UNAVAILABLE" in {
        status(result) shouldBe SERVICE_UNAVAILABLE
      }
    }

    "invalid tavc ref XYTA000960695" should {
      val result = TestController.getSubscription(TavcReferenceConstants.invalidRef1).apply(validRequest)
      "return an invalid json response" in {
        contentAsString(result) shouldBe """{"reason" : "Your submission contains one or more errors"}"""
      }
      "return BAD_REQUEST" in {
        status(result) shouldBe BAD_REQUEST
      }
    }

    "invalid tavc ref XYTAVD000960695" should {
      val result = TestController.getSubscription(TavcReferenceConstants.invalidRef2).apply(validRequest)
      "return an invalid json response" in {
        contentAsString(result) shouldBe """{"reason" : "Your submission contains one or more errors"}"""
      }
      "return BAD_REQUEST" in {
        status(result) shouldBe BAD_REQUEST
      }
    }
  }

}
