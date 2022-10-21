package dev.ranjith.appyx.appyx_node_nesting_backstack_issue.subtrees

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.core.node.node
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.push
import dev.ranjith.appyx.appyx_node_nesting_backstack_issue.common.Screen
import dev.ranjith.appyx.appyx_node_nesting_backstack_issue.common.ScreenWithRedirection
import kotlinx.parcelize.Parcelize

class TreeDNode(
    buildContext: BuildContext,
    val backStack: BackStack<Routing> = BackStack(Routing.Leaf, buildContext.savedStateMap)
): ParentNode<TreeDNode.Routing>(
    buildContext = buildContext,
    navModel = backStack
) {

    sealed class Routing: Parcelable {
        @Parcelize
        object Leaf: Routing()
        @Parcelize
        object NextTree: Routing()
    }

    override fun resolve(navTarget: Routing, buildContext: BuildContext): Node {
        return when(navTarget) {
            Routing.Leaf -> node(buildContext) { modifier ->
                ScreenWithRedirection(
                    title = "Tree D Leaf 1",
                    redirectTo = "Tree D Leaf 2",
                    onRedirect = { backStack.push(Routing.NextTree) },
                    modifier = modifier
                )
            }
            Routing.NextTree -> node(buildContext) { modifier ->
                Screen(
                    title = "Tree D Leaf 2",
                    modifier = modifier
                )
            }
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }
}