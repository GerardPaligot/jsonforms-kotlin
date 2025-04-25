package com.paligot.jsonforms.ui

import com.paligot.jsonforms.kotlin.models.uischema.GroupLayout
import com.paligot.jsonforms.kotlin.models.uischema.HorizontalLayout
import com.paligot.jsonforms.kotlin.models.uischema.LayoutOptions
import com.paligot.jsonforms.kotlin.models.uischema.VerticalLayout
import kotlinx.collections.immutable.persistentListOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RendererLayoutScopeTest {
    @Test
    fun `isVerticalLayout() returns true for VerticalLayout`() {
        val uiSchema = VerticalLayout()
        val scope: RendererLayoutScope = RendererLayoutScopeInstance(uiSchema)

        assertTrue(scope.isVerticalLayout())
        assertFalse(scope.isHorizontalLayout())
        assertFalse(scope.isGroupLayout())
    }

    @Test
    fun `isHorizontalLayout() returns true for HorizontalLayout`() {
        val uiSchema = HorizontalLayout()
        val scope: RendererLayoutScope = RendererLayoutScopeInstance(uiSchema)

        assertTrue(scope.isHorizontalLayout())
        assertFalse(scope.isVerticalLayout())
        assertFalse(scope.isGroupLayout())
    }

    @Test
    fun `isGroupLayout() returns true for GroupLayout`() {
        val uiSchema = GroupLayout(label = "Group Label")
        val scope: RendererLayoutScope = RendererLayoutScopeInstance(uiSchema)

        assertTrue(scope.isGroupLayout())
        assertFalse(scope.isVerticalLayout())
        assertFalse(scope.isHorizontalLayout())
    }

    @Test
    fun `title() returns label for GroupLayout`() {
        val uiSchema = GroupLayout(label = "Group Label")
        val scope: RendererLayoutScope = RendererLayoutScopeInstance(uiSchema)

        assertEquals("Group Label", scope.title())
    }

    @Test
    fun `description() returns description for GroupLayout`() {
        val uiSchema = GroupLayout(label = "Group Label", description = "Group Description")
        val scope: RendererLayoutScope = RendererLayoutScopeInstance(uiSchema)

        assertEquals("Group Description", scope.description())
    }

    @Test
    fun `elements() returns elements of UiSchema`() {
        val childElement = VerticalLayout()
        val uiSchema = GroupLayout(label = "Group Label", elements = persistentListOf(childElement))
        val scope: RendererLayoutScope = RendererLayoutScopeInstance(uiSchema)

        assertEquals(listOf(childElement), scope.elements())
    }

    @Test
    fun `verticalSpacing() returns correct spacing`() {
        val options = LayoutOptions(verticalSpacing = "10dp")
        val uiSchema = VerticalLayout(options = options)
        val scope: RendererLayoutScope = RendererLayoutScopeInstance(uiSchema)

        assertEquals("10dp", scope.verticalSpacing())
    }

    @Test
    fun `horizontalSpacing() returns correct spacing`() {
        val options = LayoutOptions(horizontalSpacing = "15dp")
        val uiSchema = HorizontalLayout(options = options)
        val scope: RendererLayoutScope = RendererLayoutScopeInstance(uiSchema)

        assertEquals("15dp", scope.horizontalSpacing())
    }
}
