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


import auth.{Authorisation, Authorised, NotAuthorised}
import common.EmailConstants
import play.api.Logger
import uk.gov.hmrc.play.microservice.controller.BaseController
import model._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import scala.concurrent.Future
import play.api.libs.json.{JsError, Json}
object EmailStubController extends EmailStubController {
}

trait EmailStubController extends BaseController with Authorisation {

  val response = (message: String) => s"""{"reason" : "$message"}"""

  def verificationRequest(): Action[JsValue] = Action.async(BodyParsers.parse.json) { implicit request =>
    //Logger.info(s"[EmailStubController][createSubscription]")
    request.body.validate[EmailVerificationRequestModel].fold(
      invalid = { err =>
        Future.successful(BadRequest(Json.obj("message" -> JsError.toFlatJson(err))))
      },
      valid = { verificationRequest =>
        postEmailVerificationResponse(verificationRequest)
      }
    )
  }

  def verifyEmailAddress(email: String): Action[AnyContent] = Action.async { implicit request =>
    getEmailVerificationResponse(email)
  }


  //noinspection ScalaStyle
  private def postEmailVerificationResponse(verificationModel: EmailVerificationRequestModel): Future[Result] = {
    verificationModel.email.toLowerCase() match {
      case email if email.contains("notokenfound") =>
        Future.successful(NotFound(Json.toJson(VerificationErrorResponseModel(code = EmailConstants.codeNotVerfified,
          message = EmailConstants.messageNotVerfified))))
      case email if email.contains("notfound") =>
        Future.successful(NotFound(Json.toJson(VerificationErrorResponseModel(code = EmailConstants.codeNotFound,
          message = EmailConstants.messageNotFound,
          details = Some(Map(EmailConstants.detailNotFoundKey -> EmailConstants.detailNotFoundMessage))))))
      case email if email.contains("badrequest") =>
        Future.successful(BadRequest(Json.toJson(VerificationErrorResponseModel(code = EmailConstants.codeValidationError,
          message = EmailConstants.messageValidationError,
          details = Some(Map(EmailConstants.detailValidationErrorKey -> EmailConstants.detailValidationErrorMessage))))))
      case email if email.contains("alreadyverified") =>
        Future.successful(Conflict(Json.toJson(VerificationErrorResponseModel(code = EmailConstants.codeAlreadyVerfified,
          message = EmailConstants.messageAlreadyVerfified))))
      case email if email.contains("bademailrequest") =>
        Future.successful(BadRequest(Json.toJson(VerificationErrorResponseModel(code = EmailConstants.codeBadEmailRequest,
          message = EmailConstants.messageBadEmailRequest))))
      case email if email.contains("internalservererrorrequest") =>
        Future.successful(InternalServerError(Json.toJson(VerificationErrorResponseModel(
          code = EmailConstants.codeUnexpectedError, message = EmailConstants.messageUnexpectedError))))
      case email if email.contains("upstreamerror") =>
        Future.successful(BadGateway(Json.toJson(VerificationErrorResponseModel(
          code = EmailConstants.codeUpstreamError, message = EmailConstants.messageUpstreamError))))
      case email if email.contains("getsubmittedjson") => {
        Logger.info(s"[EmailStubController][getsubmittedJson] is: ${Json.toJson(verificationModel)}")
        Future.successful(BadRequest(Json.toJson(verificationModel)))
      }
      case _ => {
        Future.successful(Created)
      }
    }
  }

  //noinspection ScalaStyle
  private def getEmailVerificationResponse(emailAddress: String): Future[Result] = {

    emailAddress.toLowerCase() match {
      case email if email.contains("notokenfound") =>
        Future.successful(NotFound(Json.toJson(VerificationErrorResponseModel(code = EmailConstants.codeNotVerfified,
          message = EmailConstants.messageNotVerfified))))
      case email if email.contains("notfound") =>
        Future.successful(NotFound(Json.toJson(VerificationErrorResponseModel(code = EmailConstants.codeNotFound,
          message = EmailConstants.messageNotFound,
          details = Some(Map(EmailConstants.detailNotFoundKey -> EmailConstants.detailNotFoundMessage))))))
      case email if email.contains("internalservererrorrequest") =>
        Future.successful(InternalServerError(Json.toJson(VerificationErrorResponseModel(
          code = EmailConstants.codeUnexpectedError, message = EmailConstants.messageUnexpectedError))))
      case email if email.contains("upstreamerror") =>
        Future.successful(BadGateway(Json.toJson(VerificationErrorResponseModel(
          code = EmailConstants.codeUpstreamError, message = EmailConstants.messageUpstreamError))))
      case _ => {
        Future.successful(Ok(Json.toJson(VerifiedEmailResponseModel(email = emailAddress))))
      }
    }
  }

}
