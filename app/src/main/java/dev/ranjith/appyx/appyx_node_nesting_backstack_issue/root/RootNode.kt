package dev.ranjith.appyx.appyx_node_nesting_backstack_issue.root

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
import dev.ranjith.appyx.appyx_node_nesting_backstack_issue.subtrees.TreeANode
import kotlinx.parcelize.Parcelize

class RootNode(
    buildContext: BuildContext,
    val backstack: BackStack<Routing> = BackStack(
        initialElement = Routing.RootLeaf1,
        savedStateMap = buildContext.savedStateMap,
    )
) : ParentNode<RootNode.Routing>(
    buildContext = buildContext,
    navModel = backstack
) {

    sealed class Routing: Parcelable {

        @Parcelize
        object RootLeaf1 : Routing()
        @Parcelize
        object RootLeaf2 : Routing()
        @Parcelize
        object NextTree : Routing()
    }

    override fun resolve(navTarget: Routing, buildContext: BuildContext): Node {
        return when (navTarget) {
            Routing.RootLeaf1 -> node(buildContext) { modifier ->
                ScreenWithRedirection(
                    title = "Root leaf 1",
                    modifier = modifier,
                    redirectTo = "Root leaf 2",
                    onRedirect = {
                        backstack.push(Routing.RootLeaf2)
                    }
                )
            }

            Routing.RootLeaf2 -> node(buildContext) { modifier ->
                ScreenWithRedirection(
                    title = "Root leaf 2",
                    modifier = modifier,
                    redirectTo = "Tree A",
                    onRedirect = {
                        backstack.push(Routing.NextTree)
                    }
                )
            }

            Routing.NextTree -> TreeANode(buildContext)
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Children(navModel = backstack)
    }
}