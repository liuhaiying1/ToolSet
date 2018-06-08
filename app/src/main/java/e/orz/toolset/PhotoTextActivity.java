package e.orz.toolset;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileOutputStream;

import e.orz.toolset.api.PhotoTextApi;

public class PhotoTextActivity extends AppCompatActivity {

    Button button;
    Button button2;
    ImageView imageView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_text);
        button = findViewById(R.id.select_photo);
        imageView = findViewById(R.id.imageview);
        textView = findViewById(R.id.tv_result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0x867);
            }
        });
        button2 = findViewById(R.id.take_photo);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(openCameraIntent, 0X768);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        //在相册里面选择好相片之后调回到现在的这个activity中
        switch (requestCode) {
            case 0x867:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri

                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        Bitmap bitmap = BitmapFactory.decodeFile(path);
                        imageView.setImageBitmap(bitmap);
                        cursor.close();
                        String s="";
                        for(String s1: PhotoTextApi.execute(path)){
                            s+=s1+"\n";
                        }
                        textView.setText(s);
                        System.out.println(s);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 0x768:
                if ( resultCode == RESULT_OK) {
                    Bitmap btImage = (Bitmap) data.getExtras().get("data");
                    String path = saveBitmapToSDCard(btImage, System.currentTimeMillis()+"");
                    imageView.setImageBitmap(btImage);
                    String s="";
                    for(String s1: PhotoTextApi.execute(path)){
                        s+=s1+"\n";
                    }
                    textView.setText(s);
                    System.out.println(s);

                }
                break;
        }
    }

    public static String saveBitmapToSDCard(Bitmap bitmap, String imagename) {
        String path = "/sdcard/" + "img-" + imagename + ".jpg";
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.close();
            }

            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
