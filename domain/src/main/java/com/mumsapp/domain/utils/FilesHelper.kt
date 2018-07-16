package com.mumsapp.domain.utils

import java.io.File

interface FilesHelper {

    fun createTemporaryFile(name: String, extWithDot: String): File

    fun getExportedUri(file: File): String

    fun getFileFromGalleryUri(path: String): File
}