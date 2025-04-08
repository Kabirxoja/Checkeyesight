package uz.kabir.checkeyesight.bluetoothconnect

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.fragment.findNavController
import uz.kabir.checkeyesight.db.User
import uz.kabir.checkeyesight.db.UserDatabase
import uz.kabir.checkeyesight.visiontest.Question
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentReadBinding
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

class ReadFragment : Fragment() {

    private var mCurrentPosition: Int = 1
    private var mQuestionsList: ArrayList<Question>? = null //not initialised
    private var mSelectedOptionPosition: Int = 0
    private var counter = 0
    private var countList = 0

    private lateinit var options: List<Int?>

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


    lateinit var dateTime: String
    lateinit var calendar: Calendar
    lateinit var simpleDateFormat: SimpleDateFormat

    private var viewBinding: FragmentReadBinding? = null
    private val binding get() = viewBinding!!

    private var rightEyeCount: Float = 1.0F
    private var leftEyeCount: Float = 1.0F
    private var sizeImage: Float = 0F
    private var questionString = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewBinding = FragmentReadBinding.inflate(inflater, container, false)

        binding.resultText.visibility = View.GONE


        mQuestionsList = ListBlueTooth.getQuestions1()

        setQuestion(mCurrentPosition)

        resizeImagesRight(0)
        resizeImagesLeft(0)


        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!myBluetoothAdapter.isEnabled) {
            val intentBTEnable = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intentBTEnable, REQUEST_ENABLE_BLUETOOTH)

            binding.btnListen.visibility = View.GONE
            binding.tvStatus.visibility = View.GONE

        }
        else
        {
            binding.btnListen.visibility = View.VISIBLE
            binding.tvStatus.visibility = View.VISIBLE
            sizeImage = 43.65F
            resultText(rightEyeCount,sizeImage,"3")
            setImage(convertSizeImage(sizeImage))
        }

        implementListeners()
        return binding.root
    }


    @SuppressLint("MissingPermission")
    private fun implementListeners() {
        //Listen Button onClickListener
        binding.btnListen.setOnClickListener {
            val serverClass = ServerClass()
            serverClass.start()
        }

    }

    var handler = Handler(Handler.Callback { msg ->
        when (msg.what) {
            STATE_LISTENING -> binding.tvStatus.text = "Listening"
            STATE_CONNECTING -> binding.tvStatus.text = "Connecting"
            STATE_CONNECTED -> {
                binding.tvStatus.text = "Connected"
                //ulanishi bilan ishlab ketadi
                sendReceive.write("started".toByteArray())
                visibleIsGone()
                countList++
                setQuestion(mCurrentPosition)
                binding.resultText.visibility = View.VISIBLE
            }
            STATE_CONNECTION_FAILED -> binding.tvStatus.text = "Connection Failed"
            STATE_MESSAGE_RECEIVED -> {
                /*
                malumotlarni oqib olish
                 */
                val readBuff = msg.obj as ByteArray
                val tempMsg = String(readBuff, 0, msg.arg1)

                when (tempMsg) {
                    "1", "2", "3", "4" -> {
                        selectQuestion(Integer.parseInt(tempMsg))
                    }
                    "yes" -> {

                        //data save
                        resultSave()

                        //qayta boshlangan payti
                        counter = 0
                        countList = 1
                        mCurrentPosition = 1
                        setQuestion(mCurrentPosition)

                        rightEyeCount = 1.0F
                        leftEyeCount = 1.0F
                        questionString = ""

                        setImage(convertSizeImage(sizeImage))
                        resultText(leftEyeCount, sizeImage, "4 m")
                    }
                    "no" -> {


                        //data save
                        resultSave()

                        findNavController().navigate(R.id.homeFragment)

                    }
                    "yesNext" -> {
                        countList++
                        mCurrentPosition = 1
                        setQuestion(mCurrentPosition)

                        rightEyeCount = 1.0F
                        questionString = ""
                        sizeImage = 58.2F

                        counter = 0
                        setImage(convertSizeImage(sizeImage))
                        resultText(rightEyeCount, sizeImage, "4 m")
                    }
                    else ->
                        Toast.makeText(requireContext(), "error toast", Toast.LENGTH_SHORT).show()
                }
            }
        }
        true
    })

    @SuppressLint("MissingPermission")
    inner class ServerClass : Thread() {
        //Reference variable for BluetoothServerSocket object
        private lateinit var bluetoothServerSocket: BluetoothServerSocket

        init {
            try {
                //Initialisation of BluetoothServerSocket object
                bluetoothServerSocket = myBluetoothAdapter.listenUsingRfcommWithServiceRecord(APP_NAME, MY_UUID)
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

    @SuppressLint("MissingPermission")
    inner class ClientClass(device: BluetoothDevice) : Thread() {
        private lateinit var bluetoothSocket: BluetoothSocket
        private var bluetoothDevice: BluetoothDevice = device

        init {
            try {
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(MY_UUID)
            } catch (esasa: IOException) {
                esasa.printStackTrace()
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

    private fun setQuestion(position: Int): Int {
        if (countList == 0) {
            binding.btnListen.visibility = View.VISIBLE
            binding.tvStatus.visibility = View.VISIBLE
            binding.ivImageView.visibility = View.INVISIBLE
        }
        if (countList == 1) {
            binding.btnListen.visibility = View.INVISIBLE
            binding.tvStatus.visibility = View.INVISIBLE
            binding.ivImageView.visibility = View.VISIBLE

        } else if (countList == 2) {
            binding.btnListen.visibility = View.INVISIBLE
            binding.tvStatus.visibility = View.INVISIBLE
            binding.ivImageView.visibility = View.INVISIBLE

        } else if (countList == 3) {
            binding.btnListen.visibility = View.INVISIBLE
            binding.tvStatus.visibility = View.INVISIBLE
            binding.ivImageView.visibility = View.VISIBLE
        }



        val question = mQuestionsList!![position - 1]
        binding.ivImageView.setImageResource(question.image)


        return position
    }

    private fun selectQuestion(select: Int) { //savolni tanlash
        if (mCurrentPosition < mQuestionsList!!.size) {
            mSelectedOptionPosition = select // 1,2,3,4
            val question = mQuestionsList!![mCurrentPosition - 1]
            options = question.variants
            mCurrentPosition++
            if (question.image == options[mSelectedOptionPosition - 1]) {
                counter += 2//increase count of correct answers

                Log.i("mytest", countList.toString())
                if (countList == 1) {
                    resizeImagesLeft(counter)
                } else {
                    resizeImagesRight(counter)
                }
            } else {
                counter -= 1
                if (countList == 1) {
                    resizeImagesLeft(counter)
                } else {
                    resizeImagesRight(counter)
                }
            }
            setQuestion(mCurrentPosition)
            //Log.i("mCurrent", mCurrentPosition.toString())
        } else {
            countList++
            if (countList == 2) {
                sendReceive.write("finishing".toByteArray())
            }


            if (countList == 4) {
                //    writeActivity list to'lganini yuborish
                sendReceive.write("restart$rightEyeCount/$leftEyeCount".toByteArray())
            }
        }
    }


    private fun resizeImagesRight(counter: Int) {

        if (0 > counter) {
            rightEyeCount = 1.3F
            sizeImage = 58.2F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }
        if (counter in 0..1) {
            rightEyeCount = 1.0F
            sizeImage = 58.2F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }
        if (counter in 2..3) {
            rightEyeCount = 0.9F
            sizeImage = 46.21F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }
        if (counter in 4..5) {
            rightEyeCount = 0.8F
            sizeImage = 36.71F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }
        if (counter in 6..7) {
            rightEyeCount = 0.7F
            sizeImage = 29.16F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }
        if (counter in 8..9) {
            rightEyeCount = 0.6F
            sizeImage = 23.16F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }
        if (counter in 10..11) {
            rightEyeCount = 0.5F
            sizeImage = 18.4F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")

        }
        if (counter in 12..13) {
            rightEyeCount = 0.4F
            sizeImage = 14.61F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }
        if (counter in 14..15) {
            rightEyeCount = 0.3F
            sizeImage = 11.61F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }
        if (counter in 16..17) {
            rightEyeCount = 0.2F
            sizeImage = 9.22F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }
        if (counter in 18..19) {
            rightEyeCount = 0.1F
            sizeImage = 7.33F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }
        if (counter in 20..21) {
            rightEyeCount = 0.0F
            sizeImage = 5.82F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }
        if (counter in 22..23) {
            rightEyeCount = -0.1F
            sizeImage = 4.62F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }

        if (counter in 24..25) {
            rightEyeCount = -0.2F
            sizeImage = 3.68F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }
        if (counter in 26..27) {
            rightEyeCount = -0.3F
            sizeImage = 2.92F
            setImage(convertSizeImage(sizeImage))
            resultText(rightEyeCount, sizeImage, "4 m")
        }

    }


    private fun resizeImagesLeft(counter: Int) {
        if (0 > counter) {
            leftEyeCount = 1.3F
            sizeImage = 58.2F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }
        if (counter in 0..1) {
            leftEyeCount = 1.0F
            sizeImage = 58.2F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }
        if (counter in 2..3) {
            leftEyeCount = 0.9F
            sizeImage = 46.21F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }
        if (counter in 4..5) {
            leftEyeCount = 0.8F
            sizeImage = 36.71F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }
        if (counter in 6..7) {
            leftEyeCount = 0.7F
            sizeImage = 29.16F
            setImage(convertSizeImage(sizeImage))
            Log.i("rasmbir",convertSizeImage(sizeImage).toString())
            resultText(leftEyeCount, sizeImage, "4 m")
        }
        if (counter in 8..9) {
            leftEyeCount = 0.6F
            sizeImage = 23.16F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
            Log.i("rasmikki",convertSizeImage(sizeImage).toString())

        }
        if (counter in 10..11) {
            leftEyeCount = 0.5F
            sizeImage = 18.4F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }
        if (counter in 12..13) {
            leftEyeCount = 0.4F
            sizeImage = 14.61F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }
        if (counter in 14..15) {
            leftEyeCount = 0.3F
            sizeImage = 11.61F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }
        if (counter in 16..17) {
            leftEyeCount = 0.2F
            sizeImage = 9.22F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }
        if (counter in 18..19) {
            leftEyeCount = 0.1F
            sizeImage = 7.33F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }
        if (counter in 20..21) {
            leftEyeCount = 0.0F
            sizeImage = 5.82F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }
        if (counter in 22..23) {
            leftEyeCount = -0.1F
            sizeImage = 4.62F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }

        if (counter in 24..25) {
            leftEyeCount = -0.2F
            sizeImage = 3.68F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }
        if (counter in 26..27) {
            leftEyeCount = -0.3F
            sizeImage = 2.92F
            setImage(convertSizeImage(sizeImage))
            resultText(leftEyeCount, sizeImage, "4 m")
        }

        Log.i("qoshishi",counter.toString())

    }


    private fun convertSizeImage(currentSizeImageMillimeters: Float): Float {
        val r: Resources = resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM,
            currentSizeImageMillimeters,
            r.displayMetrics)
    }

    private fun setImage(sizeImage: Float) {
        binding.ivImageView.layoutParams.width = sizeImage.toInt()
        binding.ivImageView.layoutParams.height = sizeImage.toInt()

        Log.i("asosiysi",sizeImage.toString())

        binding.resultText.text = questionString
    }

    private fun resultText(resultEye: Float, sizeImage: Float, distance: String) {
        questionString =
            "LogMar: " + resultEye.toString() + "\n" +
                    "sizeImage: " + sizeImage.toString() + "\n" +
                    "distance: " + distance
    }

    private fun resultSave() {
        // data storage
        calendar = Calendar.getInstance()
        simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss")
        dateTime = simpleDateFormat.format(calendar.time).toString()

        val database = UserDatabase.initDatabase(requireContext())
        database.userDao()
            .insertUser(User(0, leftEyeCount.toString(), rightEyeCount.toString(), dateTime))
    }

    private fun visibleIsGone() {
        binding.btnListen.visibility = View.GONE
        binding.tvStatus.visibility = View.GONE
    }

    override fun onDestroyView() {
        viewBinding=null
        super.onDestroyView()
    }

}