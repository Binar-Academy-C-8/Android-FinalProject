package com.raveendra.finalproject_binar.data.dummy

import com.raveendra.finalproject_binar.model.Notification

interface DummyNotificationDataSource {

    fun getNotificationData(): List<Notification>
}

class DummyNotificationdataSourceImpl() : DummyNotificationDataSource {
    override fun getNotificationData(): List<Notification> {
        return mutableListOf(
            Notification(
                label = "Promo",
                date = "2 Maret, 12:00",
                text = "Dapatkan Potongan 50% selama Bulan Maret!",
                description = "Syarat dan Ketentuan berlaku!"

            ),

            Notification(
                label = "Notifikasi",
                date = "1 Maret, 10:00",
                text = "Password Berhasil diubah!",
                description = "",
            ),

            Notification(
                label = "Notifikasi",
                date = "15 Maret, 19:00",
                text = "Rasakan senasinya mendapatkan potongan 10% pertamamu dan nikmati belajarmu!",
                description = "Syarat dan Ketentuan berlaku!"
            )
        )
    }
}