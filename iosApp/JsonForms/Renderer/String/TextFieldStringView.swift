//
//  TextFieldStringView.swift
//  iosApp
//
//  Created by Gérard PALIGOT on 21/03/2025.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI

struct TextFieldStringView: View {
    private var title: String
    private var text: Binding<String>
    @FocusState private var isFocused: Bool
    
    init(_ title: String, text: Binding<String>) {
        self.title = title
        self.text = text
    }
    
    var body: some View {
        TextField(
            self.title,
            text: self.text
        )
        .focused($isFocused)
    }
}

#Preview {
    TextFieldStringView("My label", text: .constant(""))
}
