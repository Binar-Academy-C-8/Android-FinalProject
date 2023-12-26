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
    val `data`: Data? = null,
    @SerializedName("status")
    val status: String? = null
)

fun CourseApiResponseNew.toDomain() = DetailResponseCourseDomain(
    status = this.status.orEmpty(),
    data = this.data?.toDomain()
)

fun Data.toDomain() = DetailCourseDomain(
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

fun Collection<Data>.courseDataToDomain() = this.map {
    it.toDomain()
}

fun Chapter.toDomain() = ChapterDomain(
    id = this.id ?: 0,
    chapterTitle = this.chapterTitle.orEmpty(),
    courseId = this.courseId ?: 0,
    createdAt = this.createdAt.orEmpty(),
    updatedAt = this.updatedAt.orEmpty(),
    contents = this.contents.map { it?.toDomain() },
    durationPerChapterInMinutes = this.durationPerChapterInMinutes ?: 0
)

fun Content.toDomain() = ContentDomain(
    id = this.id ?: 0,
    contentTitle = this.contentTitle.orEmpty(),
    contentUrl = this.contentUrl.orEmpty(),
    duration = this.duration.orEmpty(),
    status = this.status ?: false,
    chapterId = this.chapterId ?: 0,
    createdAt = this.createdAt.orEmpty(),
    updatedAt = this.updatedAt.orEmpty(),
    youtubeId = this.youtubeId.orEmpty()
)

fun Collection<Chapter>.chapterToDomain() = this.map { it.toDomain() }

fun Collection<Content>.contentToDomain() = this.map { it.toDomain() }

