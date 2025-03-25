import SwiftUI
import shared

struct ContentView: View {
    let schema = Schema(
        properties: [
            "login": StringProperty(title: nil, format: nil, description: nil, readOnly: nil, pattern: nil, minLength: nil, maxLength: nil, enum: nil, oneOf: nil)
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
	var body: some View {
        JsonFormView(schema: self.schema, uiSchema: self.uiSchema, data: ["login": "" as AnyObject])
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
