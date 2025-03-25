//
//  JsonFormController.swift
//  iosApp
//
//  Created by Gérard PALIGOT on 21/03/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

class JsonFormController: ObservableObject {
    @Published var map: [String: AnyObject]
    @Published var errors: [FieldError] = []

    public init(map: [String: AnyObject] = [:]) {
        self.map = map
    }
    
    @MainActor
    public func validate(schema: Schema, uiSchema: UiSchema) async -> Bool {
        let keys = Array(Set(map.keys + schema.requiredKeys()))
        do {
            let uiControls = try await uiSchema.findUiControls(keys: keys)
            let checkRequired = DataFinderKt.checkRequired(map, schema: schema, uiControls: uiControls, data: self.map)
            let checkPatterns = DataFinderKt.checkPatterns(map, schema: schema, uiControls: uiControls)
            errors = checkRequired + checkPatterns
            return errors.isEmpty
        } catch {
            errors = []
            return false
        }
    }
    
    public func binding(for key: String) -> Binding<String> {
        return .init(
            get: { self.map[key] as! String },
            set: { self.map[key] = $0 as AnyObject }
        )
    }
    
    public func binding(for key: String) -> Binding<Bool> {
        return .init(
            get: { self.map[key] as! Bool },
            set: { self.map[key] = $0 as AnyObject }
        )
    }
}
