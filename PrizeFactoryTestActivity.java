
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pri.factorytest.CameraBack.CameraBack;
import com.pri.factorytest.LCD.LCD;
import com.pri.factorytest.Service.BluetoothScanService;
import com.pri.factorytest.Service.WifiScanService;
import com.pri.factorytest.Version.Version;
import com.pri.factorytest.util.Utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pri.factorytest.FactoryTestApplication.GSM_SERIAL;
import static com.pri.factorytest.FactoryTestApplication.PRIZE_CUSTOMER;
import static com.pri.factorytest.util.Utils.BATTERY;
import static com.pri.factorytest.util.Utils.BLUETOOTH;
import static com.pri.factorytest.util.Utils.CAMERA_HALL;
import static com.pri.factorytest.util.Utils.CAMERA_HALL_CALI;

import static com.pri.factorytest.util.Utils.DOUBLE_CAMERA_STANDAR;

import static com.pri.factorytest.util.Utils.EFUSE_STATE;
import static com.pri.factorytest.util.Utils.FINGERPRINT;
import static com.pri.factorytest.util.Utils.FINGER_KEY_CHECK;
import static com.pri.factorytest.util.Utils.FLASH_LAMP_FRONT;
import static com.pri.factorytest.util.Utils.FRONT_CAM;
import static com.pri.factorytest.util.Utils.FRONT_CAM_SUB;
import static com.pri.factorytest.util.Utils.FRONT_CAM_SUB2;
import static com.pri.factorytest.util.Utils.GPS;
import static com.pri.factorytest.util.Utils.GRAVITY_SENSOR;
import static com.pri.factorytest.util.Utils.GYROSCOPE_SENSOR;
import static com.pri.factorytest.util.Utils.HALL_SENSOR;
import static com.pri.factorytest.util.Utils.HEADSET;
import static com.pri.factorytest.util.Utils.HEART_RATE_SENSOR;
import static com.pri.factorytest.util.Utils.INFRARED;
import static com.pri.factorytest.util.Utils.KEYS;
import static com.pri.factorytest.util.Utils.LED;
import static com.pri.factorytest.util.Utils.LIGHT_SENSOR;
import static com.pri.factorytest.util.Utils.MAGNETIC_SENSOR;
import static com.pri.factorytest.util.Utils.MICRO_PHONE;
import static com.pri.factorytest.util.Utils.MICRO_PHONE_LOOP;
import static com.pri.factorytest.util.Utils.NFC;
import static com.pri.factorytest.util.Utils.NXPCAL;
import static com.pri.factorytest.util.Utils.OTG;
import static com.pri.factorytest.util.Utils.PHONE;
import static com.pri.factorytest.util.Utils.PRESS_SENSOR;
import static com.pri.factorytest.util.Utils.RADIO;
import static com.pri.factorytest.util.Utils.RAM;
import static com.pri.factorytest.util.Utils.RANG_SENSOR;
import static com.pri.factorytest.util.Utils.REAR_CAM;
import static com.pri.factorytest.util.Utils.REAR_CAM_SUB;
import static com.pri.factorytest.util.Utils.REAR_CAM_SUB2;
import static com.pri.factorytest.util.Utils.REAR_CAM_SUB3;
import static com.pri.factorytest.util.Utils.RECEIVER;
import static com.pri.factorytest.util.Utils.REVERSE_WIRELESS_CHARGER;
import static com.pri.factorytest.util.Utils.SIM;
import static com.pri.factorytest.util.Utils.SPEAKER;
import static com.pri.factorytest.util.Utils.STEP_COUNTER_SENSOR;
import static com.pri.factorytest.util.Utils.SUB_MICRO_PHONE;
import static com.pri.factorytest.util.Utils.TF_CARD;
import static com.pri.factorytest.util.Utils.TORCH_HARDWARE;
import static com.pri.factorytest.util.Utils.TOUCH_SCREEN;
import static com.pri.factorytest.util.Utils.VOLTMETER;
import static com.pri.factorytest.util.Utils.WIFI;
import static com.pri.factorytest.util.Utils.WIRELESS_CHARGER;
import static com.pri.factorytest.util.Utils.YCD;

public class PrizeFactoryTestActivity extends PrizeBaseActivity {
    private static final String TAG = "PrizeFactoryTestMain";

    private Button pcbaTestButton = null;
    private Button autoTestButton = null;
    private Button manualTestButton = null;
    private Button listtestButton = null;
    private Button testReportButton = null;
    private Button factorySetButton = null;
    private Button softInfoButton = null;
    private Button agingtestButton = null;
    private Button languageSwitchButton = null;
    private SensorManager mSensorManager = null;
    private boolean isPcbaTest = false;
    private boolean isMobileTest = false;
    private boolean isManualTest = false;
    private Context mContext;
    private long startMili;
    private long endMili;
    private long mTestTime;
    private FactoryTestApplication mApp;
    private volatile int mExceptItem;
    private String[] mTestItems = new String[70];
    private String[] mItemsValue = new String[70];
    private volatile String[] mTestValue = new String[70];
    private List<String> mItemsValueList = null;
    private String mLastOperation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factory_test);
        //Log.i(TAG, "----system.serial" + Build.getSerial());
        mContext = this;
        mTestItems = mContext.getResources().getStringArray(R.array.single_test_items);
                case "com.pri.factorytest.ManualTestLauncher":
                    startManualTest();
                    break;

                case "com.pri.factorytest.PrizeFactoryTestLauncher":
                
