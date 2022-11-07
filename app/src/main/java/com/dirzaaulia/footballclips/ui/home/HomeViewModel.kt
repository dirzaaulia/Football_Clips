package com.dirzaaulia.footballclips.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dirzaaulia.footballclips.data.model.ClipState
import com.dirzaaulia.footballclips.repository.Repository
import com.dirzaaulia.footballclips.util.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _clips: MutableStateFlow<ResponseResult<List<ClipState>?>> =
        MutableStateFlow(ResponseResult.Success(null))
    val clips = _clips.asStateFlow()

    init {
        getClips()
    }

    fun getClips() {
        repository.getClips()
            .onEach { _clips.value = it }
            .launchIn(viewModelScope)
    }

}