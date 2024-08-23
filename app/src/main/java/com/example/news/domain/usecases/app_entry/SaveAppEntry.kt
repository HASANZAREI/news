package com.example.news.domain.usecases.app_entry

import com.example.news.domain.manger.LocalUserManger

class SaveAppEntry(
    private val localUserManger: LocalUserManger
) {
    suspend operator fun invoke() {
        localUserManger.saveAppEntry()
    }

}