import UIKit
import SwiftUI
import sharedLiveAnim

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
         .ignoresSafeArea(edges: .all) // For using status/bottom bars area
         .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}