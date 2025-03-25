//
//  LayoutView.swift
//  iosApp
//
//  Created by Gérard PALIGOT on 21/03/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LayoutView<Content: View>: View {
    let uiSchema: UiSchema
    let content: (Control) -> Content
    
    init(uiSchema: UiSchema, content: @escaping (Control) -> Content) {
        self.uiSchema = uiSchema
        self.content = content
    }
    
    var body: some View {
        switch uiSchema {
        case is VerticalLayout:
            VerticalLayoutView(uiSchema: self.uiSchema as! VerticalLayout, content: self.content)
        case is Control:
            self.content(self.uiSchema as! Control)
        default:
            Text("Not implemented.")
        }
    }
}
