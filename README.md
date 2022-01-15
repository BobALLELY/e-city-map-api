# Crime City API
Ce microservice sera hébergé sur internet et donc accessible directement des clients.

## Code Applicatif :
CC-API

## Stack Technique :
- SpringBoot 2
- AdoptOpenJDK 11
- Maven 3.6.3
- OpenAPI generator: https://github.com/OpenAPITools/openapi-generator#12---artifacts-on-maven-central
- OpenAPI generator maven plugin: https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-maven-plugin
- Http client: okhttp + gson

## Swagger
- UI: <base_url>/swagger-ui/index.html?url=/v3/api-docs

## Actuator 
- Base: <base_url>/manage
- Health: <base_url>/health/check
- Metrics: <base_url>/metrics
- Info: <base_url>/info

## Application Maven Installation :
`mvn clean install -DskipTests=true`\

## Application Maven Compilation :
`mvn clean package`\
Maven profile *dev* is use by default, the previous command is same as :\
`mvn clean package -Pdev`\
To compile the application in Production/K8S mode :\
`mvn clean package -Pprod`

## Application Testing :
**Unit testing :**\
`mvn clean test -Pdev`\
**Mutation testing report :**\
`mvn clean test  -Pdev,pitest`\
**To speed-up repeated analysis of the same codebase set the withHistory parameter to true.**\
`mvn clean -DwithHistory test  -Pdev,pitest`\
The mutation testing report in HTML are stored in target/pit-reports folder.\

# Docker

docker run -p8080:8080 --rm -it  space-crime-map-api:latest