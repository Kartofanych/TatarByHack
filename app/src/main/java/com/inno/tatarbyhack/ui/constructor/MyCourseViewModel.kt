package com.inno.tatarbyhack.ui.constructor

import android.content.Context
import android.net.Uri
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
    fun updateModulesPosition(i1:Int, i2:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCourseModules(id, i1, i2)
        }
    }
    fun addCourseModules(newModule: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCourseModules(id, newModule)
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