package uz.kabir.checkeyesight.bluetoothconnect

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentWriteBinding
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*


class WriteFragment : Fragment() {


    //Reference variable for BluetoothAdapter object
    lateinit var myBluetoothAdapter: BluetoothAdapter

    //Array for paired BT Devices
    private var pairedDevicesArray = arrayOfNulls<BluetoothDevice>(50)

    //Constants for the Handler
    private val STATE_LISTENING = 1
    private val STATE_CONNECTING = 2
    private val STATE_CONNECTED = 3
    private val STATE_CONNECTION_FAILED = 4
    private val STATE_MESSAGE_RECEIVED = 5

    //Constant for bluetooth enable request
    private val REQUEST_ENABLE_BLUETOOTH = 1

    //Constants for App Name and UUID
    private val APP_NAME = "CheckEyesight"
    private val MY_UUID = UUID.fromString("58e0a7d7-eebc-11d8-9669-0800200c9a66")

    //Reference variable for SendReceive object
    lateinit var sendReceive: SendReceive


    private var viewBinding: FragmentWriteBinding? = null
    private val binding get() = viewBinding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.linearVariants.visibility = View.GONE

        //Enable bluetooth if it is disabled
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!myBluetoothAdapter.isEnabled) {
            val intentBTEnable = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intentBTEnable, REQUEST_ENABLE_BLUETOOTH)

            binding.rlFindDevices.visibility=View.GONE

        }
        else
        {
            binding.rlFindDevices.visibility=View.VISIBLE

        }

        implementListeners()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewBinding = FragmentWriteBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }


    //Function for implementing onClickListeners to different UI widgets
    @SuppressLint("MissingPermission")
    private fun implementListeners() {
        //List Devices Button onClickListener
        binding.btnListDevices.setOnClickListener {
            //On button click, we want to show list of all paired devices in the ListView

            //Define a Set for already paired BT devices
            val pairedDevicesSet: Set<BluetoothDevice> = myBluetoothAdapter.bondedDevices

            //Declare arrays for paired device names and paired devices
            val pairedDeviceNamesArray = arrayOfNulls<String>(pairedDevicesSet.size)
            //pairedDevicesArray = Array<BluetoothDevice>(pairedDevicesSet.size, null)

            var index = 0

            //Get all the device names and devices in the respective arrays from the set
            if (pairedDevicesSet.size > 0) {
                for (device in pairedDevicesSet) {
                    pairedDeviceNamesArray[index] = device.name
                    pairedDevicesArray[index] = device
                    index++
                }
            }

            //Define the arrayadapter for ListView
            val arrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1, pairedDeviceNamesArray
            )

            //Set the adapter to ListView
            binding.lvDevices.adapter = arrayAdapter
        }


        //Action when Paired Devices in the ListView are clicked
        binding.lvDevices.setOnItemClickListener { parent, view, position, id ->
            val clientClass = ClientClass(pairedDevicesArray[position]!!)
            clientClass.start()
            binding.status.text = "Connecting"
        }

        // yozish

//        btnSend1.setOnClickListener {
//            var string = etWriteMsg1.text.toString()
//            sendReceive.write(string.toByteArray())
//        }

        binding.tvOptionOne.setOnClickListener {
            object :CountDownTimer(2000,1000){
                override fun onTick(millisUntilFinished: Long) {
                    binding.tvOptionOne.isSelected=true
                    binding.tvOptionTwo.isSelected=false
                    binding.tvOptionThree.isSelected=false
                    binding.tvOptionFour.isSelected=false
                    sendReceive.write("1".toByteArray())
                }

                override fun onFinish() {
                    clearBtnSelected()
                }

            }.start()




        }
        binding.tvOptionTwo.setOnClickListener {

            object :CountDownTimer(1000,1000){
                override fun onTick(millisUntilFinished: Long) {
                    binding.tvOptionOne.isSelected=false
                    binding.tvOptionTwo.isSelected=true
                    binding.tvOptionThree.isSelected=false
                    binding.tvOptionFour.isSelected=false
                    sendReceive.write("2".toByteArray())
                }

                override fun onFinish() {
                    clearBtnSelected()
                }

            }.start()



        }
        binding.tvOptionThree.setOnClickListener {

            object :CountDownTimer(1000,1000){
                override fun onTick(millisUntilFinished: Long) {
                    binding.tvOptionOne.isSelected=false
                    binding.tvOptionTwo.isSelected=false
                    binding.tvOptionThree.isSelected=true
                    binding.tvOptionFour.isSelected=false
                    sendReceive.write("3".toByteArray())
                }

                override fun onFinish() {
                    clearBtnSelected()
                }

            }.start()



        }
        binding.tvOptionFour.setOnClickListener {

            object :CountDownTimer(1000,1000){
                override fun onTick(millisUntilFinished: Long) {
                    binding.tvOptionOne.isSelected=false
                    binding.tvOptionTwo.isSelected=false
                    binding.tvOptionThree.isSelected=false
                    binding.tvOptionFour.isSelected=true
                    sendReceive.write("4".toByteArray())
                }

                override fun onFinish() {
                    clearBtnSelected()
                }

            }.start()


        }


    }
    private   fun clearBtnSelected(){

        binding.tvOptionOne.isSelected=false
        binding.tvOptionTwo.isSelected=false
        binding.tvOptionThree.isSelected=false
        binding.tvOptionFour.isSelected=false
    }

    /*Create Handler for the status. This handler will listen the message from another thread and
    change the text of the Status TextView*/
    @SuppressLint("MissingInflatedId")
    var handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            STATE_LISTENING -> binding.status.text = "Listening"
            STATE_CONNECTING -> binding.status.text = "Connecting"
            STATE_CONNECTED -> binding.status.text = "Connected"
            STATE_CONNECTION_FAILED -> binding.status.text = "Connection Failed"
            STATE_MESSAGE_RECEIVED -> {
                val readBuff1 = msg.obj as ByteArray

                val tempMsg1 = String(readBuff1, 0, msg.arg1)

                val endSub = tempMsg1.substring(0, 7)
                val endOfWords = tempMsg1.substring(7,tempMsg1.length)


                if (endSub == "restart") {
                    val builder = AlertDialog.Builder(context, R.style.myFullscreenAlertDialogStyle)
                    val dialogView: View = layoutInflater.inflate(R.layout.custom_dialog_result, null)
                    builder.setView(dialogView)

                    val cancel = dialogView.findViewById<Button>(R.id.dialog_cancel_button)
                    val start = dialogView.findViewById<Button>(R.id.dialog_start_button)

                    val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
                    val infoTitle = dialogView.findViewById<TextView>(R.id.info_title)
                    infoTitle.gravity = Gravity.CENTER

                    val leftEye = endOfWords.split("/").get(0).toString()
                    val rightEye = endOfWords.split("/").get(1).toString()

                    infoTitle.text = "leftEye = $leftEye\n" +
                                        "right = $rightEye"


                    dialogTitle.text = "Qaytadan boshlamoqchimisz?"
                    val dialog = builder.create()
                    dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
                    dialog.setCancelable(false)
                    dialog.show()

                    cancel.setOnClickListener {
                        sendReceive.write("no".toByteArray())
                        findNavController().navigate(R.id.homeFragment)
                        dialog.dismiss()
                    }
                    start.setOnClickListener {
                        dialog.dismiss()
                        sendReceive.write("yes".toByteArray())
                        dialogScreen("left")
                    }
                }
                if (tempMsg1 == "finishing") {
                    dialogScreen("right")
                    Log.i("ishladii", tempMsg1)
                }
                if (tempMsg1 == "started") {
                    dialogScreen("left")
                    binding.rlFindDevices.visibility=View.GONE
                    binding.linearVariants.visibility = View.VISIBLE
                }

            }
        }
        true
    })

    //Thread for the Server
    @SuppressLint("MissingPermission")
    inner class ServerClass : Thread() {
        //Reference variable for BluetoothServerSocket object
        private lateinit var bluetoothServerSocket: BluetoothServerSocket

        init {
            try {
                //Initialisation of BluetoothServerSocket object
                bluetoothServerSocket =
                    myBluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun run() {
            var bluetoothSocket: BluetoothSocket? = null
            while (bluetoothSocket == null) {
                try {
                    val message = Message.obtain()
                    message.what = STATE_CONNECTING
                    handler.sendMessage(message)

                    //Listen for the connection
                    bluetoothSocket = bluetoothServerSocket.accept()
                } catch (e: IOException) {
                    e.printStackTrace()
                    val message = Message.obtain()
                    message.what = STATE_CONNECTION_FAILED
                    handler.sendMessage(message)
                    break
                }
                /*If the connection is established, we will get a socket which will be used to
                send and receive the messages*/
                if (bluetoothSocket != null) {
                    val message = Message.obtain()
                    message.what = STATE_CONNECTED
                    handler.sendMessage(message)
                    //Do something for Send/Receive
                    sendReceive = SendReceive(bluetoothSocket)
                    sendReceive.start()
                    break
                }
            }
        }
    }

    //Thread for the client
    @SuppressLint("MissingPermission")
    inner class ClientClass(device: BluetoothDevice) : Thread() {
        private lateinit var bluetoothSocket: BluetoothSocket
        private var bluetoothDevice: BluetoothDevice = device

        init {
            try {
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        @SuppressLint("MissingPermission")
        override fun run() {
            try {
                bluetoothSocket.connect()
                val message = Message.obtain()
                message.what = STATE_CONNECTED
                handler.sendMessage(message)

                //Do something for Send/Receive
                sendReceive = SendReceive(bluetoothSocket)
                sendReceive.start()
            } catch (e: IOException) {
                e.printStackTrace()
                val message = Message.obtain()
                message.what = STATE_CONNECTION_FAILED
                handler.sendMessage(message)
            }
        }
    }

    //Thread for sending and receiving messages
    inner class SendReceive(socket: BluetoothSocket) : Thread() {
        private var bluetoothSocket: BluetoothSocket
        private var inputStream: InputStream
        private var outputStream: OutputStream

        init {
            bluetoothSocket = socket
            var tempIn: InputStream? = null
            var tempOut: OutputStream? = null
            try {
                tempIn = bluetoothSocket.inputStream
                tempOut = bluetoothSocket.outputStream
            } catch (e: IOException) {
                e.printStackTrace()
            }
            inputStream = tempIn!!
            outputStream = tempOut!!
        }

        override fun run() {
            val buffer = ByteArray(1024)
            var bytes: Int
            while (true) {
                try {
                    bytes = inputStream.read(buffer)
                    handler.obtainMessage(STATE_MESSAGE_RECEIVED, bytes, -1, buffer)
                        .sendToTarget()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        fun write(bytes: ByteArray) {
            try {
                outputStream.write(bytes)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun dialogScreen(eye: String) {

        val builder = AlertDialog.Builder(context,R.style.myFullscreenAlertDialogStyle)
        val dialogView: View = layoutInflater.inflate(R.layout.custom_dialog_bluetooth, null)

        builder.setView(dialogView)
        val next = dialogView.findViewById<Button>(R.id.button_continue)
        val image = dialogView.findViewById<ImageView>(R.id.left_right_image_bluetooth)

        val dialogTitle = dialogView.findViewById<TextView>(R.id.text)

        if (eye == "right") {
            image.setImageResource(R.drawable.vp_close_left_eye)
            dialogTitle.text = getString(R.string.close_left_eye)
        } else {
            image.setImageResource(R.drawable.vp_close_right_eye)
            dialogTitle.text = getString(R.string.close_right_eye)
        }


        val dialog = builder.create()
        dialog.window?.setGravity(Gravity.CENTER_VERTICAL)
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.setCancelable(false)
        dialog.show()
        next.setOnClickListener {
            if (eye == "right") {
                sendReceive.write("yesNext".toByteArray())
                dialog.dismiss()
            } else {
                dialog.dismiss()

            }
        }
    }

    override fun onDestroyView() {
        viewBinding=null
        super.onDestroyView()
    }

}