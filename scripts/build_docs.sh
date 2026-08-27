#!/bin/bash

set -ex

# Generate the API reference documentation using Dokka
./gradlew dokkaGeneratePublicationHtml
mv ./build/dokka/html docs/api

# Build the site locally
mkdocs build
