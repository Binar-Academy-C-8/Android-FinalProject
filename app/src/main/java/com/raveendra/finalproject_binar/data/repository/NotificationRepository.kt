package com.raveendra.finalproject_binar.data.repository

import com.raveendra.finalproject_binar.data.dummy.DummyNotificationDataSource
import com.raveendra.finalproject_binar.model.Notification

interface NotificationRepository {
    fun getNotification(): List<Notification>
}

class CourseRepositoryImpl(
    private val notificationDataSource: DummyNotificationDataSource
) : NotificationRepository {
    override fun getNotification(): List<Notification> {
        return notificationDataSource.getNotificationData()
    }

}