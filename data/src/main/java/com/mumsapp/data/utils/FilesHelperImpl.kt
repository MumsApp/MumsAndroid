package com.mumsapp.data.utils

import android.os.Environment
import com.mumsapp.domain.utils.FilesHelper
import java.io.File
import javax.inject.Inject

class FilesHelperImpl : FilesHelper {

    @Inject
    constructor()

    override fun createTemporaryFile(name: String, ext: String): File {
        var tmpDir = Environment.getExternalStorageDirectory()
        tmpDir = File(tmpDir.absolutePath + "/.temp/")
        if(!tmpDir.exists()) {
            tmpDir.mkdirs()
        }

        return File.createTempFile(name, ext, tmpDir)
    }
}