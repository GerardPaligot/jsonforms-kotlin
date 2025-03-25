package com.paligot.jsonforms.kotlin.internal

sealed class FieldError(val scope: String, val message: String) {
    class RequiredFieldError(scope: String) : FieldError(scope, "Field required $scope")

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
}
