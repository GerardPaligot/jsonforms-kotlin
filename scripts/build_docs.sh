#!/bin/bash

set -ex

# Generate the API reference documentation using Dokka
./gradlew dokkaHtmlMultiModule
mv ./build/dokka/htmlMultiModule docs/api

# Build the site locally
mkdocs build
