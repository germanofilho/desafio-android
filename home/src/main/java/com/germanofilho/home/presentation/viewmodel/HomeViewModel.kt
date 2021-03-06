package com.germanofilho.home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.germanofilho.core.helper.Resource
import com.germanofilho.core.viewmodel.BaseViewModel
import com.germanofilho.home.repository.HomeRepository
import com.germanofilho.network.feature.repositorylist.model.entity.GitRepositoryResponse
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel() {

    var count = 1
    var releaseLoad: Boolean = true

    val repositoryList = MutableLiveData<Resource<GitRepositoryResponse>>()

    fun getRepositoryList(page: Int = 1) {
        viewModelScope.launch {
            if(page == 1) repositoryList.loading(true)
            try {
                repositoryList.success(repository.getRepositoryList(page))
                releaseLoad = true
            } catch (e: Exception) {
                repositoryList.error(e)
            } finally {
                repositoryList.loading(false)
            }
        }
    }

    fun getNextRepositoryList(){
        getRepositoryList(++count)
        releaseLoad = false
    }
}