package com.vmloft.develop.app.template.ui.main

import androidx.lifecycle.viewModelScope

import com.vmloft.develop.app.template.common.SignManager
import com.vmloft.develop.app.template.request.bean.User
import com.vmloft.develop.app.template.request.repository.CommonRepository
import com.vmloft.develop.app.template.request.repository.MainRepository
import com.vmloft.develop.library.common.base.BViewModel
import com.vmloft.develop.library.common.request.RResult

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Create by lzan13 on 2020/4/20 17:28
 * 描述：首页 ViewModel
 */
class MainViewModel(val repository: MainRepository, val commonRepository: CommonRepository) : BViewModel() {

    /**
     * 加载当前用户数据
     */
    fun getCurrUser() {
        viewModelScope.launch(Dispatchers.Main) {
            emitUIState(true)
            val result = repository.loadCurrUser()

            if (result is RResult.Success) {
                SignManager.setCurrUser(result.data as User)

                emitUIState(isSuccess = true, data = result.data, type = "userInfo")
                return@launch
            } else if (result is RResult.Error) {
                emitUIState(code = result.code, error = result.error)
            }
        }
    }

    /**
     * 检查版本
     */
    fun checkVersion() {
        viewModelScope.launch(Dispatchers.Main) {
            emitUIState(true)
            val result = commonRepository.checkVersion()
            if (result is RResult.Success) {
                emitUIState(isSuccess = true, data = result.data, type = "checkVersion")
                return@launch
            } else if (result is RResult.Error) {
                emitUIState(code = result.code, error = result.error)
            }
        }
    }
}