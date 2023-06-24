package com.phngsapplication.app.ui

import android.app.ProgressDialog
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.StorageReference
import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.phngsapplication.app.R
import com.phngsapplication.app.model.Article
import kotlinx.android.synthetic.main.fragment_post_articles.*

class PostArticles : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var firestoredatabase: FirebaseFirestore

//    private val storagePath = "ImagePostArticles/"
    private lateinit var uid: String
    private lateinit var postCoverPhoto: String

    private lateinit var imagePost: ImageView
    private lateinit var imageButtonPost: ImageView
    private lateinit var cameraButtonPost: ImageView
    private lateinit var header: TextView
    private lateinit var name: TextView
    private lateinit var postingAs: TextView
    private lateinit var closeButton: ImageView
    private lateinit var sendButton: ImageView
    private lateinit var attachmentCloseButton: ImageView
    private lateinit var attachedImageName: TextView
    private lateinit var postStatus: EditText
    private lateinit var postTitle: EditText


    private var db = Firebase.firestore
    var articleList = ArrayList<Article>()


    private lateinit var pd: ProgressDialog

    private val cameraPermission =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val storagePermission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_articles, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        firestoredatabase = FirebaseFirestore.getInstance()
        firebaseUser = firebaseAuth.currentUser!!
        firebaseDatabase = FirebaseDatabase.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = firebaseDatabase.reference.child("Articles")
        pd = ProgressDialog(requireContext())
        pd.setCanceledOnTouchOutside(false)

        imageButtonPost = view.findViewById(R.id.imageButtonPost)
        cameraButtonPost = view.findViewById(R.id.cameraButtonPost)
        header = view.findViewById(R.id.header)
        postingAs = view.findViewById(R.id.postingAs)
        closeButton = view.findViewById(R.id.closeButton)
        name = view.findViewById(R.id.namepost)
        sendButton = view.findViewById(R.id.sendButton)

        attachmentCloseButton = view.findViewById(R.id.attachmentCloseButton)
        postStatus = view.findViewById(R.id.post)
        postTitle = view.findViewById(R.id.header)
        imagePost = view.findViewById(R.id.imagePost)


        val query: Query = databaseReference.orderByChild("email").equalTo(firebaseUser.email)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSnapshot1 in dataSnapshot.children) {
                    val image = dataSnapshot1.child("image").value.toString()
                    try {
                        Glide.with(requireContext()).load(image).into(imagePost)
                        Log.e("IAMGE POST: ", image.toString())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })

        loadArticlesFromFireStore()
        closeButton.setOnClickListener {
            var articlesArr : Array<Article> = articleList.toTypedArray()
            val action = PostArticlesDirections.actionPostArticlesToArticlesFragment(articlesArr)
            val controller = findNavController()
            controller.navigate(action)
        }

        imageButtonPost.setOnClickListener {
            pd.setMessage("Updating Profile Picture")
            postCoverPhoto = "image"
            ImagePush()
        }
        cameraButtonPost.setOnClickListener {
            pd.setMessage("Updating Profile Picture")
            postCoverPhoto = "image"
            CameraPush()
        }

        sendButton.setOnClickListener {
            val contentText = postStatus.text.toString().trim()
            val captionText = postTitle.text.toString().trim()
            if (captionText.isNotEmpty() && contentText.isNotEmpty()) {
                uploadImageToServer(imageUri, captionText, contentText)
                //uploadCaptionToFirestore(captionText)
            } else if(captionText.isEmpty()){
                Toast.makeText(requireContext(), "Please enter a caption", Toast.LENGTH_SHORT).show()
            } else if(contentText.isEmpty()){
            Toast.makeText(requireContext(), "Please enter a content", Toast.LENGTH_SHORT).show()
        }
        }

        attachmentCloseButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                TransitionManager.endTransitions(attachment)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition(attachment)
            }
        }

//        view.post {
//            postStatus.requestFocus()
//            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.showSoftInput(binding.post, InputMethodManager.SHOW_IMPLICIT)
//        }
//
//        setEnabled(true, binding.progressBar, binding.sendButton)
        return view
    }

    private fun loadArticlesFromFireStore() {
        db = FirebaseFirestore.getInstance()
        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener {
                if(!it.isEmpty){
                    articleList.clear()
                    for(data in it.documents){
                        val uid = data.get("id")
                        val name = data.get("name")
                        val avatar = data.get("profileImage")
                        db.collection("User/$uid/Articles").get().addOnSuccessListener {  }
                            .addOnSuccessListener {it1->
                                if(!it1.isEmpty){
                                    //data1.clear()
                                    for(data in it1.documents){
                                        val imgArticle = data.get("URL")
                                        val titleArticle = data.get("caption")
                                        val contentArticle = data.get("content")
                                        val txtDate = data.get("timestamp")
                                        articleList.add(Article(
                                            imgArticle.toString(),
                                            titleArticle.toString(),
                                            contentArticle.toString(),
                                            avatar.toString(),
                                            name.toString(),
                                            txtDate.toString()
                                        ))
                                    }
                                }
                            }
                    }
                }
            }
            .addOnFailureListener {e->
                Toast.makeText(requireContext(), "Failed to load FireStore due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setEnabled(enabled: Boolean, progressBar: ProgressBar, sendButton: ImageView) {
        progressBar.visibility = if (enabled) View.GONE else View.VISIBLE
        sendButton.visibility = if (enabled) View.VISIBLE else View.GONE
        imageButtonPost.isEnabled = enabled
        cameraButtonPost.isEnabled = enabled
    }

    private fun checkStoragePermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        return result
    }
    private fun requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST)
    }

    private fun checkCameraPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        val result1 = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        return result && result1
    }
    private fun requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST)
    }

    private fun ImagePush() {
        if (!checkCameraPermission()) {
            requestCameraPermission()
        } else {
            pickFromCamera()
        }
    }
    private fun CameraPush() {
        if (!checkStoragePermission()) {
            requestStoragePermission()
        } else {
            pickFromGallery()
        }
    }

    private fun pickFromGallery() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "Temp Pick")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Descr")
        imageUri = requireContext().contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            values
        )!!
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, IMAGE_PICK_CAMERA_REQUEST)
    }

    private fun pickFromCamera() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_GALLERY_REQUEST)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromCamera()
                } else {
                    Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickFromGallery()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Storage permission denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_REQUEST) {
                imageUri = data?.data!!
                try {
                    Glide.with(requireContext()).load(imageUri).into(imagePost)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            if (requestCode == IMAGE_PICK_CAMERA_REQUEST) {
                try {
                    Glide.with(requireContext()).load(imageUri).into(imagePost)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun uploadImageToServer(uri: Uri?, captionText: String, contentText: String) {

        pd.show()
        val timestamp = System.currentTimeMillis()
//        val filePathAndName = storagePath + "" + postCoverPhoto + "_" + firebaseUser.uid
        val filePathAndName = "ImagePostArticles/$timestamp"
        val storageReference2nd = storageReference.child(filePathAndName)
        storageReference2nd.putFile(uri!!)
            .addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val downloadUri = uriTask.result
                if (uriTask.isSuccessful) {
                    val results: HashMap<String, Any> = HashMap()
                    results[postCoverPhoto] = downloadUri.toString()
                    databaseReference!!.child(firebaseUser.uid).updateChildren(results)
                    uploadCaptionToFirestore(downloadUri.toString(), captionText, contentText, timestamp)
                    pd.dismiss()
                    Toast.makeText(requireContext(), "Image updated", Toast.LENGTH_SHORT).show()
                } else {
                    pd.dismiss()
                    Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener { e ->
                pd.dismiss()
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
            .addOnProgressListener { taskSnapshot ->
                val progress =
                    (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                pd.setMessage("Uploading $progress%")
            }
    }
    private fun uploadCaptionToFirestore(uploadedImageUrl: String, captionText: String, contentText: String, timestamp: Long) {
        val articleData = hashMapOf(
            //"id" to "$timestamp",
            "caption" to captionText,
            "content" to contentText,
            "URL" to uploadedImageUrl,
            "timestamp" to timestamp
        )

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        firestoredatabase.collection("User/$userId/Articles").document("$timestamp").set(articleData)
            .addOnSuccessListener {
                // Caption uploaded successfully
                // You can use the generatedId or perform any other actions here
                Toast.makeText(requireContext(), "Caption uploaded successfully", Toast.LENGTH_SHORT).show()
                var articlesArr : Array<Article> = articleList.toTypedArray()
                val action = PostArticlesDirections.actionPostArticlesToArticlesFragment(articlesArr)
                val controller = findNavController()
                controller.navigate(action)
            }
            .addOnFailureListener { e ->
                // Error occurred while uploading caption
                Toast.makeText(requireContext(), "Error uploading caption: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    companion object {
        private const val RESULT_LOAD_IMAGE = 1
        private const val RESULT_CAMERA = 2
        private const val CAMERA_REQUEST = 100
        private const val STORAGE_REQUEST = 200
        private const val IMAGE_PICK_GALLERY_REQUEST = 300
        private const val IMAGE_PICK_CAMERA_REQUEST = 400
    }
}