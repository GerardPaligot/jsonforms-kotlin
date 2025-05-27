`jsonforms-kotlin` is a Kotlin Multiplatform implementation of the JSONForms standard from the 
Eclipse Foundation. It leverages Compose Multiplatform to render dynamic forms based on 
JSON Schemas and UI Schemas.

This project is built with Kotlin Multiplatform and Compose Multiplatform.

| Platform      | Supported |
|---------------|-----------|
| Android       | ✅         |
| Desktop (JVM) | ✅         |
| iOS           | ✅         |
| Wasm          | ❌         |
| JS/Canvas     | ❌         |

## Download

The `jsonforms-kotlin` library is not yet available on Maven Central in release mode but you can 
already use the latest SNAPSHOT version, `1.0.0-SNAPSHOT`:

```kotlin
val version = "1.0.0-SNAPSHOT"
dependencies {
    implementation("com.paligot.jsonforms.kotlin:core:$version")
    implementation("com.paligot.jsonforms.kotlin:ui:$version")
    implementation("com.paligot.jsonforms.kotlin:material3:$version")
    implementation("com.paligot.jsonforms.kotlin:cupertino:$version")
}
```

## License

```
Copyright 2025 Gérard Paligot.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

