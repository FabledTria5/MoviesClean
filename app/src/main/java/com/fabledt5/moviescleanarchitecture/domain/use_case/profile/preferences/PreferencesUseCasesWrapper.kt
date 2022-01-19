package com.fabledt5.moviescleanarchitecture.domain.use_case.profile.preferences

import javax.inject.Inject

data class PreferencesUseCasesWrapper @Inject constructor (
    val readListsShape: ReadListsShape,
    val updateListsShape: UpdateListsShape,
    val readSortState: ReadSortState,
    val updateSortState: UpdateSortState
)