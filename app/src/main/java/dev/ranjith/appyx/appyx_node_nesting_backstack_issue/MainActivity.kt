package dev.ranjith.appyx.appyx_node_nesting_backstack_issue

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import dev.ranjith.appyx.appyx_node_nesting_backstack_issue.root.RootNode
import dev.ranjith.appyx.appyx_node_nesting_backstack_issue.ui.theme.AppyxNodeNestingBackstackIssueTheme

class MainActivity : NodeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppyxNodeNestingBackstackIssueTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Column {
                        NodeHost(integrationPoint = integrationPoint) {
                            RootNode(buildContext = it)
                        }
                    }
                }
            }
        }
    }
}