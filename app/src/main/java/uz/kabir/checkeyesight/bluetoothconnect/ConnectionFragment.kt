package uz.kabir.checkeyesight.bluetoothconnect


import android.Manifest
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentChoosingConnectionBinding


class ConnectionFragment : Fragment() {

    private var _viewBinding: FragmentChoosingConnectionBinding? = null
    private val binding get() = _viewBinding!!

    private val bluetoothAdapter by lazy{
        BluetoothAdapter.getDefaultAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewBinding = FragmentChoosingConnectionBinding.inflate(inflater, container, false)
        return binding.root
    }



    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkBluetoothPermissions()

        binding.readBtn.setOnClickListener {
            if(isBluetoothEnabled())
                findNavController().navigate(R.id.action_choosingConnection_to_readFragment)
            else
                enableBluetooth()
        }

        binding.writeBtn.setOnClickListener {
            if (isBluetoothEnabled())
                findNavController().navigate(R.id.action_choosingConnection_to_writeFragment)
            else
                enableBluetooth()
        }

    }
    // Check if device supports Bluetooth and permissions are granted
    private fun checkBluetoothPermissions() {
        if (!hasBluetoothPermissions()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                bluetoothPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT
                    )
                )
            }
        }
    }
    // Permission request launcher
    private val bluetoothPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.all { it.value }
            if (!granted) {
                Toast.makeText(context, "Bluetooth permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    private fun hasBluetoothPermissions(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.BLUETOOTH_SCAN
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) == PackageManager.PERMISSION_GRANTED
        } else true
    }
    // Check if Bluetooth is enabled
    private fun isBluetoothEnabled(): Boolean {
        return bluetoothAdapter?.isEnabled == true
    }

    // Enable Bluetooth via system dialog
    private fun enableBluetooth() {
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "Bluetooth not supported", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isBluetoothEnabled()) {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            requestBluetoothLauncher.launch(intent)
        }
    }
    // Enable Bluetooth launcher
    private val requestBluetoothLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Toast.makeText(context, "Bluetooth enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Bluetooth not enabled", Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

}