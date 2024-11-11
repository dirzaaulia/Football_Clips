package com.dirzaaulia.footballclips.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(

): ViewModel() {

    var currentCompetition = ""
}