package ie.por.thirdplace.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import ie.por.thirdplace.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.hint_selectImage.toString())
    intentLauncher.launch(chooseFile)
}