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


object EmailConstants {

  val messageNotVerfified = "Token not found or expired."
  val codeNotVerfified = "EMAIL_NOT_FOUND_OR_NOT_VERIFIED"

  val messageAlreadyVerfified = "Email has already been verified"
  val codeAlreadyVerfified = "EMAIL_VERIFIED_ALREADY"

  val messageBadEmailRequest = "Template not found"
  val codeBadEmailRequest = "BAD_EMAIL_REQUEST"


  val messageUpstreamError = "POST of 'http://localhost:11111/send-templated-email' returned 500. Response body: 'some-5xx-message'"
  val codeUpstreamError = "UPSTREAM_ERROR"

  val messageUnexpectedError = "An unexpected error occured"
  val codeUnexpectedError = "UNEXPECTED_ERROR"

  val codeNotFound = "NOT_FOUND"
  val messageNotFound = "URI not found"
  val detailNotFoundKey = "requestedUrl"
  val detailNotFoundMessage = "/email-verification/non-existent-url"

  val codeValidationError = "VALIDATION_ERROR"
  val messageValidationError = "Payload validation failed"
  val detailValidationErrorKey = "obj.email"
  val detailValidationErrorMessage = "error.path.missing"





}
