# WireMock-stateful-api-testing
WireMock and Rest-Assured for stateful API testing

## WireMock standalone
This script makes use of wiremock standalone jar
- To record start WireMock standalone the following way
```shell
java -jar wiremock-jre8-standalone-<version>.jar --port <7777> \
  --proxy-all="http://<proxy address>:<potrt>" \
  --record-mappings \ 
  --verbose 
```

## Mappings
- Make the mapping by putting your request/responsemappings in the mappings.json file in the mappings dir
- Remember to restart once new mappings have been added. 
- The mappings used in the video [can be found here](./mapping-example.json)

## State
- State can be setup in the mappings, can be called using ```__admin/scenarios```

## Rest Client (desktop)
- Using [insomnia rest client](https://insomnia.rest/) as a rest client
- This is mainly used to setup and play around with the Backend JAVA API

## Application
- Todo Backend applications [more info here](https://todobackend.com/) 
- The specific one I used was [this one] which used Javalin (https://github.com/johanvogelzang/todo-backend)

# References 
- https://wiremock.org/docs/stateful-behaviour/
- https://wiremock.org/docs/record-playback/
- 