package com.inno.tatarbyhack.data

import com.inno.tatarbyhack.domain.models.Course
import com.inno.tatarbyhack.domain.repository.Repository

class RepositoryImpl : Repository {

    private var allCourses = listOf(
        Course(
            1,
            "C++",
            "https://w7.pngwing.com/pngs/646/751/png-transparent-the-c-programming-language-computer-programming-programmer-others-blue-class-logo.png",
            "С++ крутой язык программирования, этот курс тоже крутой",
            "Галиев Ирек",
            arrayListOf()
        ),
        Course(
            2,
            "Python",
            "https://w7.pngwing.com/pngs/735/881/png-transparent-python-computer-icons-graphical-user-interface-python-stickers-angle-text-logo.png",
            "Python крутой язык программирования, этот курс тоже крутой",
            "Сальников Егор",
            arrayListOf()
        ),
        Course(
            3,
            "Kotlin",
            "https://download.logo.wine/logo/Kotlin_(programming_language)/Kotlin_(programming_language)-Logo.wine.png",
            "Kotlin крутой язык программирования, этот курс тоже крутой",
            "Насыбуллин Карим",
            arrayListOf()
        )
    )




    override fun getAllCourses(): List<Course> {
        return allCourses
    }


    override suspend fun getLocalPopular(): List<Course> {
        getPopularCourses()
        return allCourses
//                dao.getAllFlow().collect { list ->
//                    emit(UiState.Success(list.map { it.toItem() }))
//                }

    }

    override suspend fun getPopularCourses() {
        //network -> if success -> change DB
    }


    override suspend fun getLocalRecommended(): Array<List<Course>> {
        //request
        return arrayOf(
            listOf(
                Course(
                    1,
                    "C++",
                    "https://w7.pngwing.com/pngs/646/751/png-transparent-the-c-programming-language-computer-programming-programmer-others-blue-class-logo.png",
                    "С++ крутой язык программирования, этот курс тоже крутой",
                    "Галиев Ирек",
                    arrayListOf()
                ),
                Course(
                    2,
                    "Python",
                    "https://w7.pngwing.com/pngs/735/881/png-transparent-python-computer-icons-graphical-user-interface-python-stickers-angle-text-logo.png",
                    "Python крутой язык программирования, этот курс тоже крутой",
                    "Сальников Егор",
                    arrayListOf()
                ),
                Course(
                    3,
                    "Kotlin",
                    "https://download.logo.wine/logo/Kotlin_(programming_language)/Kotlin_(programming_language)-Logo.wine.png",
                    "Kotlin крутой язык программирования, этот курс тоже крутой",
                    "Насыбуллин Карим",
                    arrayListOf()
                )
            ),
            listOf(
                Course(
                    2,
                    "Python",
                    "https://w7.pngwing.com/pngs/735/881/png-transparent-python-computer-icons-graphical-user-interface-python-stickers-angle-text-logo.png",
                    "Python крутой язык программирования, этот курс тоже крутой",
                    "Сальников Егор",
                    arrayListOf()
                ),
                Course(
                    1,
                    "C++",
                    "https://w7.pngwing.com/pngs/646/751/png-transparent-the-c-programming-language-computer-programming-programmer-others-blue-class-logo.png",
                    "С++ крутой язык программирования, этот курс тоже крутой",
                    "Галиев Ирек",
                    arrayListOf()
                ),
                Course(
                    3,
                    "Kotlin",
                    "https://download.logo.wine/logo/Kotlin_(programming_language)/Kotlin_(programming_language)-Logo.wine.png",
                    "Kotlin крутой язык программирования, этот курс тоже крутой",
                    "Насыбуллин Карим",
                    arrayListOf()
                )
            ),
            listOf(
                Course(
                    3,
                    "Kotlin",
                    "https://download.logo.wine/logo/Kotlin_(programming_language)/Kotlin_(programming_language)-Logo.wine.png",
                    "Kotlin крутой язык программирования, этот курс тоже крутой",
                    "Насыбуллин Карим",
                    arrayListOf()
                ),
                Course(
                    2,
                    "Python",
                    "https://w7.pngwing.com/pngs/735/881/png-transparent-python-computer-icons-graphical-user-interface-python-stickers-angle-text-logo.png",
                    "Python крутой язык программирования, этот курс тоже крутой",
                    "Сальников Егор",
                    arrayListOf()
                ),
                Course(
                    1,
                    "C++",
                    "https://w7.pngwing.com/pngs/646/751/png-transparent-the-c-programming-language-computer-programming-programmer-others-blue-class-logo.png",
                    "С++ крутой язык программирования, этот курс тоже крутой",
                    "Галиев Ирек",
                    arrayListOf()
                ),
            )
        )
    }

    override suspend fun getRecommendedCourses() {
        //network -> if success -> change DB
    }
}