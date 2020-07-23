package cn.teach.equip.view.personmessage;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.guoqi.actionsheet.ActionSheet;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import cn.teach.equip.R;
import cn.teach.equip.api.HttpResultSubscriber;
import cn.teach.equip.api.HttpServerImpl;
import cn.teach.equip.base.MyApplication;
import cn.teach.equip.bean.pojo.UserBO;
import cn.teach.equip.mvp.MVPBaseActivity;
import cn.teach.equip.util.PhotoFromPhotoAlbum;


/**
 * MVPPlugin
 * 个人信息界面
 */

public class PersonMessageActivity extends MVPBaseActivity<PersonMessageContract.View, PersonMessagePresenter>
        implements PersonMessageContract.View, ActionSheet.OnActionSheetSelected {

    @BindView(R.id.user_img)
    RoundedImageView userImg;
    @BindView(R.id.jiahao)
    TextView jiahao;
    @BindView(R.id.user_img_layout)
    LinearLayout userImgLayout;
    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.cancle)
    TextView cancle;
    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.user_address)
    TextView userAddress;
    @BindView(R.id.user_danwei)
    TextView userDanwei;
    @BindView(R.id.address_layout)
    LinearLayout addressLayout;
    @BindView(R.id.address_line)
    View addressLine;

    private File cameraSavePath;//拍照照片路径
    private Uri uri;

    private File file;

    @Override
    protected int getLayout() {
        return R.layout.act_person_message;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        goBack();
        setTitleText("个人信息");
        cameraSavePath = new File(Environment.getExternalStorageDirectory().getPath() + "/" +
                System.currentTimeMillis() + ".jpg");

        showUI();
    }


    private void showUI() {
        if (StringUtils.isEmpty(MyApplication.userBO.getAvatarUrl())) {
            jiahao.setVisibility(View.VISIBLE);
            userImg.setVisibility(View.GONE);
        } else {
            jiahao.setVisibility(View.GONE);
            userImg.setVisibility(View.VISIBLE);
            Glide.with(this).load(MyApplication.userBO.getAvatarUrl()).into(userImg);
        }
        String address = "";
        if (!StringUtils.isEmpty(MyApplication.userBO.getProvinceName())) {
            address += MyApplication.userBO.getProvinceName() + "省";
        }
        if (!StringUtils.isEmpty(MyApplication.userBO.getCityName())) {
            address += MyApplication.userBO.getCityName();
        }
        if (MyApplication.userBO.getUserType() == 2) {  //企业用户不展示地址
            addressLayout.setVisibility(View.GONE);
            addressLine.setVisibility(View.GONE);
        }
        userAddress.setText(address);
        userDanwei.setText(MyApplication.userBO.getUnitName());
        etUserName.setText(MyApplication.userBO.getUserName());
    }


    @OnClick(R.id.user_img_layout)
    public void clickImg() {
        checkPermissions();
    }

    @OnClick(R.id.cancle)
    public void cancle() {
        finish();
    }

    @OnClick(R.id.edit_txt)
    public void editClick() {
        etUserName.setFocusable(true);
        etUserName.setFocusableInTouchMode(true);
        if (isShowing(this)) {
            showOrHide();
        }
    }

    @OnClick(R.id.commit)
    public void commit() {
        String userName = etUserName.getText().toString().trim();
        if (StringUtils.isEmpty(userName)) {
            showToast2("请输入用户名称！");
            return;
        }
        HttpServerImpl.saveUserInfo(userName, file).subscribe(new HttpResultSubscriber<UserBO>() {
            @Override
            public void onSuccess(UserBO s) {
                showToast("修改成功！");
                MyApplication.userBO = s;
                showUI();
            }

            @Override
            public void onFiled(String message) {
                showToast2(message);
            }
        });
    }


    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * Detect camera authorization
     */
    public void checkPermissions() {
        if (allPermissionsGranted()) {
            onPermissionGranted();
        } else {
            ActivityCompat.requestPermissions(this, getRequiredPermissions(), 0x11);
        }
    }

    public String[] getRequiredPermissions() {
        return new String[]{Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x11) {
            //已授权
            if (allGranted(grantResults)) {
                onPermissionGranted();
            } else {
                onPermissionRefused();
            }
        }
    }

    /**
     * Denied camera permissions
     */
    public void onPermissionRefused() {
        new AlertDialog.Builder(this).setMessage("请去设置开启相关权限！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

    private boolean allGranted(int[] grantResults) {
        boolean hasPermission = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
            }
        }
        return hasPermission;
    }

    /**
     * Got camera permissions
     */
    public void onPermissionGranted() {
        ActionSheet.showSheet(this, this, null);
    }


    //激活相册操作
    private void goPhotoAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    //激活相机操作
    private void goCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, "cn.teach.equip.fileprovider", cameraSavePath);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(cameraSavePath);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                photoPath = String.valueOf(cameraSavePath);
            } else {
                photoPath = uri.getEncodedPath();
            }
            Log.d("拍照返回图片路径:", photoPath);
            updateFile(new File(Objects.requireNonNull(photoPath)));
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            photoPath = PhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            updateFile(new File(photoPath));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(int whichButton) {
        switch (whichButton) {
            case ActionSheet.CHOOSE_PICTURE:
                //相册
                goPhotoAlbum();
                break;
            case ActionSheet.TAKE_PICTURE:
                //拍照
                goCamera();
                break;
            case ActionSheet.CANCEL:
                //取消
                break;
        }
    }


    private void updateFile(File file) {
        jiahao.setVisibility(View.GONE);
        userImg.setVisibility(View.VISIBLE);
        userImg.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        this.file = file;
//        showProgress();
//        HttpServerImpl.uploadHeadImage(file).subscribe(new HttpResultSubscriber<String>() {
//            @Override
//            public void onSuccess(String s) {
//                stopProgress();
//                Glide.with(getActivity()).load(s).into(ivHeadFragmentPersonalData);
//            }
//
//            @Override
//            public void onFiled(String message) {
//                stopProgress();
//                showToast(message);
//            }
//        });
    }


}
