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

package controllers

import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.play.test.UnitSpec
import org.scalatest.mock.MockitoSugar
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import common.{EmailConstants, EmailJsonResponses}
import model.{EmailVerificationRequestModel, VerificationErrorResponseModel, VerifiedEmailResponseModel}
import org.scalatestplus.play.OneAppPerSuite
import play.api.libs.json
import play.api.libs.json.Json


class EmailVerificationControllerSpec extends UnitSpec with OneAppPerSuite with MockitoSugar {

  implicit lazy val actorSystem = ActorSystem()
  implicit lazy val mat = ActorMaterializer()

  private class Setup {

    object TestController extends EmailStubController {
    }

  }

  // POST REQUESTS

  "The email stub POST request" should {
    "return a success created by default for email not designed to cause an error" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("somenonerroremail@test.com")))
      status(result) shouldBe CREATED
    }
  }

  "The email stub POST request" should {
    "return a not Found status with expected json error response for email containing notfound" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testnotfoundtest@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeNotFound
      emailResponse.message shouldBe EmailConstants.messageNotFound
      emailResponse.details.get.head._1 shouldBe EmailConstants.detailNotFoundKey
      emailResponse.details.get.head._2 shouldBe EmailConstants.detailNotFoundMessage
      status(result) shouldBe NOT_FOUND

    }
  }

  "The email stub POST request" should {
    "return a bad request status with expected validation error json error response for email containing badrequest" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testbadrequesttest@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeValidationError
      emailResponse.message shouldBe EmailConstants.messageValidationError
      emailResponse.details.get.head._1 shouldBe EmailConstants.detailValidationErrorKey
      emailResponse.details.get.head._2 shouldBe EmailConstants.detailValidationErrorMessage
      status(result) shouldBe BAD_REQUEST

    }
  }

  "The email stub POST request" should {
    "return a conflict request status with expected already verified error json error response for email containing alreadyverified" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testalreadyverifiedtest@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeAlreadyVerfified
      emailResponse.message shouldBe EmailConstants.messageAlreadyVerfified
      emailResponse.details shouldBe None
      status(result) shouldBe CONFLICT

    }
  }

  "The email stub POST request" should {
    "return a not Found status with expected not verified json error response for email containing notokenfound" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testnotokenfoundtest@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeNotVerfified
      emailResponse.message shouldBe EmailConstants.messageNotVerfified
      emailResponse.details shouldBe None
      status(result) shouldBe NOT_FOUND

    }
  }

  "The email stub POST request" should {
    "return an internal server error status with expected unexpected error json error response for email containing internalservererrorrequest" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testinternalservererrorrequesttest@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeUnexpectedError
      emailResponse.message shouldBe EmailConstants.messageUnexpectedError
      emailResponse.details shouldBe None
      status(result) shouldBe INTERNAL_SERVER_ERROR

    }
  }

  "The email stub POST request" should {
    "return an bad request error status with expected bad email request error json error response for email containing bademailrequest" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testibademailrequesttest@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeBadEmailRequest
      emailResponse.message shouldBe EmailConstants.messageBadEmailRequest
      emailResponse.details shouldBe None
      status(result) shouldBe BAD_REQUEST

    }
  }

  "The email stub POST request" should {
    "return a bad gateway error status with expected upstream error json error response for email containing upstreamerror" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testupstreamerrortest@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeUpstreamError
      emailResponse.message shouldBe EmailConstants.messageUpstreamError
      emailResponse.details shouldBe None
      status(result) shouldBe BAD_GATEWAY

    }
  }

  // notverifiedWithResponse

  "The email stub POST request" should {
    "return a not Found status with expected not verified json error response for email containing notverified404A" in new Setup {
      println(EmailJsonResponses.getPostJson("notverified404A@test.com"))

      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testnotverified404atest@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeNotVerfified
      emailResponse.message shouldBe EmailConstants.messageNotVerfified
      emailResponse.details shouldBe None
      status(result) shouldBe NOT_FOUND

    }
  }
  "The email stub POST request" should {
    "return a not Found status with expected not verified json error response for email containing notverified404b" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testnotverified404btest@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeNotFound
      emailResponse.message shouldBe EmailConstants.messageNotFound
      emailResponse.details.get.head._1 shouldBe EmailConstants.detailNotFoundKey
      emailResponse.details.get.head._2 shouldBe EmailConstants.detailNotFoundMessage
      status(result) shouldBe NOT_FOUND

    }
  }

  "The email stub POST request" should {
    "return a not bad gateway status with expected not verified json error response for email containing notverified502" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testnotverified502test@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeUpstreamError
      emailResponse.message shouldBe EmailConstants.messageUpstreamError
      emailResponse.details shouldBe None
      status(result) shouldBe BAD_GATEWAY

    }
  }

  "The email stub POST request" should {
    "return an internal server error status with expected not verified json error response for email containing notverified500" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testnotverified500test@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeUnexpectedError
      emailResponse.message shouldBe EmailConstants.messageUnexpectedError
      emailResponse.details shouldBe None
      status(result) shouldBe INTERNAL_SERVER_ERROR

    }
  }

  "The email stub POST request" should {
    "return a bad request status with expected not verified json error response for email containing notverified400" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testnotverified400test@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeValidationError
      emailResponse.message shouldBe EmailConstants.messageValidationError
      emailResponse.details.get.head._1 shouldBe EmailConstants.detailValidationErrorKey
      emailResponse.details.get.head._2 shouldBe EmailConstants.detailValidationErrorMessage
      status(result) shouldBe BAD_REQUEST

    }
  }

  "The email stub POST request" should {
    "return a bad request status with expected not verified json error response for email containing notverified400b" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testnotverified400btest@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeValidationError
      emailResponse.message shouldBe EmailConstants.messageValidationError
      emailResponse.details.get.head._1 shouldBe EmailConstants.detailValidationErrorKey
      emailResponse.details.get.head._2 shouldBe EmailConstants.detailValidationErrorMessage
      status(result) shouldBe BAD_REQUEST

    }
  }

  "The email stub POST request" should {
    "return a conflict status with expected not verified json error response for email containing notverified409" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testnotverified409test@test.com")))
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeAlreadyVerfified
      emailResponse.message shouldBe EmailConstants.messageAlreadyVerfified
      emailResponse.details shouldBe None
      status(result) shouldBe CONFLICT

    }
  }

  "The email stub POST request" should {
    "return a created email containing notverified201" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("testnotverified201test@test.com")))
      status(result) shouldBe CREATED
    }
  }

  //getSubmittedJson
  "The email stub POST request" should {
    "return a bad request and the submitted Json as the return response" in new Setup {
      val result = TestController.verificationRequest().apply(FakeRequest().withBody(EmailJsonResponses.getPostJson("getsubmittedjson@test.com")))
      val emailResponse: EmailVerificationRequestModel = jsonBodyOf(result).as[EmailVerificationRequestModel]
      status(result) shouldBe BAD_REQUEST
    }
  }

  // GET REQUESTS
  "The email stub GET request" should {
    "return a sucess for default email not designed to simulate a failure response" in new Setup {
      val result = TestController.verifyEmailAddress("noerrorexpected@test.com").apply(FakeRequest())
      val emailResponse: VerifiedEmailResponseModel = jsonBodyOf(result).as[VerifiedEmailResponseModel]
      emailResponse.email shouldBe "noerrorexpected@test.com"
      status(result) shouldBe OK
    }
  }


  "The email stub GET request" should {
    "return an internal server error status with expected unexpected error json error response for email containing internalservererrorrequest" in new Setup {
      val result = TestController.verifyEmailAddress("testinternalservererrorrequesttest@test.com").apply(FakeRequest())
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeUnexpectedError
      emailResponse.message shouldBe EmailConstants.messageUnexpectedError
      emailResponse.details shouldBe None
      status(result) shouldBe INTERNAL_SERVER_ERROR

    }
  }

  "The email stub GET request" should {
    "return a bad gateway error status with expected upstream error json error response for email containing upstreamerror" in new Setup {
      val result = TestController.verifyEmailAddress("testupstreamerrortest@test.com").apply(FakeRequest())
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeUpstreamError
      emailResponse.message shouldBe EmailConstants.messageUpstreamError
      emailResponse.details shouldBe None
      status(result) shouldBe BAD_GATEWAY

    }
  }

  "The email stub GET request" should {
    "return a not Found status with expected not verified json error response for email containing notokenfound" in new Setup {
      val result = TestController.verifyEmailAddress("testnotokenfoundtest@test.com").apply(FakeRequest())
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeNotVerfified
      emailResponse.message shouldBe EmailConstants.messageNotVerfified
      emailResponse.details shouldBe None
      status(result) shouldBe NOT_FOUND

    }
  }

  "The email stub GET request" should {
    "return a not Found status with expected json error response for email containing notfound" in new Setup {
      val result = TestController.verifyEmailAddress("testnotfoundtest@test.com").apply(FakeRequest())
      val emailResponse: VerificationErrorResponseModel = jsonBodyOf(result).as[VerificationErrorResponseModel]
      emailResponse.code shouldBe EmailConstants.codeNotFound
      emailResponse.message shouldBe EmailConstants.messageNotFound
      emailResponse.details.get.head._1 shouldBe EmailConstants.detailNotFoundKey
      emailResponse.details.get.head._2 shouldBe EmailConstants.detailNotFoundMessage
      status(result) shouldBe NOT_FOUND

    }
  }

}
