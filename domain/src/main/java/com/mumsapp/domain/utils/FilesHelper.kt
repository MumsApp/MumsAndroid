package com.mumsapp.domain.utils

import java.io.File

interface FilesHelper {

    fun createTemporaryFile(name: String, ext: String): File
}