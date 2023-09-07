package com.inno.tatarbyhack.ui.constructor

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.repository.MyCoursesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID


class MyCourseViewModel(
    private val repository: MyCoursesRepository,
    private val id: String,
    private val context: Context
) : ViewModel() {

    private val myCourseFlow = MutableStateFlow(Course())
    val myCourse = myCourseFlow.asStateFlow()

    private val modulesFlow =
        MutableStateFlow(
            listOf(
                "1. Модуль 1",
                "2. Модуль 2",
                "3. Модуль 3",
                "4. Модуль 4",
                "5. Модуль 5",
                "6. Модуль 6",
                "7. Модуль 7",
                "8. Модуль 8"
            )
        )
    val modules = modulesFlow.asStateFlow()

    fun onChange(i1:Int, i2:Int){
        val cur = modules.value.toMutableList()
        val temp = cur[i1]
        cur[i1] = cur[i2]
        cur[i2] = temp
        viewModelScope.launch(Dispatchers.IO) {
            modulesFlow.emit(cur)
        }
    }

    init {
        getCourse()
    }

    private fun getCourse() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCourse(id).collect {
                myCourseFlow.emit(it)
            }
        }
    }

    fun deleteCourse() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCourse(id)
        }
    }

    fun updateCourseImage(uri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newPath = saveImageToCache(uri)
            repository.changeCourseImage(newPath, id)
        }
    }

    fun updateCourseDescription(desc: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCourseDesc(id, desc)
        }
    }


    private suspend fun saveImageToCache(uri: String): String {
        val destFilePath = "${context.getExternalFilesDir("photos")}/${UUID.randomUUID()}.jpg"
        val `in`: InputStream = context.contentResolver.openInputStream(Uri.parse(uri))!!
        val outFile: File = File(destFilePath)
        val out: OutputStream = withContext(Dispatchers.IO) {
            FileOutputStream(outFile)
        }
        copyFile(`in`, out)
        return destFilePath
    }

    @Throws(IOException::class)
    private fun copyFile(`in`: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (`in`.read(buffer).also { read = it } != -1) {
            out.write(buffer, 0, read)
        }
    }

}