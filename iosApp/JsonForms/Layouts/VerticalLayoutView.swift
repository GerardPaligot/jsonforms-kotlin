//
//  VerticalLayoutView.swift
//  iosApp
//
//  Created by Gérard PALIGOT on 21/03/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct VerticalLayoutView<Content: View>: View {
    let uiSchema: VerticalLayout
    let content: (Control) -> Content
    
    init(uiSchema: VerticalLayout, content: @escaping (Control) -> Content) {
        self.uiSchema = uiSchema
        self.content = content
    }
    
    var body: some View {
        Section {
            ForEach(0...(self.uiSchema.elements.count - 1), id: \.self) { index in
                let element = self.uiSchema.elements[index] as! UiSchema
                LayoutView(uiSchema: element, content: self.content)
            }
        }
    }
}
