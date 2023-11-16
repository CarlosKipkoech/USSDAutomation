package com.codezila.ussdautomation

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast

class USSDService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

        if (event?.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            // Check if the current window content contains USSD code
            selectUSSDOption("1")
            if (event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
                val eventText = event.text.toString()
                Log.d("USSDService", "Event Text: $eventText")

                // Check if the current window content contains USSD code
                val ussdCode = extractUSSDCode(event.text)
                Log.d("USSDService", "Extracted USSD Code: $ussdCode")

                if (ussdCode.isNotEmpty()) {
                    Log.d("USSDService", "USSD Code Found")

                    // Process the USSD code as needed (e.g., send a response or take further action)
                    // Note: Be cautious about handling sensitive information and user privacy

                    // Automatically select an option (for example, option 1)
                    selectUSSDOption("1")
                } else {
                    Log.d("USSDService", "USSD is not found")
                }
            }

        }
    }

    private fun extractUSSDCode(text: List<CharSequence>?): String {
        // Implement logic to extract the USSD code from the text content
        // For simplicity, let's assume the entire text is the USSD code
        return text?.joinToString(separator = "") ?: ""
    }

    private fun selectUSSDOption(option: String) {
        val rootNode = rootInActiveWindow
        Log.d("USSDService", "Triggered")

        // Implement logic to find and click the UI element corresponding to the selected option
        // This may involve traversing the accessibility node tree and simulating clicks

        // For example, let's assume the USSD menu has a set of buttons with text labels
        rootNode?.let { findAndClickButton(it, option) }
    }

    private fun findAndClickButton(rootNode: AccessibilityNodeInfo, option: String) {
        // Implement logic to find and click the button with the specified text
        // This may involve recursively traversing the accessibility node tree
        for (i in 0 until rootNode.childCount) {
            val childNode = rootNode.getChild(i)
            if (childNode != null && childNode.text != null && childNode.text.toString().contains(option)) {
                // Found the button with the specified text, perform a click
                childNode.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                break
            }

            // Recursively search in child nodes
            findAndClickButton(childNode, option)
        }
    }

    override fun onInterrupt() {
        // Handle service interruption (if necessary)
    }
}