package com.phngsapplication.app.ui

private const val LIB = "com.phngsapplication"

object CameraConfiguration {

    /** Result key for the captured image URI */
    const val IMAGE_URI = "$LIB.IMAGE_URI"

    /** User configuration key for viewfinder overlay resource ID */
    const val VIEW_FINDER_OVERLAY = "$LIB.VIEW_FINDER_OVERLAY"

    /** User configuration key for default camera lens facing */
    const val CAMERA_LENS_FACING = "$LIB.CAMERA_LENS_FACING"

    /** User configuration key for camera switching behavior */
    const val FULL_SCREEN_ENABLED = "$LIB.FULL_SCREEN_ENABLED"

    /** User configuration key for camera switching behavior */
    const val CAMERA_SWITCH_DISABLED = "$LIB.CAMERA_SWITCH_DISABLED"
    // New key for loading image from gallery
    const var GALLERY_IMAGE_URI = "$LIB.GALLERY_IMAGE_URI"
}
