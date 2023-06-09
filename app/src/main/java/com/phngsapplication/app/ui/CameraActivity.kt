package com.phngsapplication.app.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.LifecycleOwner
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.ActivityCameraBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random



private const val IMMERSIVE_FLAG_TIMEOUT = 500L
class CameraActivity : AppCompatActivity(){

  private lateinit var binding: ActivityCameraBinding

  private lateinit var container: ConstraintLayout
  private lateinit var viewFinder: PreviewView

  private var preview: Preview? = null
  private var imageCapture: ImageCapture? = null
  private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

  private val cameraProvider by lazy {
      ProcessCameraProvider.getInstance(this)
  }

  private val executor by lazy {
      ContextCompat.getMainExecutor(this)
  }

  private val metadata by lazy {
    packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA).metaData
  }

  private val permissions by lazy {
    listOf(Manifest.permission.CAMERA)
  }

  private val permissionsRequestCode by lazy {
      Random.nextInt(0, 10000)
  }

  /**
   * Helper function used to retrieve a configuration value given its key. The priority order is:
   * 1. Intent extras bundle
   * 2. App manifest metadata
   */
  private fun getConfigurationValue(key: String): Any? = when {
    intent.extras?.containsKey(key) == true -> intent.extras?.get(key)
    metadata?.containsKey(key) == true -> metadata.get(key)
    else -> null
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_camera)

    setContentView(R.layout.activity_camera)
    container = findViewById(R.id.camera_container)
    viewFinder = findViewById(R.id.view_finder)

    // Try to provide a seamless rotation for devices that support it
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      window.attributes.rotationAnimation =
          WindowManager.LayoutParams.ROTATION_ANIMATION_SEAMLESS
    }

    // Operate on the viewfinder's thread to make sure it's ready
    viewFinder.post { drawCameraControls() }

  }


  /** Reads and applies all custom configuration provided by the user of this activity */
  private fun applyUserConfiguration() {

    // If the user requested a specific lens facing, select it
    getConfigurationValue(CameraConfiguration.CAMERA_LENS_FACING)?.let {
      lensFacing = it as Int
    }

    // If the user disabled camera switching, hide the button
    if (true == getConfigurationValue(CameraConfiguration.CAMERA_SWITCH_DISABLED)) {
      container.findViewById<ImageView>(R.id.camera_switch_button).visibility = View.GONE
    }

    // If the user requested full screen, set the appropriate flags
    if (true == getConfigurationValue(CameraConfiguration.FULL_SCREEN_ENABLED)) {

      // Before setting full screen flags, we must wait a bit to let UI settle; otherwise, we may
      // be trying to set app to immersive mode before it's ready and the flags do not stick
      container.postDelayed({
        container.systemUiVisibility = FLAGS_FULLSCREEN
      }, IMMERSIVE_FLAG_TIMEOUT)
    }
  }

  /**
   * Inflate camera controls and update the UI manually upon config changes to avoid removing
   * and re-adding the view finder from the view hierarchy; this provides a seamless rotation
   * transition on devices that support it.
   * NOTE: The flag is supported starting in Android 8 but there still is a small flash on the
   * screen for devices that run Android 9 or below.
   */
  override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    drawCameraControls()
  }

  /** Volume down button receiver used to trigger shutter */
  override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
    return when (keyCode) {
      // When the volume down button is pressed, simulate a shutter button click
      KeyEvent.KEYCODE_VOLUME_DOWN -> {
        val shutter = container.findViewById<ImageButton>(R.id.camera_capture_button)
        shutter.simulateClick()
        true
      }
      else -> super.onKeyDown(keyCode, event)
    }
  }

  /**
   * Method used to re-draw the camera UI controls, called every time configuration changes.
   */
  private fun drawCameraControls() {

    // Remove previous UI if any
    container.findViewById<ConstraintLayout>(R.id.camera_controls_container)?.let {
      container.removeView(it)
    }

    // Inflate a new view containing all UI for controlling the camera
    val controls = View.inflate(this, R.layout.camera_controls, container)

    // Listener for button used to capture photo
    controls.findViewById<ImageButton>(R.id.camera_capture_button).setOnClickListener {

      // Disable all camera controls
      findViewById<ImageButton>(R.id.camera_capture_button).isEnabled = false
      findViewById<ImageView>(R.id.camera_switch_button).isEnabled = false

      // Get a stable reference of the modifiable image capture use case
      imageCapture?.let { imageCapture ->

        // Create output file to hold the image
        val photoFile = createFile(filesDir)

        // Setup image capture metadata
        val metadata = ImageCapture.Metadata().apply {
          // Mirror image when using the front camera
          isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
          .setMetadata(metadata)
          .build()

        // Setup image capture listener which is triggered after photo has been taken
        imageCapture.takePicture(
          outputOptions, executor, object : ImageCapture.OnImageSavedCallback {

            @SuppressLint("SuspiciousIndentation")
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
              val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
              Log.d(TAG, "Image captured successfully: $savedUri")
              setResult(RESULT_OK, Intent().apply {
                putExtra(CameraConfiguration.IMAGE_URI, savedUri)
              })
              finish()
            }

            override fun onError(exc: ImageCaptureException) {
              Log.e(TAG, "Error capturing image", exc)
              cancelAndFinish()
            }
          })
      }
    }

    controls.findViewById<ImageView>(R.id.imageRectangle)?.setOnClickListener {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        if(!hasReadStoragePermission(this)){
          Toast.makeText(this, "Don't permission STORAGE", Toast.LENGTH_SHORT).show()
          val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
          ActivityCompat.requestPermissions(
            this, permissions, PERMISSION_CODE
          )
        } else {
          selectImageGallery();
        }
      }
      else{
        //system OS is < Marshmallow
        selectImageGallery();
      }
    }

    // Listener for button used to switch cameras
    controls.findViewById<ImageView>(R.id.camera_switch_button).setOnClickListener {

      // Flip-flop the required lens facing
      lensFacing = if (CameraSelector.LENS_FACING_FRONT == lensFacing) {
          CameraSelector.LENS_FACING_BACK
      } else {
          CameraSelector.LENS_FACING_FRONT
      }

      // Re-bind all use cases
      bindCameraUseCases()
    }

    // Apply user configuration every time controls are drawn
    applyUserConfiguration()
  }

  fun hasReadStoragePermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
  }

  private fun selectImageGallery(){
    val intent =  Intent(
      Intent.ACTION_PICK,
      MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    );
    intent.setType("image/*");
    startActivityForResult(
      Intent.createChooser(intent, "Select File"),
      REQUEST_SELECT_IMAGE_IN_ALBUM);
  }

  /** Declare and bind preview, capture and analysis use cases */
  private fun bindCameraUseCases() = viewFinder.post {

    cameraProvider.addListener(Runnable {

      // Camera provider is now guaranteed to be available
      val cameraProvider = cameraProvider.get()

      // Set up the view finder use case to display camera preview
      preview = Preview.Builder()
        .setTargetRotation(viewFinder.display.rotation)
        .build()
        .apply {
          setSurfaceProvider(viewFinder.previewSurfaceProvider)
        }

      // Set up the capture use case to allow users to take photos
      imageCapture = ImageCapture.Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
        .setTargetRotation(viewFinder.display.rotation)
        .build()

      // Create a new camera selector each time, enforcing lens facing
      val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

      // Apply declared configs to CameraX using the same lifecycle owner
      cameraProvider.unbindAll()
      val camera = cameraProvider.bindToLifecycle(
        this as LifecycleOwner, cameraSelector, preview, imageCapture)

      // TODO: Use camera controls to implement touch-to-focus once PreviewView metering
      //  point factory is ready
    }, executor)
  }

  override fun onResume() {
    super.onResume()

    // Request permissions each time the app resumes, since they can be revoked at any time
    if (!hasPermissions(this)) {
        ActivityCompat.requestPermissions(
            this, permissions.toTypedArray(), permissionsRequestCode
        )
    } else {
      drawCameraControls()
      bindCameraUseCases()
    }
  }

  /** Sets cancel result code and exits the activity */
  private fun cancelAndFinish() {
    setResult(RESULT_CANCELED)
    finish()
  }

  override fun onRequestPermissionsResult(
      requestCode: Int,
      permissions: Array<out String>,
      grantResults: IntArray
  ) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == permissionsRequestCode && hasPermissions(this)) {
      bindCameraUseCases()
    } else if(requestCode == PERMISSION_CODE && hasReadStoragePermission(this)){
      selectImageGallery();
    } else if(requestCode == PERMISSION_CODE && !hasReadStoragePermission(this)){

    } else {
      // Indicate that the user cancelled the action and exit if no permissions are granted
      cancelAndFinish()
    }
  }

  @SuppressLint("MissingSuperCall")
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if(resultCode == RESULT_OK && requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM){
      Log.d("ANH", data?.data.toString())
      //mImageView.setImageURI(data?.data)
      setResult(RESULT_OK, Intent().apply {
        putExtra(CameraConfiguration.IMAGE_URI, data?.data)
      })
      finish()
    }
  }

  /** Override back-navigation to add a cancelled result extra */
  override fun onBackPressed() {
    setResult(RESULT_CANCELED)
    super.onBackPressed()
  }

  /** Convenience method used to check if all permissions required by this app are granted */
  private fun hasPermissions(context: Context) = permissions.all {
    ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
  }

  companion object {
    const val TAG: String = "CAMERA_ACTIVITY"
    private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
    private const val PHOTO_EXTENSION = ".jpg"
    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1000
    private val PERMISSION_CODE = 1001

    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, CameraActivity::class.java)
      destIntent.putExtra(TAG, bundle)
      return destIntent
    }

    /** Helper function used to create a timestamped file */
    private fun createFile(
        baseFolder: File,
        format: String = FILENAME,
        extension: String = PHOTO_EXTENSION
    ) = File(
        baseFolder, SimpleDateFormat(format, Locale.US)
            .format(System.currentTimeMillis()) + extension
    )
  }
}