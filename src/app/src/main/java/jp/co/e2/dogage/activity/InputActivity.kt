package jp.co.e2.dogage.activity

import java.io.File
import java.io.IOException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import jp.co.e2.dogage.BuildConfig
import jp.co.e2.dogage.R
import jp.co.e2.dogage.common.AndroidUtils
import jp.co.e2.dogage.common.DateHelper
import jp.co.e2.dogage.common.ImgHelper
import jp.co.e2.dogage.common.LogUtils
import jp.co.e2.dogage.common.Utils
import jp.co.e2.dogage.config.AppApplication
import jp.co.e2.dogage.config.Config
import jp.co.e2.dogage.dialog.DatePickerDialog
import jp.co.e2.dogage.dialog.NoticeDialog
import jp.co.e2.dogage.dialog.KindSelectDialog
import jp.co.e2.dogage.entity.PetEntity
import jp.co.e2.dogage.model.BaseSQLiteOpenHelper
import jp.co.e2.dogage.model.PetDao
import jp.co.e2.dogage.validate.ValidateDate
import jp.co.e2.dogage.validate.ValidateHelper
import jp.co.e2.dogage.validate.ValidateLength
import jp.co.e2.dogage.validate.ValidateRequire

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.database.sqlite.transaction

import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

/**
 * 入力画面アクテビティ
 */
class InputActivity : BaseActivity() {

    companion object {
        private const val PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE = 100

        private const val PARAM_DATA = "data"
        private const val PARAM_PAGE_NUM = "page_num"
        private const val PARAM_INIT_FLG = "init_flg"
        private const val PARAM_PHOTO_PATH = "photo_path"

        private const val TAG_PERMISSION_REQUEST = "permission_request"

        /**
         * ファクトリーメソッドもどき
         *
         * @param activity アクテビティ
         * @param initFlg 初期フラグ
         * @param pageNum 表示対象のページ数
         * @param data エンティティ
         * @return intent
         */
        fun newInstance(activity: Activity, initFlg: Boolean, pageNum: Int, data: PetEntity?): Intent {
            val intent = Intent(activity, InputActivity::class.java)

            intent.putExtra(PARAM_INIT_FLG, initFlg)
            intent.putExtra(PARAM_PAGE_NUM, pageNum)

            if(data != null) {
                intent.putExtra(PARAM_DATA, data)
            } else {
                intent.putExtra(PARAM_DATA, PetEntity())
            }

            return intent
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_common)

        //アクションバーをセットする
        if (intent.getBooleanExtra(PARAM_INIT_FLG, false)) {
            setToolbar()
        } else {
            setBackArrowToolbar()
        }

        if (savedInstanceState == null) {
            val data = intent.getSerializableExtra(PARAM_DATA) as PetEntity
            val pageNum = intent.getIntExtra(PARAM_PAGE_NUM, 0)

            val fragment = InputFragment.newInstance(pageNum, data)
            supportFragmentManager.beginTransaction().add(R.id.container, fragment).commit()
        }
    }

    /**
     * ${inheritDoc}
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * InputFragment
     */
    class InputFragment : Fragment(), KindSelectDialog.CallbackListener, DatePickerDialog.CallbackListener,
            PopupMenu.OnMenuItemClickListener, NoticeDialog.CallbackListener, ActivityCompat.OnRequestPermissionsResultCallback {

        companion object {
            const val TAG_DIALOG_BIRTHDAY = "birthday"
            const val TAG_DIALOG_ARCHIVE = "archive"

            /**
             * ファクトリーメソッド
             *
             * @param pageNum 表示対象のページ数
             * @param data エンティティ
             * @return intent
             */
            fun newInstance(pageNum: Int, data: PetEntity?): Fragment {
                val bundle = Bundle()
                bundle.putInt(PARAM_PAGE_NUM, pageNum)

                if (data != null) {
                    bundle.putSerializable(PARAM_DATA, data)
                }

                val fragment = InputFragment()
                fragment.arguments = bundle

                return fragment
            }
        }

        private lateinit var petEntity: PetEntity
        private var photoPath: String? = null
        private var archiveOpenFlg = false

        /**
         * ${inheritDoc}
         */
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            if (savedInstanceState == null) {
                if (arguments!!.containsKey(PARAM_DATA)) {
                    petEntity = arguments!!.getSerializable(PARAM_DATA) as PetEntity
                }
            } else {
                petEntity = savedInstanceState.getSerializable(PARAM_DATA) as PetEntity
                photoPath = savedInstanceState.getString(PARAM_PHOTO_PATH)
            }
        }

        /**
         * ${inheritDoc}
         */
        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
            super.onCreate(savedInstanceState)

            val view = inflater.inflate(R.layout.fragment_input, container, false)

            //値を画面にセットする
            setItem(view)

            //クリックイベントをセットする
            setClickEvent(view)

            //スクロールビューのオーバースクロールで端の色を変えないように
            container!!.overScrollMode = View.OVER_SCROLL_NEVER

            return view
        }

        /**
         * ${inheritDoc}
         */
        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)

            outState.putSerializable(PARAM_DATA, petEntity)
            outState.putString(PARAM_PHOTO_PATH, photoPath)
        }

        /**
         * ${inheritDoc}
         */
        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)

            if (resultCode == Activity.RESULT_OK && requestCode == Config.INTENT_CODE_CAMERA) {
                //カメラ
                AndroidUtils.addPhotoToGallery(activity, photoPath)

                val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    FileProvider.getUriForFile(activity!!, BuildConfig.APPLICATION_ID + ".provider", File(photoPath!!))
                } else {
                    Uri.fromFile(File(photoPath!!))
                }
                doTrimming(uri)
            } else if (resultCode == Activity.RESULT_OK && requestCode == Config.INTENT_CODE_GALLERY) {
                //ギャラリー
                doTrimming(data!!.data)
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                //トリミング
                val result = CropImage.getActivityResult(data)

                if (resultCode == Activity.RESULT_OK) {
                    fromTrimmingIntent(result.uri)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val noticeDialog = NoticeDialog.newInstance(getString(R.string.error), getString(R.string.trimming_fail))
                    noticeDialog.show(fragmentManager, "dialog")
                }
            }
        }

        /**
         * ${inheritDoc}
         */
        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            when (requestCode) {
                PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE -> {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        startCamera()
                    } else {
                        //拒否の場合はカメラが使えない旨を表示
                        val title = getString(R.string.error)
                        val msg = getString(R.string.permission_denied)

                        val noticeDialog = NoticeDialog.newInstance(title, msg)
                        noticeDialog.show(fragmentManager, "dialog")
                    }
                }
            }
        }

        /**
         * 値を画面にセットする
         *
         * @param view
         */
        private fun setItem(view: View) {
            view.findViewById<EditText>(R.id.editTextName).apply {
                this.setText(petEntity.name)
            }

            if (petEntity.birthday != null) {
                setDateToButton(view, R.id.buttonBirthday, petEntity.birthday!!)
            }

            view.findViewById<Button>(R.id.buttonKind).apply {
                this.text = petEntity.getKindDisp(activity!!)
            }

            if (petEntity.archiveDate != null) {
                setDateToButton(view, R.id.buttonArchive, petEntity.archiveDate!!)
                setOpenButton(view)
            }

            if (petEntity.photoFlg) {
                view.findViewById<ImageView>(R.id.imageViewPhoto).apply {
                    this.setImageBitmap(petEntity.getPhotoInput(activity!!))
                }
            } else {
                //画像がない場合、NO PHOTOを角丸の画像にする
                setNoPhoto(view)
            }

            //アーカイブ関連を制御する
            if (petEntity.archiveDate == null) {
                archiveOpenFlg = true

                setClearButton(view, false)
                setOpenButton(view)
            }
        }

        /**
         * NO PHOTOをセットする
         */
        private fun setNoPhoto(view: View) {
            try {
                view.findViewById<ImageView>(R.id.imageViewPhoto).apply {
                    val imgUtils = ImgHelper(activity, R.drawable.img_no_photo)
                    val bitmap = imgUtils.getKadomaruBitmap(AndroidUtils.dpToPixel(activity, Config.KADOMARU_DP))

                    this.setImageBitmap(bitmap)
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        /**
         * ボタン上に日付を表示する
         *
         * @param view
         * @param resId リソースID
         * @param date 日付
         */
        private fun setDateToButton(view: View, resId: Int, date: String) {
            view.findViewById<Button>(resId).apply {
                val dateArray = date.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val dateFormat = getString(R.string.date_format)

                this.text = String.format(dateFormat, dateArray[0], dateArray[1], dateArray[2])
            }
        }

        /**
         * クリアボタン表示非表示etc処理
         *
         * @param view
         * @param flg 表示フラグ
         */
        private fun setClearButton(view: View, flg: Boolean) {
            if (flg) {
                view.findViewById<Button>(R.id.buttonClear).apply {
                    this.visibility = View.VISIBLE
                }
            } else {
                petEntity.archiveDate = null

                view.findViewById<ImageButton>(R.id.buttonClear).apply {
                    this.visibility = View.GONE
                }

                view.findViewById<Button>(R.id.buttonArchive).apply {
                    this.text = null
                }
            }
        }

        /**
         * その他を開くボタン表示非表示etc処理
         *
         * @param view
         */
        private fun setOpenButton(view: View) {
            val linearLayoutArchive = view.findViewById<LinearLayout>(R.id.linearLayoutArchive)
            val buttonOpen = view.findViewById<Button>(R.id.buttonOpen)

            if (!archiveOpenFlg) {
                archiveOpenFlg = true

                linearLayoutArchive.visibility = View.VISIBLE

                val objectAnimator = ObjectAnimator.ofFloat(linearLayoutArchive, "alpha", 0f, 1f)
                objectAnimator.duration = 500
                objectAnimator.start()

                buttonOpen.text = getString(R.string.close_other)
            } else {
                archiveOpenFlg = false

                val objectAnimator = ObjectAnimator.ofFloat(linearLayoutArchive, "alpha", 1f, 0f)
                objectAnimator.duration = 300
                objectAnimator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        linearLayoutArchive.visibility = View.GONE
                        buttonOpen.text = getString(R.string.open_other)
                    }
                })
                objectAnimator.start()
            }
        }

        /**
         * クリックイベントをセットする
         *
         * @param view
         */
        private fun setClickEvent(view: View) {
            //誕生日
            view.findViewById<Button>(R.id.buttonBirthday).apply {
                this.setOnClickListener {
                    val title = getString(R.string.dog_birthday)

                    val datePickerDialog = DatePickerDialog.newInstance(petEntity.birthday, title)
                    datePickerDialog.setCallbackListener(this@InputFragment)
                    datePickerDialog.show(fragmentManager, TAG_DIALOG_BIRTHDAY)
                }
            }

            //種類
            val dogMasters = (activity!!.application as AppApplication).dogMasterList
            val kindSelectDialog = KindSelectDialog.newInstance(dogMasters)
            kindSelectDialog.setCallbackListener(this)

            view.findViewById<Button>(R.id.buttonKind).apply {
                this.setOnClickListener { kindSelectDialog.show(fragmentManager, "dialog") }
            }

            //写真
            view.findViewById<ImageView>(R.id.imageViewPhoto).apply {
                val popup = PopupMenu(context, this)
                popup.menuInflater.inflate(R.menu.photo_menu, popup.menu)
                popup.setOnMenuItemClickListener(this@InputFragment)

                this.setOnClickListener { popup.show() }
            }

            //開くボタン
            view.findViewById<Button>(R.id.buttonOpen).apply {
                this.setOnClickListener { setOpenButton(view) }
            }

            //アーカイブ
            view.findViewById<Button>(R.id.buttonArchive).apply {
                this.setOnClickListener {
                    val title = getString(R.string.dog_die_date)

                    val datePickerDialog = DatePickerDialog.newInstance(petEntity.archiveDate, title)
                    datePickerDialog.setCallbackListener(this@InputFragment)
                    datePickerDialog.show(fragmentManager, TAG_DIALOG_ARCHIVE)
                }
            }

            //クリアボタン
            view.findViewById<ImageButton>(R.id.buttonClear).apply {
                this.setOnClickListener { setClearButton(view, false) }
            }

            //保存ボタン
            view.findViewById<Button>(R.id.buttonSave).apply {
                this.setOnClickListener { save() }
            }
        }

        /**
         * 保存する
         */
        private fun save() {
            //バリデーション
            val v = validate()

            //エラーあり
            if (!v.result) {
                val errorMsg = Utils.implode(v.errorMsgList, "\n")
                val title = getString(R.string.error)

                val noticeDialog = NoticeDialog.newInstance(title, errorMsg)
                noticeDialog.show(fragmentManager, "dialog")
                return
            }

            //保存とページ遷移
            if (saveDb()) {
                //アラームセット
//                SetAlarmManager(context!!).set()

                activity!!.setResult(Activity.RESULT_OK)
                activity!!.finish()
            } else {
                val title = getString(R.string.error)
                val msg = getString(R.string.save_fail)

                val noticeDialog = NoticeDialog.newInstance(title, msg)
                noticeDialog.show(fragmentManager, "dialog")
            }
        }

        /**
         * バリデーション
         *
         * @return boolean
         */
        private fun validate(): ValidateHelper {
            val editTextName = view!!.findViewById<EditText>(R.id.editTextName)
            val name = editTextName.text.toString()

            //バリデーション
            val v = ValidateHelper()

            val keyName = getString(R.string.dog_name)
            ValidateRequire.check(v, name, keyName)
            ValidateLength.maxCheck(v, name, keyName, 10)

            val keyBirthday = getString(R.string.dog_birthday)
            ValidateRequire.check(v, petEntity.birthday, keyBirthday)
            ValidateDate.check(v, petEntity.birthday, keyBirthday, DateHelper.FMT_DATE)
            ValidateDate.isPastAllowToday(v, petEntity.birthday, keyBirthday)

            val keyKind = getString(R.string.dog_kind)
            ValidateRequire.check(v, petEntity.kind, keyKind)

            val keyDieDate = getString(R.string.dog_die_date)
            ValidateDate.check(v, petEntity.archiveDate, keyDieDate, DateHelper.FMT_DATE)
            ValidateDate.isPastAllowToday(v, petEntity.archiveDate, keyDieDate)

            //独自バリデーション
            if (!customValidate(petEntity.archiveDate, petEntity.birthday)) {
                v.error(keyDieDate, getString(R.string.validate_error_archive_date))
            }

            return v
        }

        /**
         * アーカイブ日付は誕生日より未来かどうか
         *
         * @param birthday 誕生日
         * @param archiveDate アーカイブ日付（亡くなった日）
         * @return boolean
         */
        private fun customValidate(birthday: String?, archiveDate: String?): Boolean {
            var ret = true

            if (birthday == null || archiveDate == null) {
                return true
            }

            try {
                val archiveDateMillis = DateHelper(archiveDate, DateHelper.FMT_DATE).milliSecond
                val birthdayMillis = DateHelper(birthday, DateHelper.FMT_DATE).milliSecond

                ret = (archiveDateMillis <= birthdayMillis)

            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return ret
        }

        /**
         * DBに保存する
         *
         * @return ret 結果
         */
        private fun saveDb(): Boolean {
            var ret = false

            val editTextName = view!!.findViewById<EditText>(R.id.editTextName)
            val name = editTextName.text.toString()
            petEntity.name = name

            BaseSQLiteOpenHelper(context!!).writableDatabase.use {
                it.transaction {
                    val petDao = PetDao(context!!)
                    ret = petDao.save(it, petEntity)
                }
            }

            return ret
        }

        /**
         * カメラを起動する
         */
        private fun startCamera() {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (intent.resolveActivity(activity!!.packageManager) != null) {
                if (!checkReadWriteExternalStoragePermission()) {
                    return
                }

                val file: File
                try {
                    file = createFile()
                } catch (e: IOException) {
                    e.printStackTrace()

                    val noticeDialog = NoticeDialog.newInstance(getString(R.string.error), e.message!!)
                    noticeDialog.show(fragmentManager, "dialog")
                    return
                }

                val uri = FileProvider.getUriForFile(context!!, BuildConfig.APPLICATION_ID + ".provider", file)
                LogUtils.d(uri)

                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(intent, Config.INTENT_CODE_CAMERA)
            }
        }

        /**
         * パーミッションチェック
         *
         * @return boolean
         */
        private fun checkReadWriteExternalStoragePermission(): Boolean {
            //パーミッションあり
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                    ContextCompat.checkSelfPermission(context!!, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true
            }

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //一度拒否されているので、必要な理由を表示する
                val title = getString(R.string.permission_request_title)
                val msg = getString(R.string.permission_request_text)

                val noticeDialog = NoticeDialog.newInstance(title, msg)
                noticeDialog.show(fragmentManager, TAG_PERMISSION_REQUEST)
                noticeDialog.setCallbackListener(this)
            } else {
                //パーミッションをリクエストする
                requestReadWriteExternalStoragePermission()
            }

            return false
        }

        /**
         * パーミッションをリクエストする
         */
        private fun requestReadWriteExternalStoragePermission() {
            //バージョンが6.0未満の場合は処理不要
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return
            }

            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

            requestPermissions(permissions, PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE)
        }

        /**
         * ファイルを生成する
         */
        private fun createFile(): File {
            if (!AndroidUtils.isExternalStorageWritable()) {
                throw IOException(getString(R.string.failed_to_use_camera))
            }

            val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "dog_age")
            LogUtils.d(dir.absolutePath)

            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    throw IOException(getString(R.string.failed_to_use_camera))
                }
            }

            val filename = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date()) + ".jpg"
            val file = File(dir, filename)

            if (!file.exists()) {
                if (!file.createNewFile()) {
                    throw IOException(getString(R.string.failed_to_use_camera))
                }
            }

            photoPath = file.absolutePath
            LogUtils.d(photoPath)

            return file

        }

        /**
         * ギャラリーを起動する
         */
        private fun startGallery() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"

            if (intent.resolveActivity(activity!!.packageManager) != null) {
                startActivityForResult(intent, Config.INTENT_CODE_GALLERY)
            } else {
                //ギャラリーアプリなくてエラー
                val title = getString(R.string.error)
                val msg = getString(R.string.no_gallery_app)

                val noticeDialog = NoticeDialog.newInstance(title, msg)
                noticeDialog.show(fragmentManager, "dialog")
            }
        }

        /**
         * トリミング起動処理
         *
         * @param uri トリミング対象の画像URI
         */
        private fun doTrimming(uri: Uri) {
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(activity!!, this)

            val msg = getString(R.string.lets_trimming_photo)
            AndroidUtils.showToastL(activity, msg)
        }

        /**
         * トリミングインテントからの戻り処理
         */
        private fun fromTrimmingIntent(uri: Uri) {
            try {
                petEntity.savePhotoUri = uri
                petEntity.photoFlg = true

                //角丸の画像にを取得
                val imgUtils = ImgHelper(uri.path)
                val size = AndroidUtils.dpToPixel(activity, Config.PHOTO_INPUT_DP)
                val kadomaruBitmap = imgUtils.getResizeKadomaruBitmap(size, size, AndroidUtils.dpToPixel(activity, Config.KADOMARU_DP))

                //画像を表示
                val imageViewPhoto = view!!.findViewById<ImageView>(R.id.imageViewPhoto)
                imageViewPhoto.setImageBitmap(kadomaruBitmap)

            } catch (e: Exception) {
                val title = getString(R.string.error)
                val msg = getString(R.string.trimming_fail)

                val noticeDialog = NoticeDialog.newInstance(title, msg)
                noticeDialog.show(fragmentManager, "dialog")
                e.printStackTrace()
            }

        }

        /**
         * ${inheritDoc}
         */
        override fun onClickKindSelectDialog(tag: String, kind: Int, name: String) {
            petEntity.kind = kind

            val buttonKind = view!!.findViewById<Button>(R.id.buttonKind)
            buttonKind.text = name
        }

        /**
         * ${inheritDoc}
         */
        override fun onClickDatePickerDialogOk(tag: String, date: String) {
            if (tag == TAG_DIALOG_BIRTHDAY) {
                petEntity.birthday = date
                setDateToButton(view!!, R.id.buttonBirthday, date)
            } else if (tag == TAG_DIALOG_ARCHIVE) {
                petEntity.archiveDate = date
                setDateToButton(view!!, R.id.buttonArchive, date)

                val buttonClear = view!!.findViewById<ImageButton>(R.id.buttonClear)
                buttonClear.visibility = View.VISIBLE
            }
        }

        /**
         * ${inheritDoc}
         */
        override fun onClickDatePickerDialogCancel(tag: String) {}

        /**
         * ${inheritDoc}
         */
        override fun onMenuItemClick(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.menuCamera -> startCamera()
                R.id.menuGallery -> startGallery()
                R.id.menuDelete -> {
                    petEntity.photoFlg = false
                    petEntity.savePhotoUri = null

                    //NO PHOTOをセットする
                    setNoPhoto(view!!)
                }
            }

            return false
        }

        /**
         * ${inheritDoc}
         */
        override fun onClickErrorDialogOk(tag: String) {
            //パーミッションリクエスト
            if (tag == TAG_PERMISSION_REQUEST) {
                requestReadWriteExternalStoragePermission()
            }
        }
    }
}