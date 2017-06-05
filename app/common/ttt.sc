import play.api.libs.json.Json

def getPostJson(email:String) = Json.parse(
  s"""
     |{
     |  "email": "$email",
     |  "templateId": "anExistingTemplateInEmailServiceId",
     |  "templateParameters": {
     |    "name": "Gary Doe"
     |  },
     |  "linkExpiryDuration" : "P1D",
     |  "continueUrl" : "http://some-continue.url"
     |}
    """.stripMargin
)

val x = getPostJson("gary@gary.com")
x