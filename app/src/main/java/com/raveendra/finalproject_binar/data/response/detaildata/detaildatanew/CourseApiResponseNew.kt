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
    val `data`: DetailCourseDataResponse? = null,
    @SerializedName("status")
    val status: String? = null
)
@Keep
data class DetailCourseDataResponse(
    @SerializedName("aboutCourse")
    val aboutCourse: String? = null,
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("categoryId")
    val categoryId: Int? = null,
    @SerializedName("chapters")
    val chapters: List<ChapterResponse>,
    @SerializedName("courseBy")
    val courseBy: String? = null,
    @SerializedName("courseCode")
    val courseCode: String? = null,
    @SerializedName("courseLevel")
    val courseLevel: String? = null,
    @SerializedName("courseName")
    val courseName: String? = null,
    @SerializedName("coursePrice")
    val coursePrice: Int? = null,
    @SerializedName("courseDiscountInPercent")
    val courseDiscountInPercent: Int? = null,
    @SerializedName("rawPrice")
    val rawPrice: Int? = null,
    @SerializedName("courseType")
    val courseType: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("durationPerCourseInMinutes")
    val durationPerCourseInMinutes: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("image")
    val image: String? = null,
    @SerializedName("intendedFor")
    val intendedFor: String? = null,
    @SerializedName("modulePerCourse")
    val modulePerCourse: Int? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("userId")
    val userId: Int? = null
)
@Keep
data class ChapterResponse(
    @SerializedName("chapterTitle")
    val chapterTitle: String? = null,
    @SerializedName("contents")
    val contents: List<ContentResponse?>,
    @SerializedName("courseId")
    val courseId: Int? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("durationPerChapterInMinutes")
    val durationPerChapterInMinutes: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null
)

@Keep
data class ContentResponse(
    @SerializedName("chapterId")
    val chapterId: Int? = null,
    @SerializedName("contentTitle")
    val contentTitle: String? = null,
    @SerializedName("contentUrl")
    val contentUrl: String? = null,
    @SerializedName("createdAt")
    val createdAt: String? = null,
    @SerializedName("duration")
    val duration: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("status")
    val status: Boolean? = null,
    @SerializedName("updatedAt")
    val updatedAt: String? = null,
    @SerializedName("isLocked")
    val isLocked: Boolean? = null,
    @SerializedName("isWatched")
    val isWatched: Boolean? = null,
    @SerializedName("youtubeId")
    val youtubeId: String? = null
)

fun CourseApiResponseNew.toDomain() = DetailResponseCourseDomain(
    status = this.status.orEmpty(),
    data = this.data?.toDomain()
)

fun DetailCourseDataResponse.toDomain() = DetailCourseDomain(
    id = this.id ?: 0,
    courseCode = this.courseCode.orEmpty(),
    categoryId = this.categoryId?: 0,
    userId = this.userId?: 0,
    courseName = this.courseName.orEmpty(),
    image = this.image.orEmpty(),
    courseType = this.courseType.orEmpty(),
    courseLevel = this.courseLevel.orEmpty(),
    aboutCourse = this.aboutCourse.orEmpty(),
    intendedFor = this.intendedFor.orEmpty(),
    coursePrice = this.coursePrice?: 0,
    createdAt = this.createdAt.orEmpty(),
    updatedAt = this.updatedAt.orEmpty(),
    category = this.category.orEmpty(),
    courseBy = this.courseBy.orEmpty(),
    rating = this.rating?: 0.0,
    durationPerCourseInMinutes = this.durationPerCourseInMinutes ?: 0,
    modulePerCourse = this.modulePerCourse?: 0,
    courseDiscountInPercent = this.courseDiscountInPercent?: 0,
    rawPrice = this.rawPrice?: 0,
    chapters = this.chapters.map { it.toDomain() }
)

fun ChapterResponse.toDomain() = ChapterDomain(
    id = this.id ?: 0,
    chapterTitle = this.chapterTitle.orEmpty(),
    courseId = this.courseId ?: 0,
    createdAt = this.createdAt.orEmpty(),
    updatedAt = this.updatedAt.orEmpty(),
    contents = this.contents.map { it?.toDomain() },
    durationPerChapterInMinutes = this.durationPerChapterInMinutes ?: 0
)

fun ContentResponse.toDomain() = ContentDomain(
    id = this.id ?: 0,
    contentTitle = this.contentTitle.orEmpty(),
    contentUrl = this.contentUrl.orEmpty(),
    duration = this.duration.orEmpty(),
    status = this.status ?: false,
    chapterId = this.chapterId ?: 0,
    createdAt = this.createdAt.orEmpty(),
    updatedAt = this.updatedAt.orEmpty(),
    isLocked = this.isLocked ?: false,
    isWatched = this.isWatched ?: false,
    youtubeId = this.youtubeId.orEmpty()
)

