package com.raveendra.finalproject_binar.data.response.detaildata.detaildatanew


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.raveendra.finalproject_binar.domain.ChapterDomain
import com.raveendra.finalproject_binar.domain.ContentDomain
import com.raveendra.finalproject_binar.domain.DetailCourseDomain
import com.raveendra.finalproject_binar.domain.DetailResponseCourseDomain

@Keep
data class CourseApiResponseNew(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("status")
    val status: String
)

fun CourseApiResponseNew.toDomain() = DetailResponseCourseDomain(
    status = this.status,
    data = this.data.toDomain()
)
fun Data.toDomain() = DetailCourseDomain(
    id = this.id,
    courseCode = this.courseCode,
    categoryId = this.categoryId,
    userId = this.userId,
    courseName = this.courseName,
    image = this.image,
    courseType = this.courseType,
    courseLevel = this.courseLevel,
    aboutCourse = this.aboutCourse,
    intendedFor = this.intendedFor,
    coursePrice = this.coursePrice,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    category = this.category,
    courseBy = this.courseBy,
    rating = this.rating,
    durationPerCourseInMinutes = this.durationPerCourseInMinutes,
    modulePerCourse = this.modulePerCourse,
    chapters = this.chapters.map { it.toDomain() }
)
fun Collection<Data>.courseDataToDomain() = this.map {
    it.toDomain()
}
fun Chapter.toDomain() = ChapterDomain(
    id = this.id,
    chapterTitle = this.chapterTitle,
    courseId = this.courseId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    contents = this.contents.map { it.toDomain() },
    durationPerChapterInMinutes = this.durationPerChapterInMinutes
)

fun Content.toDomain()= ContentDomain(
    id = this.id,
    contentTitle = this.contentTitle,
    contentUrl = this.contentUrl,
    duration = this.duration,
    status = this.status,
    chapterId = this.chapterId,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt,
    youtubeId = this.youtubeId
)
fun Collection<Chapter>.chapterToDomain() = this.map { it.toDomain() }

fun Collection<Content>.contentToDomain() = this.map { it.toDomain()}

