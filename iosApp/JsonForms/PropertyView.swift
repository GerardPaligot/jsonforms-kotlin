//
//  PropertyView.swift
//  iosApp
//
//  Created by Gérard PALIGOT on 21/03/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct PropertyView: View {
    let control: Control
    let provider: SchemaProvider
    let controller: JsonFormController
    
    init(
        control: Control,
        provider: SchemaProvider,
        controller: JsonFormController
    ) {
        self.control = control
        self.provider = provider
        self.controller = controller
    }
    
    var body: some View {
        let id = self.control.propertyKey()
        let required = self.provider.propertyIsRequired(control: self.control, data: self.controller.map)
        let property = provider.getPropertyByControl(control: control)
        let title = property.label(required: required, control: self.control) ?? ""
        
        Group {
            switch property {
            case is StringProperty:
                TextFieldStringView(title, text: self.controller.binding(for: id))
                
            default:
                Text("Not implemented.")
            }
        }
    }
}
