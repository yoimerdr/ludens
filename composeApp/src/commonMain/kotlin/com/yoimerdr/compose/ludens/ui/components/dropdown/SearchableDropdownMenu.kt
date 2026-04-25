package com.yoimerdr.compose.ludens.ui.components.dropdown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yoimerdr.compose.ludens.ui.components.layout.BlockIntrinsicsMeasurePolicy
import com.yoimerdr.compose.ludens.ui.components.provider.LocalSpacing
import ludens.composeapp.generated.resources.Res
import ludens.composeapp.generated.resources.no_results
import ludens.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource


/**
 * A searchable dropdown menu anchor with built-in search functionality.
 *
 * This component provides an ExposedDropdownMenuBox with a search field that filters
 * the displayed items based on the search query. It supports custom item rendering,
 * item enabling/disabling, and empty state handling.
 *
 * @param items The set of items to display in the dropdown.
 * @param selectedItem The currently selected item, or null if none.
 * @param onItemSelected Callback invoked when an item is selected.
 * @param modifier Modifier for the dropdown box.
 * @param menuModifier Modifier for the dropdown menu panel.
 * @param contentModifier Modifier for the content area.
 * @param enabled Whether the dropdown is enabled.
 * @param searchEnabled Whether the search field is visible.
 * @param maxListHeight Maximum height of the dropdown list.
 * @param disableSelectedItem Whether to disable the selected item in the list.
 * @param searchPlaceholder Placeholder text for the search field.
 * @param itemKey Key function for item identity in LazyColumn.
 * @param itemEnabled Function to determine if an item is enabled.
 * @param itemSearchText Function to extract searchable text from an item.
 * @param itemContent Composable for rendering each item.
 * @param emptyContent Composable for empty state when no items match.
 * @param anchor Anchor content lambda receiving expand state and expand callback.
 *
 * @see ExposedDropdownMenuBox
 * @see SearchableDropdownMenuPanel
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> SearchableDropdownMenuAnchor(
    items: Set<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    menuModifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    enabled: Boolean = true,
    searchEnabled: Boolean = true,
    maxListHeight: Dp = 280.0f.dp,
    disableSelectedItem: Boolean = true,
    searchPlaceholder: String? = null,
    itemKey: ((T) -> Any)? = null,
    itemEnabled: (T) -> Boolean = { true },
    itemSearchText: (T) -> String,
    itemContent: @Composable (item: T, selected: Boolean) -> Unit,
    emptyContent: @Composable (() -> Unit)? = null,
    anchor: @Composable BoxScope.(expanded: Boolean, onExpandRequest: (() -> Unit)?) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val onClose = {
        expanded = false
        searchQuery = ""
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (enabled) expanded = it
        },
        modifier = modifier
    ) {
        Box(modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)) {
            anchor(
                expanded,
                if (enabled) {
                    { expanded = true }
                } else null
            )
        }

        if (enabled) {
            SearchableDropdownMenuPanel(
                expanded = expanded,
                onDismissRequest = onClose,
                entries = items,
                selectedItem = selectedItem,
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                onItemSelected = {
                    onClose()
                    onItemSelected(it)
                },
                modifier = menuModifier,
                contentModifier = contentModifier,
                maxListHeight = maxListHeight,
                searchEnabled = searchEnabled,
                disableSelectedItem = disableSelectedItem,
                searchPlaceholder = searchPlaceholder,
                itemKey = itemKey,
                itemEnabled = itemEnabled,
                itemSearchText = itemSearchText,
                itemContent = itemContent,
                emptyContent = emptyContent,
            )
        }
    }
}

/**
 * A searchable dropdown menu panel with built-in search and filtering.
 *
 * This composable is intended to be used inside an ExposedDropdownMenuBox scope.
 * It displays a search field and a filtered list of items based on the search query.
 *
 * @param expanded Whether the menu is expanded.
 * @param onDismissRequest Callback invoked when the menu should dismiss.
 * @param entries The set of items to display in the dropdown.
 * @param selectedItem The currently selected item, or null if none.
 * @param searchQuery Current search query text.
 * @param onSearchQueryChange Callback invoked when search query changes.
 * @param onItemSelected Callback invoked when an item is selected.
 * @param modifier Modifier for the dropdown menu.
 * @param contentModifier Modifier for the content area.
 * @param maxListHeight Maximum height of the dropdown list.
 * @param searchEnabled Whether the search field is visible.
 * @param disableSelectedItem Whether to disable the selected item in the list.
 * @param searchPlaceholder Placeholder text for the search field.
 * @param itemKey Key function for item identity in LazyColumn.
 * @param itemEnabled Function to determine if an item is enabled.
 * @param itemSearchText Function to extract searchable text from an item.
 * @param itemContent Composable for rendering each item.
 * @param emptyContent Composable for empty state when no items match.
 *
 * @see ExposedDropdownMenuBox
 * @see SearchableDropdownMenuAnchor
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ExposedDropdownMenuBoxScope.SearchableDropdownMenuPanel(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    entries: Set<T>,
    selectedItem: T?,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onItemSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    maxListHeight: Dp = 280.0f.dp,
    searchEnabled: Boolean = true,
    disableSelectedItem: Boolean = true,
    searchPlaceholder: String? = null,
    itemKey: ((T) -> Any)? = null,
    itemEnabled: (T) -> Boolean = { true },
    itemSearchText: (T) -> String,
    itemContent: @Composable (item: T, selected: Boolean) -> Unit,
    emptyContent: @Composable (() -> Unit)? = null,
) {
    val spacing = LocalSpacing.current
    val allItems = remember(entries) { entries.toList() }
    val filteredItems by remember(allItems, searchQuery) {
        derivedStateOf {
            val query = searchQuery.trim()
            if (query.isBlank()) {
                allItems
            } else {
                allItems.filter {
                    itemSearchText(it).contains(query, ignoreCase = true)
                }
            }
        }
    }

    ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier
            .widthIn(min = 240f.dp, max = 320f.dp)
            .height(IntrinsicSize.Max),
    ) {
        Column(
            modifier = Modifier
                .height(maxListHeight)
                .padding(spacing.small)
                .then(contentModifier),
            verticalArrangement = Arrangement.spacedBy(spacing.small)
        ) {
            if (searchEnabled) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    placeholder = {
                        Text(searchPlaceholder ?: stringResource(Res.string.search))
                    }
                )
            }

            Layout(
                content = {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        userScrollEnabled = true
                    ) {
                        if (filteredItems.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier.fillParentMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    emptyContent?.invoke() ?: Text(
                                        text = stringResource(Res.string.no_results),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        } else {
                            items(
                                items = filteredItems,
                                key = itemKey ?: { it.hashCode() }
                            ) { item ->
                                val selected = selectedItem == item
                                DropdownMenuItem(
                                    text = { itemContent(item, selected) },
                                    onClick = { onItemSelected(item) },
                                    enabled = itemEnabled(item) && (!disableSelectedItem || !selected),
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                measurePolicy = BlockIntrinsicsMeasurePolicy
            )
        }
    }
}