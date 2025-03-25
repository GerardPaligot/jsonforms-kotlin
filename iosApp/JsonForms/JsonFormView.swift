//
//  JsonFormView.swift
//  iosApp
//
//  Created by Gérard PALIGOT on 21/03/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct JsonFormView: View {
    let schema: Schema
    let uiSchema: UiSchema
    let provider: SchemaProvider
    let controller: JsonFormController
    
    init(schema: Schema, uiSchema: UiSchema, data: [String: AnyObject] = [:]) {
        self.schema = schema
        self.uiSchema = uiSchema
        self.provider = SchemaProviderImpl(
            uiSchema: self.uiSchema,
            schema: self.schema
        )
        self.controller = JsonFormController(map: data)
    }
    
    var body: some View {
        let count = self.uiSchema.elements?.count ?? 0
        ForEach(0...(count - 1), id: \.self) { index in
            let element = self.uiSchema.elements![index] as UiSchema
            LayoutView(uiSchema: element) { control in
                PropertyView(control: control, provider: self.provider, controller: self.controller)
            }
        }
    }
}

#Preview {
    let schema = Schema(
        properties: [
            "#/properties/login": StringProperty(title: nil, format: nil, description: nil, readOnly: nil, pattern: nil, minLength: nil, maxLength: nil, enum: nil, oneOf: nil)
        ],
        required: [],
        title: nil,
        format: nil,
        description: nil,
        readOnly: nil,
        anyOf: nil,
        oneOf: nil,
        allOf: nil
    )
    let uiSchema = VerticalLayout(
        elements: [
            Control(scope: "#/properties/login", label: "Login", options: nil, elements: nil, rule: nil)
        ],
        rule: nil,
        options: nil
    )
    JsonFormView(
        schema: schema,
        uiSchema: uiSchema
    )
}
