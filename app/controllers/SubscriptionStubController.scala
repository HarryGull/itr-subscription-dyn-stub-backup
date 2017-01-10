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
import play.api.{Logger, http}
import uk.gov.hmrc.play.microservice.controller.BaseController
import play.api.libs.json._
import model._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import common.Validation._
import common.Generators._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import common.{JsonResponses, TavcReferenceConstants}

object SubscriptionStubController extends SubscriptionStubController {
}

trait SubscriptionStubController extends BaseController with Authorisation {

  val response = (message: String) => s"""{"reason" : "$message"}"""

  def createSubscription(safeId: String): Action[JsValue] = Action.async(BodyParsers.parse.json) { implicit request =>
    authorised {
      case Authorised => {
        Logger.info(s"[SubscriptionStubController][createSubscription]")
        val subscriptionApplicationBodyJs = request.body.validate[SubscriptionRequest]
        checkApplicationBody(safeId,subscriptionApplicationBodyJs)
      }
      case NotAuthorised(error) => Future.successful(Unauthorized(error))
    }
  }

  def getSubscription(tavcRefNumber: String):Action[AnyContent] = Action.async { implicit request =>
    authorised {
      case Authorised => {
        Logger.info(s"[SubscriptionStubController][getSubscription]")
        if (tavcIdValidationCheck(tavcRefNumber)) {
          getsubscriptionDetailsResponse(tavcRefNumber)
        }
        else {
          Future.successful(BadRequest(response("Your submission contains one or more errors")))
        }
      }
      case NotAuthorised(error) => Future.successful(Unauthorized(error))
    }
  }

  //noinspection ScalaStyle
  private def getsubscriptionDetailsResponse(tavcRefNumber: String): Future[Result] = {
    tavcRefNumber match {
      case TavcReferenceConstants.notFoundRef => {
        Future.successful(NotFound)
      }
      case TavcReferenceConstants.badRequestRefOneOrMoreErrors => {
        Future.successful(BadRequest(response("Your submission contains one or more errors")))
      }
      case TavcReferenceConstants.badRequestRefInvalidJsonMessage => {
        Future.successful(BadRequest(response("Invalid JSON message received")))
      }
      case TavcReferenceConstants.badRequesDuplicateSubmissionRef => {
        Future.successful(BadRequest(response("Error 004")))
      }
      case TavcReferenceConstants.serverErrorRef => {
        Future.successful(InternalServerError(response("Server error")))
      }
      case TavcReferenceConstants.serverErrorRegimeRef => {
        Future.successful(InternalServerError(response("Error 001")))
      }
      case TavcReferenceConstants.serverErrorSAPmissingRef => {
        Future.successful(InternalServerError(response("Error 002")))
      }
      case TavcReferenceConstants.serviceUnavailableNotRespondingRef => {
        Future.successful(ServiceUnavailable(response("Service unavailable")))
      }
      case TavcReferenceConstants.serviceUnavailable003Ref => {
        Future.successful(ServiceUnavailable(response("Error 003")))
      }
      case TavcReferenceConstants.serviceUnavailable999Ref => {
        Future.successful(ServiceUnavailable(response("Error 999")))
      }
      case _ => getMatchingJsonSubscription(tavcRefNumber)
    }
  }

  //noinspection ScalaStyle
  private def getMatchingJsonSubscription(tavcReferenceId: String): Future[Result] = {

    val json = tavcReferenceId match {
      case TavcReferenceConstants.subFullRef =>  JsonResponses.getSubFull
      case TavcReferenceConstants.subNoAddressRef =>  JsonResponses.getSubNoAddress
      case TavcReferenceConstants.subNoContactDetailsRef =>  JsonResponses.getSubNoContactDetails
      case TavcReferenceConstants.subMinimumRef =>  JsonResponses.getSubMinimum
      case TavcReferenceConstants.subMinimumForeignAddressAndDetailsRef => JsonResponses.getSubMinForeignAddressWithDetails
      case TavcReferenceConstants.subMinimumUkAddressAndDetailsRef => JsonResponses.getSubMinUkAddressWithDetails
      case TavcReferenceConstants.subSubmissionErrorRef => JsonResponses.getSubmissionErrorSub
      case TavcReferenceConstants.subResourceNotFoundRef => JsonResponses.getResourceNotFoundSub
      case TavcReferenceConstants.subServerErrorRef => JsonResponses.getServerErrorSub
      case TavcReferenceConstants.subServiceUnavailableRef => JsonResponses.getServiceUnavailableSub

      case _ => JsonResponses.getSubFull
    }

    Future(Status(http.Status.OK)(json))
  }

  //noinspection ScalaStyle
  private def checkApplicationBody(safeId: String, subscriptionApplicationBodyJs: JsResult[SubscriptionRequest]) = {
    subscriptionApplicationBodyJs.fold(
      errors => Future.successful(BadRequest("""{"reason" : "Invalid JSON message received"}""")),
      submitRequest => {
        (safeIdValidationCheck(safeId), submitRequest.subscriptionType.correspondenceDetails.contactName.get.name1) match {
          case (true, "notfound") => Future.successful(NotFound)
          case (true, "duplicate") => Future.successful(BadRequest(response("Error 004")))
          case (true, "servererror") => Future.successful(InternalServerError(response("Server error")))
          case (true, "serviceunavailable") => Future.successful(ServiceUnavailable(response("Service Unavailable")))
          case (true, "serviceunavailableendpoint") => Future.successful(ServiceUnavailable(response("Error 999")))
          case (true, "missingregime") => Future.successful(InternalServerError(response("Error 001")))
          case (true, "sapnumbermissing") => Future.successful(InternalServerError(response("Error 002")))
          case (true, "notprocessed") => Future.successful(ServiceUnavailable(response("Error 003")))
          case (true, "oneormoreerrors") => Future.successful(BadRequest(response("Your submission contains one or more errors")))
          case (true, "malformedJson") => Future.successful(BadRequest("""{"reason" : "Invalid JSON message received"}"""))
          case (true, _) => {
            val TAVCRef = generateTavcReference
            Logger.info(s"[SubscriptionStubController][createSubscription] response is: ${Json.toJson(SubscriptionResponse(currentDateTime, TAVCRef)).toString()}")
            Future.successful(Ok(Json.toJson(SubscriptionResponse(currentDateTime, TAVCRef))))
          }
          case (false, _) => Future.successful(BadRequest(response("Your submission contains one or more errors")))
        }
      }
    )
  }
}
