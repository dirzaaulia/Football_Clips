package com.dirzaaulia.footballclips.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dirzaaulia.footballclips.data.Clips
import com.dirzaaulia.footballclips.repository.Repository
import com.dirzaaulia.footballclips.util.ResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val repository: Repository
  ): ViewModel() {

  private val _clips: MutableStateFlow<ResponseResult<List<Clips>?>> =
    MutableStateFlow(ResponseResult.Success(null))
  val clips = _clips.asStateFlow()

  init {
    getClips()
  }

  fun getClips()  {
    repository.getClips()
      .onEach {
        _clips.value = it
      }
      .launchIn(viewModelScope)
  }

}