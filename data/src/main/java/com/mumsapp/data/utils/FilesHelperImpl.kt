package com.mumsapp.data.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.mumsapp.android.di.qualifiers.ApplicationId
import com.mumsapp.domain.utils.FilesHelper
import java.io.File
import javax.inject.Inject

class FilesHelperImpl : FilesHelper {

    private val appContext: Context
    private val applicationId: String

    @Inject
    constructor(appContext: Context, @ApplicationId applicationId: String) {
        this.appContext = appContext
        this.applicationId = applicationId
    }

    override fun createTemporaryFile(name: String, extWithDot: String): File {
        val tmpDir = Environment.getExternalStorageDirectory()
        if(!tmpDir.exists()) {
            tmpDir.mkdirs()
        }

        return File.createTempFile(name, extWithDot, tmpDir)
    }

    override fun getExportedUri(file: File): String {
        val uri = FileProvider.getUriForFile(appContext, "$applicationId.provider", file)
        return uri.toString()
    }

    override fun getFileFromGalleryUri(uriString: String): File {
        val uri = Uri.parse(uriString)
        val resolver = appContext.contentResolver
        val projection = arrayOf(MediaStore.Images.Media.DATA)

        val cursor = resolver.query(uri, projection, null, null, null)
        val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val path = cursor.getString(columnIndex)
        cursor.close()

        return File(path)
    }
}