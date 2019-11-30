package com.example.customisedpdfview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
 Button mSaveBtn,openpdf;
EditText mtextEt;
private static final  int STORAGE_CODE=1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mSaveBtn=findViewById(R.id.button);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // we need to handle the runtime permission for the device with marshmallow and above
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M)
                {
                    //syatem os>=marshmallow,check if permission is enabled or not
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)
                    {
                        String[]  permission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,STORAGE_CODE);

                    }
                    else{

                        //permission is already graneted,call save pdf method
                        savepdf();
                    }


                }
                else {

                    //system os <marshmallow ,call save pdf method
                    savepdf();
                }
            }
        });

        /*openpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openpdf();
            }
        });*/

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case STORAGE_CODE:{
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //permision granted from popup,call save pdf
                    savepdf();
                }
                else {
                    //permisson was denied from popup,show  error message
                    Toast.makeText(this,"permission denied........",Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public  void savepdf()
{
    //create object of document class
    Document mDoc=new Document();
    String mfileName= new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
   //pdf file path
    String mfilepath= Environment.getExternalStorageDirectory()+"/"+mfileName+".pdf";
    try{
        // create instance of pdf writer
        PdfWriter.getInstance(mDoc,new FileOutputStream(mfilepath));

        //open the documnet for writing
        mDoc.open();

        //get text from edit text
        String mText=mtextEt.getText().toString();

        // add author of documnet (optional)
    mDoc.addAuthor("osam khatri");

    //add paragraph ( to document
        mDoc.add(new Paragraph(mText));

        // clos the document
        mDoc.close();

        // show msg file saved it will show file name and file path as well
        Toast.makeText(this,mfileName+".pdf\n is saved to \n"+mfilepath,Toast.LENGTH_LONG).show();

    }catch (Exception e){
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
    }

}

}
