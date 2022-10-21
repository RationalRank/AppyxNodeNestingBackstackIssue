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
import dev.ranjith.appyx.appyx_node_nesting_backstack_issue.common.ScreenWithRedirection
import kotlinx.parcelize.Parcelize

class TreeBNode(
    buildContext: BuildContext,
    val backStack: BackStack<Routing> = BackStack(Routing.Leaf1, buildContext.savedStateMap)
): ParentNode<TreeBNode.Routing>(
    buildContext = buildContext,
    navModel = backStack
) {

    sealed class Routing: Parcelable {
        @Parcelize
        object Leaf1: Routing()
        @Parcelize
        object Leaf2: Routing()
        @Parcelize
        object NextTree: Routing()
    }

    override fun resolve(navTarget: Routing, buildContext: BuildContext): Node {
        return when(navTarget) {
            Routing.Leaf1 -> node(buildContext) { modifier ->
                ScreenWithRedirection(
                    title = "Tree B Leaf 1",
                    redirectTo = "Tree B Leaf 2",
                    onRedirect = { backStack.push(Routing.Leaf2) },
                    modifier = modifier
                )
            }
            Routing.Leaf2 -> node(buildContext) { modifier ->
                ScreenWithRedirection(
                    title = "Tree B Leaf 2",
                    redirectTo = "Tree C",
                    onRedirect = { backStack.push(Routing.NextTree) },
                    modifier = modifier
                )
            }
            Routing.NextTree -> TreeCNode(buildContext)
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backStack)
    }
}