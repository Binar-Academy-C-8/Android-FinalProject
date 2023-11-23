package com.raveendra.finalproject_binar.data.dummy

import com.raveendra.finalproject_binar.model.Notification

interface DummyNotificationDataSource {

    fun getNotificationData(): List<Notification>
}

class DummyNotificationdataSourceImpl() : DummyNotificationDataSource {
    override fun getNotificationData(): List<Notification> {
        return mutableListOf(
            Notification(
                image = "https://raw.githubusercontent.com/Bahrulilmi30/appFoodYL/master/app/src/main/res/drawable/ic_banner.png",
                label = "Promo",
                date = "2 Maret, 12:00",
                elipse = "https://github.com/Bahrulilmi30/appFoodYL/blob/master/app/src/main/res/drawable/ic_elllipse_green.xml",
                text = "Dapatkan Potongan 50% selama Bulan Maret!",
                description = "Syarat dan Ketentuan berlaku!"

            ),

            Notification(
                image = "https://github.com/Bahrulilmi30/appFoodYL/blob/master/app/src/main/res/drawable/ic_notification.xml",
                label = "Notifikasi",
                date = "1 Maret, 10:00",
                elipse = "https://github.com/Bahrulilmi30/appFoodYL/blob/master/app/src/main/res/drawable/ic_elllipse_red.xml",
                text = "Password Berhasil diubah!",
                description = "",
            ),

            Notification(
                image = "https://github.com/Bahrulilmi30/appFoodYL/blob/master/app/src/main/res/drawable/ic_notification.xml",
                label = "Notifikasi",
                date = "15 Maret, 19:00",
                elipse = "https://github.com/Bahrulilmi30/appFoodYL/blob/master/app/src/main/res/drawable/ic_elllipse_green.xml",
                text = "Rasakan senasinya mendapatkan potongan 10% pertamamu dan nikmati belajarmu!",
                description = "Syarat dan Ketentuan berlaku!"
            )
        )
    }
}