package com.raveendra.finalproject_binar.data.local

import com.raveendra.finalproject_binar.data.network.api.service.dummydatavideos.ItemVideos
import com.raveendra.finalproject_binar.data.network.api.service.dummydatavideos.SectionedData

/**
 *hrahm,26/11/2023, 05:35
 **/
interface LocalDataSource {
    fun getVideos(): List<SectionedData>
    fun getVideoUrl(videoTitle:String):String?
}

class LocalDataSourceimpl() : LocalDataSource {
    override fun getVideos(): List<SectionedData> {
        return listOf(
            SectionedData(
                "Beginner", listOf(
                    ItemVideos("UI/UX", "t0aCoqXKFOU"),
                    ItemVideos("Web development", "kD0en6bbJPI"),
                )
            ),
            SectionedData(
                "Middle", listOf(
                    ItemVideos("Cyber Security", "W4A3obpPWRE")
                )
            ),
            SectionedData(
                "Expert", listOf(
                    ItemVideos("Back End", "mRttyh1GQ5I")
                )
            ),
            SectionedData(
                "CEO", listOf(
                    ItemVideos("Dev Ops", "H0WtJ8E-hqE")
                )
            ),
        )
    }

    override fun getVideoUrl(videoTitle: String): String? {
        val allVideos = getVideos().flatMap { it.data }
        return allVideos.find{it.titleVideos == videoTitle}?.urlVideos
    }


}