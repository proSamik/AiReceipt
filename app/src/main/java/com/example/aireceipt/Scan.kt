package com.example.aireceipt

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.aireceipt.databinding.ActivityScanBinding
import com.example.aireceipt.room.Item
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Scan : AppCompatActivity() {
    private lateinit var binding:ActivityScanBinding
    private lateinit var cameraExecutor: ExecutorService
    private val viewModel: InventoryViewModel by viewModels{
        InventoryViewModelFactory(
            (this.application as InventoryApplication).database.itemDao()
        )
    }
    private lateinit var allitems: List<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i("cbill", "onCreate: dao call failed")
        val Observer = Observer<List<Item>>{ item->
            allitems = item
        }
        viewModel.allitems.observe(this,Observer)
        Log.i("cbill", "onCreate: find the problem")

        binding.dashboardbtn.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Set up the listener for take photo button

        cameraExecutor = Executors.newSingleThreadExecutor()



    }

    private fun startCamera() {
        //val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.campreview.surfaceProvider)
                }
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, TextDetector())
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()

                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }
    private val mTextRecognizer by lazy {
        TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }
    inner class TextDetector : ImageAnalysis.Analyzer {

        @SuppressLint("UnsafeOptInUsageError")
        override fun analyze(imageProxy: ImageProxy) {

            val mediaImage = imageProxy.image

            if (mediaImage != null) {
                val image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

                mTextRecognizer.process(image)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            //recognized text
                            //binding.txt.text = it.result?.text
                            Log.i("cbill", "analyze: failed at loop")
                           for(item in allitems){
                                Log.i("cbill", "analyze: failed at if")
                                if(it.result?.text==item.itemName){
                                    binding.found.text="Click New Item to add " + item.itemName
                                    viewModel.x=item.itemName
                                }
                            }

                        }

                        //TO AVOID: com.google.mlkit.common.MlKitException: Internal error has occurred when executing ML Kit tasks
                        imageProxy.close()
                    }
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }



}








