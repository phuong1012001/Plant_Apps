package com.phngsapplication.app.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var mainActivity: MainActivity

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference

    private val storagePath = "Users_Profile_Cover_image/"

    private lateinit var pd: ProgressDialog
    private var db = Firebase.firestore

    private val cameraPermission =
        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val storagePermission = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private lateinit var imageUri: Uri
    private lateinit var profileOrCoverPhoto: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_profile, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!
        firebaseDatabase = FirebaseDatabase.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        databaseReference = firebaseDatabase.reference.child("Users")

        pd = ProgressDialog(requireContext())
        pd.setCanceledOnTouchOutside(false)

        loadImageFromUser()

        binding.changepassword.setOnClickListener {
            pd.setMessage("Changing Password")
            showPasswordChangeDialog()
        }

        binding.profilepic.setOnClickListener {
            pd.setMessage("Updating Profile Picture")
            profileOrCoverPhoto = "image"
            showImagePicDialog()
        }

        binding.editname.setOnClickListener {
            pd.setMessage("Updating Name")
            showNamePhoneUpdate("name")
        }

        binding.editlocal.setOnClickListener{
            pd.setMessage("Updating Local")
            showLocalUpdate("local")
        }

        return binding.root
    }

    private fun loadImageFromUser() {
        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val image = data.get("profileImage")
                        val uid = data.get("id")
                        if(uid.toString() == firebaseUser.uid)
                        {
                            Log.d("User", uid.toString())
                            try {
                                Glide.with(requireContext()).load(image).into(binding.editProfileImage)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
            .addOnFailureListener {e->
                Toast.makeText(mainActivity, "Failed to load FireStore due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showPasswordChangeDialog() {
        val view: View =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_update_password, null)
        val oldpass = view.findViewById<EditText>(R.id.oldpasslog)
        val newpass = view.findViewById<EditText>(R.id.newpasslog)
        val update = view.findViewById<Button>(R.id.updatepass)

        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setView(view)

        val dialog: AlertDialog = builder.create()
        dialog.show()

        update.setOnClickListener {
            val oldPassword = oldpass.text.toString().trim()
            val newPassword = newpass.text.toString().trim()

            if (TextUtils.isEmpty(oldPassword)) {
                oldpass.error = "Required"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(newPassword)) {
                newpass.error = "Required"
                return@setOnClickListener
            }

            pd.show()
            val user = firebaseAuth.currentUser
            val email = firebaseUser.email
            val credential: AuthCredential? =
                email?.let { it1 -> EmailAuthProvider.getCredential(it1, oldPassword) }

            if (credential != null) {
                user!!.reauthenticate(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        user.updatePassword(newPassword).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                pd.dismiss()
                                Toast.makeText(requireContext(), "Password updated", Toast.LENGTH_SHORT)
                                    .show()
                                dialog.dismiss()
                                firebaseAuth.signOut()
                                startActivity(Intent(requireContext(), LoginScreenActivity::class.java))
                                requireActivity().finish()
                            } else {
                                pd.dismiss()
                                Toast.makeText(
                                    requireContext(),
                                    "Password update failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        pd.dismiss()
                        Toast.makeText(requireContext(), "Authentication failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun showImagePicDialog() {
        val options = arrayOf("Camera", "Gallery")
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Pick Image From")
        builder.setItems(options) { _, which ->
            if (which == 0) {
                if (!checkCameraPermission()) {
                    requestCameraPermission()
                } else {
                    pickFromCamera()
                }
            } else if (which == 1) {
                if (!checkStoragePermission()) {
                    requestStoragePermission()
                } else {
                    pickFromGallery()
                }
            }
        }
        builder.create().show()
    }

    private fun showNamePhoneUpdate(key: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Update $key")
        val linearLayout = LinearLayout(requireContext())
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.setPadding(10, 10, 10, 10)

        val editText = EditText(requireContext())
        editText.hint = "Enter $key"
        linearLayout.addView(editText)

        builder.setView(linearLayout)
        builder.setPositiveButton("Update") { _, _ ->
            val value = editText.text.toString().trim()
            if (!TextUtils.isEmpty(value)) {
                val userMap = mapOf(
                    "name" to value,
                )
                db.collection("User").document(firebaseAuth.uid.toString()).update(userMap)
                    .addOnSuccessListener {
                        //user info save, open user dashboard
                        //progressDialog.dismiss()
                        Toast.makeText(requireContext(), "Updated Successfully...", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener{e->
                        //progressDialog.dismiss()
                        Toast.makeText(requireContext(), "Failed to add FireStore due to ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(requireContext(), "Please enter $key", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        builder.create().show()
    }

    private fun showLocalUpdate(key: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Update $key")
        val linearLayout = LinearLayout(requireContext())
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.setPadding(10, 10, 10, 10)

        val editText = EditText(requireContext())
        editText.hint = "Enter $key"
        linearLayout.addView(editText)

        builder.setView(linearLayout)
        builder.setPositiveButton("Update") { _, _ ->
            val value = editText.text.toString().trim()
            if (!TextUtils.isEmpty(value)) {
                val userMap = mapOf(
                    "local" to value,
                )
                db.collection("User").document(firebaseAuth.uid.toString()).update(userMap)
                    .addOnSuccessListener {
                        //user info save, open user dashboard
                        //progressDialog.dismiss()
                        Toast.makeText(requireContext(), "Updated Successfully...", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener{e->
                        //progressDialog.dismiss()
                        Toast.makeText(requireContext(), "Failed to add FireStore due to ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(requireContext(), "Please enter $key", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        builder.create().show()
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

    private fun pickFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_GALLERY_REQUEST)
    }

    private fun pickFromCamera() {
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
                uploadProfileCoverPhoto(imageUri)
            }
            if (requestCode == IMAGE_PICK_CAMERA_REQUEST) {
                uploadProfileCoverPhoto(imageUri)
            }
        }
    }

    private fun uploadProfileCoverPhoto(uri: Uri?) {
        pd.show()
        val filePathAndName = storagePath + "" + profileOrCoverPhoto + "_" + firebaseUser.uid
        val storageReference2nd = storageReference.child(filePathAndName)
        storageReference2nd.putFile(uri!!)
            .addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val downloadUri = uriTask.result
                if (uriTask.isSuccessful) {
                    val userMap = mapOf(
                        "profileImage" to downloadUri.toString(),
                    )
                    db.collection("User").document(firebaseAuth.uid.toString()).update(userMap)
                        .addOnSuccessListener {
                            pd.dismiss()
                            Toast.makeText(requireContext(), "Image Updated...", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener{e->
                            pd.dismiss()
                            Toast.makeText(requireContext(), "Failed to add FireStore due to ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    loadImageFromUser()
                } else {
                    pd.dismiss()
                    Toast.makeText(requireContext(), "Error occurred", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                pd.dismiss()
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
            .addOnProgressListener { taskSnapshot ->
                val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount)
                pd.setMessage("Uploading $progress%")
            }
    }

    companion object {
        private const val CAMERA_REQUEST = 100
        private const val STORAGE_REQUEST = 200
        private const val IMAGE_PICK_GALLERY_REQUEST = 300
        private const val IMAGE_PICK_CAMERA_REQUEST = 400
    }
}