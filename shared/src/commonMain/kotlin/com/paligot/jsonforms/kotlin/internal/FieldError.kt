package com.paligot.jsonforms.kotlin.internal

import com.paligot.jsonforms.kotlin.models.schema.Property
import kotlinx.serialization.json.JsonPrimitive

sealed class FieldError(val scope: String, val message: String) {
    class RequiredFieldError(scope: String) : FieldError(scope, "Field required $scope")

    class InvalidValueFieldError(val value: JsonPrimitive, scope: String) :
        FieldError(scope, "Field $scope has invalid value $value")

    class InvalidEnumFieldError(val enum: List<String>, scope: String) :
        FieldError(scope, "Field $scope must be one of ${enum.joinToString(",")}")

    class PatternFieldError(val pattern: String, scope: String) :
        FieldError(scope, "Field $scope must match pattern $pattern")

    class MaxLengthFieldError(val maxLength: Int, scope: String) :
        FieldError(scope, "Field $scope must have at most $maxLength characters")

    class MinLengthFieldError(val minLength: Int, scope: String) :
        FieldError(scope, "Field $scope must have at least $minLength characters")

    class MinValueFieldError(val minValue: Int, scope: String) :
        FieldError(scope, "Field $scope must have at least $minValue")

    class MaxValueFieldError(val maxValue: Int, scope: String) :
        FieldError(scope, "Field $scope must have at most $maxValue")

    class MalformedFieldError(scope: String) : FieldError(scope, "Field malformed $scope")

    class InvalidNotPropertyError(val not: Property, scope: String) :
        FieldError(scope, "Field $scope must not be $not")
}
