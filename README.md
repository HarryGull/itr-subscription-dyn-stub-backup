# investment-tax-relief-subscription-dynamic-stub

[![Apache-2.0 license](http://img.shields.io/badge/license-Apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html) [![Build Status](https://travis-ci.org/hmrc/investment-tax-relief-subscription-dynamic-stub.svg?branch=master)](https://travis-ci.org/hmrc/investment-tax-relief-subscription-dynamic-stub) [ ![Download](https://api.bintray.com/packages/hmrc/releases/investment-tax-relief-subscription-dynamic-stub/images/download.svg) ](https://bintray.com/hmrc/releases/investment-tax-relief-subscription-dynamic-stub/_latestVersion)

This is a stub for the Tax Relief Subscription Service. The stub is a test double that supports the Tax Relief Submission Service REST API in development or test environments, this enables testing of clients of the service without requiring a full end-to-end test environment that has all the backend services and systems available.

The stub is a Play/Scala application backed by a Mongo database for the test data, which can be dynamically created (hence it is termed a dynamic stub, because it does not contain hardcoded, static test data). The test data can be set up either by making requests to the relevant apply or amend operations of the API, or directly loaded into the database using e.g. `mongoimport`. 

This stub also implements various test scenarios (success/failure http responses) based on the name in the submission JSON.

The stub supports these Tax Relief Subscription Service API operations:

API
----

| PATH | Supported Methods |
|------|-------------------|
|Simulate a TAVC service Subscription and various HTTP responses: |
|```/taxpayers/:safeId/subscription``` | POST |
|Get the company registration details for specified safeID:|
|```/taxpayers/:tavcRefNumber/subscription``` | GET |

   
Requirements
------------

This service is written in [Scala](http://www.scala-lang.org/) and [Play](http://playframework.com/), so needs at least a [JRE] to run.


## Run the application


To update from Nexus and start all services from the RELEASE version instead of snapshot

```
sm --start TAVC_ALL -f
```

 
##To run the application locally execute the following:


Kill the service ```sm --stop ITR_SUBSC_DYNAMIC_STUB``` in service Manager and run:
```
sbt 'run 9640' 
```
*This service is part of the investment tax relief service and has dependent services.*
*For a full list of the dependent microservices that comprise this service please see the readme for our* [Submission Frontend Service](https://github.com/hmrc/investment-tax-relief-submission-frontend/)


### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html")

This is a stub for the Tax Relief Subscription Service. The stub is a test double that supports the Tax Relief subscription Service REST API in development or test environments, this enables testing of clients of the service without requiring a full end-to-end test environment that has all the backend services and systems available.

The stub is a Play/Scala application backed by a Mongo database for the test data, which is dynamically created (hence it is termed a dynamic stub, because it does not contain hardcoded, static test data). The test data can be set up either by making requests to the relevant apply or amend operations of the API, or directly loaded into the database using e.g. `mongoimport`. 

The stub supports these Tax Relief Subscription Service API operations:



/*** Example subscription Json ***/
````
Example Create Subscription Request JSON 
  {
  	"acknowledgementReference": "ABCD123",
  	"subscriptionType": {
  		"correspondenceDetails": {
  			"contactName": {
  				"name1": "John",
  				"name2": "Smith"
  			},
  			"contactDetails": {
  				"phoneNumber": "07000 000000",
  				"mobileNumber": "010000 000000",
  				"faxNumber": "010000 2000000",
  				"emailAddress": "john.smith@noplaceanywhereDzx.com"
  			},
  			"contactAddress": {
  				"addressLine1": "Line 1",
  				"addressLine2": "Line 2",
  				"addressLine3": "Line 3",
  				"addressLine4": "Line 4",
  				"countryCode": "GB",
  				"postalCode": "AA 1AA"
  			}
  		}
  	}
  }
  ```
  
  
  Example Get Subscription details JSON Response:
  ```
  {
      "processingDate": "2001-12-17T09:30:47Z",
      "subscriptionType": {
          "safeId": "XA0000000012345",
          "correspondenceDetails": {
              "contactName": {
                  "name1": "John",
                  "name2": "Smith"
              },
              "contactDetails": {
                  "phoneNumber": "0000 000000",
                  "mobileNumber": "00000 000000",
                  "faxNumber": "0000 000000",
                  "emailAddress": "john.smith@noplaceanywhereDzx.co"
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
  }
  ```
