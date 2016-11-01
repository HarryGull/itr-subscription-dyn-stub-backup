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

package auth

import play.api.mvc.{Request, Result}

import scala.concurrent.Future

trait Authorisation {

  val noAuthHeaderError: String = "required header 'Authorisation' not set in ETMP request"
  val noEnvHeaderError: String = "required header 'Environment' not set in ETMP request"
  val badEnvHeaderError: String = "required header 'Environment' is invalid"
  val combinedError: String = s"$noAuthHeaderError and $noEnvHeaderError"
  val validEnvironments = Seq("isit","clone","live")

  def authorised(f: => AuthResponse => Future[Result])(implicit request: Request[Any]): Future[Result] = {
    val environment =request.headers.toMap.get("Environment")
    val token = request.headers.toMap.get("Authorization")
    play.Logger.info(s"""Request headers: environment = ${environment.getOrElse("<NOT SET>")}, authorisation=" + ${token.getOrElse("<NOT SET>")})""")
    (environment, token) match {
      case (Some(e), Some(t)) => {
        validEnvironments.find(_.equals(e.last)) match {
          case Some(_) => f(Authorised)
          case None => f(new NotAuthorised(badEnvHeaderError))
        }
      }
      case (Some(e), None) => f(new NotAuthorised(noAuthHeaderError))
      case (None, Some(t)) => f(new NotAuthorised(noEnvHeaderError))
      case (None, None) => f(new NotAuthorised(combinedError))
    }
  }
}