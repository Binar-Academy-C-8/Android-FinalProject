package com.raveendra.finalproject_binar.domain

/**
 *hrahm,12/12/2023, 18:03
 **/

data class DetailResponseCourseDomain(
    val status: String,
    val data: DetailCourseDomain?,
)

data class DetailCourseDomain(
    val aboutCourse: String,
    val category: String,
    val categoryId: Int,
    val chapters: List<ChapterDomain?>,
    val courseBy: String,
    val courseCode: String,
    val courseLevel: String,
    val courseName: String,
    val coursePrice: Int,
    val rawPrice: Int,
    val courseDiscountInPercent: Int,
    val courseType: String,
    val createdAt: String,
    val durationPerCourseInMinutes: Int,
    val id: Int,
    val image: String,
    val intendedFor: String,
    val modulePerCourse: Int,
    val rating: Double,
    val updatedAt: String,
    val userId: Int
)

data class ChapterDomain(
    val chapterTitle: String,
    val contents: List<ContentDomain?>,
    val courseId: Int,
    val createdAt: String,
    val durationPerChapterInMinutes: Int,
    val id: Int,
    val updatedAt: String
)

data class ContentDomain(
    val chapterId: Int,
    val contentTitle: String,
    val contentUrl: String,
    val createdAt: String,
    val duration: String,
    val id: Int,
    val status: Boolean,
    val updatedAt: String,
    val youtubeId: String
)
