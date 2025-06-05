package com.pyanov.liveanimation.designSystem

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

const val PADDING_TINY = 4
const val PADDING_SMALL = 8
const val PADDING_MEDIUM = 16
const val PADDING_ML = PADDING_MEDIUM + 2
const val PADDING_LARGE = 24

const val DEFAULT_ELEVATION = 4
const val DEFAULT_CORNER_RADIUS = 20

@Composable
fun getDefaultShape() = RoundedCornerShape(DEFAULT_CORNER_RADIUS.dp)

@Composable
fun SpacerSmall() = Spacer(Modifier.size(PADDING_SMALL.dp))

@Composable
fun SpacerMedium() = Spacer(Modifier.size(PADDING_MEDIUM.dp))

@Composable
fun SpacerLarge() = Spacer(Modifier.size(PADDING_LARGE.dp))
