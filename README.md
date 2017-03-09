# Summary

Generated ODL base project based on Boron-SR2 startup archetype using :

`mvn archetype:generate -DarchetypeGroupId=org.opendaylight.controller \
 -DarchetypeArtifactId=opendaylight-startup-archetype \
 -DarchetypeVersion=1.2.2-Boron-SR2 \
 -DarchetypeRepository=http://nexus.opendaylight.org/content/repositories/opendaylight.release/ \
 -DarchetypeCatalog=http://nexus.opendaylight.org/content/repositories/opendaylight.release/archetype-catalog.xml
 `

and loading objects from a CSV file and saving them as JSON/XML using JAXB.

# Features

- Parsing a CSV file to POJO using `opencsv` and its annotation-based approach
- Enhanced default CSV parser to trim leading and trailing spaces
- Converting model objects to JSON/XML using `EclipseLink MOXy`
- Integrated new OSGI bundles/dependencies to keep ODL project build green
- Implemented standard and parametrized unit tests, assertions performed using `assertj` library 

# Building project

You need to tweak your `~/.m2/settings.xml` to use ODL mirror repositories using :
 
`wget -q -O - https://raw.githubusercontent.com/opendaylight/odlparent/master/settings.xml > ~/.m2/settings.xml`

and then :

`mvn clean install`
