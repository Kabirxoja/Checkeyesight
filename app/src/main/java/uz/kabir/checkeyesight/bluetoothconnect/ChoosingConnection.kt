package uz.kabir.checkeyesight.bluetoothconnect


import android.Manifest
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentChoosingConnectionBinding

const val REQUEST_ENABLE_BLUETOOTH = 1

class ChoosingConnection : Fragment() {

    private var viewBinding: FragmentChoosingConnectionBinding? = null
    private val binding get() = viewBinding!!

    private lateinit var vieW: ConstraintLayout
    private var bluetoothAdapter: BluetoothAdapter? = null





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewBinding = FragmentChoosingConnectionBinding.inflate(inflater, container, false)

        vieW = binding.root

        return vieW
    }

    private fun checkNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                requestPermissions(arrayOf<String>(Manifest.permission.BLUETOOTH_SCAN,Manifest.permission.BLUETOOTH_CONNECT), REQUEST_ENABLE_BLUETOOTH)
            }
            else
            {
                Toast.makeText(context, "Bluetooth is not enabled", Toast.LENGTH_SHORT).show()
            }


        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkNotificationPermission()

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        binding.readBtn.setOnClickListener {
            if(isBluetoothEnabled())
            {
                findNavController().navigate(R.id.action_choosingConnection_to_readFragment)
            }
            else
            {
                enableBluetooth()
            }


        }

        binding.writeBtn.setOnClickListener {
            if (isBluetoothEnabled())
            {
                findNavController().navigate(R.id.action_choosingConnection_to_writeFragment)
            }
            else
            {
                enableBluetooth()
            }

        }

    }

    private fun enableBluetooth() {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "It is not supported", Toast.LENGTH_SHORT).show()
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBluetoothIntent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            startActivity(enableBluetoothIntent)
        }
    }

    // Check if Bluetooth is enabled on the device
    fun isBluetoothEnabled(): Boolean {
        val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled
    }


    private val requestMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions())
    { permissions ->
        permissions.entries.forEach {
            Log.d("test006", "${it.key} = ${it.value}")
        }
    }

    private var requestBluetooth = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            //granted
        } else {
            //deny
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.removeItem(R.id.info_uz)
    }

//    private fun requestBluetoothPermission(): Boolean {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            requestMultiplePermissions.launch(arrayOf(
//                Manifest.permission.BLUETOOTH_SCAN,
//                Manifest.permission.BLUETOOTH_CONNECT))
//            turnOn = true
//        } else if (!bluetoothAdapter?.isEnabled!!) {
//            // Bluetooth is not enabled, request the user to enable it
//            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
//            requestBluetooth.launch(enableBtIntent)
//            turnOn = false
//        }
//        return turnOn
//    }

    override fun onDestroyView() {
        viewBinding=null
        super.onDestroyView()
    }
}