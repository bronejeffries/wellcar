package com.tambula.code.Login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetectorOptions;

import java.util.List;


@SuppressLint("AppCompatCustomView")
public class FaceImageView extends ImageView {

    private static final int MAX_FACES = 1;
    private RectF[] rects = new RectF[MAX_FACES];
    private Bitmap image;
    private Bitmap croped;
    public static float radius = 10.0f;

    public FaceImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public FaceImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public FaceImageView(Context context) {
        super(context);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        /*@SuppressLint("DrawAllocation") Path clipPath = new Path();
        @SuppressLint("DrawAllocation") RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);*/

        super.onDraw(canvas);

    }

    Bitmap detectFace(InputImage inmage, Bitmap image) {

        this.image = image;

        FaceDetectorOptions options =
                new FaceDetectorOptions.Builder()
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                        .setMinFaceSize(0.15f)
                        .enableTracking()
                        .build();

        com.google.mlkit.vision.face.FaceDetector detector = FaceDetection.getClient(options);

        Task<List<com.google.mlkit.vision.face.Face>> result =
                detector.process(inmage)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<com.google.mlkit.vision.face.Face>>() {
                                    @Override
                                    public void onSuccess(List<com.google.mlkit.vision.face.Face> faces) {

                                        for (com.google.mlkit.vision.face.Face face : faces) {
                                            Rect bounds = face.getBoundingBox();
                                            float rotY = face.getHeadEulerAngleY();

                                            try {

                                                croped = Bitmap.createBitmap(image, bounds.left, bounds.top, bounds.width(), bounds.height());

                                               }
                                               catch(Exception e){
                                                   Toast.makeText(getContext(), "Face Resizing Failed", Toast.LENGTH_LONG).show();

                                                }

                                          }

                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(getContext(), "Face Detection Failed", Toast.LENGTH_LONG).show();
                                    }
                                });

        return croped;

    }
}