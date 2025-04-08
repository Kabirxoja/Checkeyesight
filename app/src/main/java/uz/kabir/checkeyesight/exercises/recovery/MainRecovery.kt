package uz.kabir.checkeyesight.exercises.recovery

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentMainRecoveryBinding
import uz.kabir.checkeyesight.exercises.typeconstructor.ExerciseTypes
import java.lang.String.format
import java.util.*


class MainRecovery : Fragment() {

    private var viewBinding: FragmentMainRecoveryBinding? = null
    private val binding get() = viewBinding!!

    private var lists: ArrayList<ExerciseTypes>? = null
    private var listPosition: Int = 0


    private var mainCountTimer: CountDownTimer? = null
    private var nextCountTimer: CountDownTimer? = null

    private var nextTimerRunning = false
    private var mainTimerRunning = false

    private val START_TIME_IN_MILLIS_NEXT: Long = 5000
    private val START_TIME_IN_MILLIS_MAIN: Long = 40000

    private var nextTimerMillis: Long = START_TIME_IN_MILLIS_NEXT
    private var mainTimerMillis: Long = START_TIME_IN_MILLIS_MAIN


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                val builder = android.app.AlertDialog.Builder(context)
                val dialogView: View = layoutInflater.inflate(R.layout.custom_dialog_info, null)
                builder.setView(dialogView)

                val cancel = dialogView.findViewById<Button>(R.id.dialog_cancel_button)
                val start = dialogView.findViewById<Button>(R.id.dialog_start_button)

                val dialogTitle = dialogView.findViewById<TextView>(R.id.dialog_title)
                val infoTitle = dialogView.findViewById<TextView>(R.id.info_title)
                infoTitle.visibility = View.GONE

                start.text = getString(R.string.answer_yes)
                cancel.text = getString(R.string.answer_no)

                dialogTitle.text = getString(R.string.close_eye_exercise)

                val dialog = builder.create()
                dialog.show()

                cancel.setOnClickListener {
                    dialog.dismiss()
                }
                start.setOnClickListener {

                    view?.findNavController()?.navigate(R.id.homeFragment)
                    dialog.dismiss()
                }

                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        viewBinding = FragmentMainRecoveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lists = ListDataRecovery.getExercise()

        visibilities(true)


        binding.myAnimationView.setAnimation(lists?.get(listPosition)!!.images)
        binding.txtTypeExercise.setText(lists?.get(listPosition)!!.exerciseName)



        pauseToPlay()
        pauseToPlayMain()



        binding.nextTimerBtn.setOnClickListener {
            if (!nextTimerRunning) {
                nextFun()
                playToPause()
            } else {
                pauseNext()
                pauseToPlay()
            }


        }


        binding.mainTimerBtn.setOnClickListener {
            if (!mainTimerRunning) {
                playToPauseMain()
                mainFun()

            } else {
                pauseToPlayMain()
                pauseMain()
            }


        }

    }


    private fun nextFun() {
        nextCountTimer = object : CountDownTimer(nextTimerMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // next timer tick
                updateNextText()
                nextTimerMillis = millisUntilFinished
                Log.i("next", nextTimerMillis.toString())
                seekBarCircle((millisUntilFinished / 1000).toInt())
            }

            override fun onFinish() {
                // next timer finish


                mainFun()
                nextTimerMillis = START_TIME_IN_MILLIS_NEXT


                visibilities(false)

                binding.myAnimationView.loop(true)
                binding.myAnimationView.playAnimation()
                binding.myAnimationView.speed = 0.2F

                nextTimerRunning = false

            }

        }.start()
        playToPause()
        nextTimerRunning = true
    }


    private fun mainFun() {
        mainCountTimer = object : CountDownTimer(mainTimerMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //main Timer Tick
                updateMainText()
                mainTimerMillis = millisUntilFinished

                val millisecondToSecond = millisUntilFinished.toInt() / 1000
                seekBarImageShow(millisecondToSecond)


            }

            override fun onFinish() {
                //main Timer Finish
                if (listPosition < lists!!.size - 1) {
                    nextFun()

                    visibilities(true)

                    listPosition++
                    binding.myAnimationView.setAnimation(lists?.get(listPosition)!!.images)
                    binding.txtTypeExercise.setText(lists?.get(listPosition)!!.exerciseName)

                } else {
                    invisible()
                    binding.toHome.setOnClickListener {
                        view?.findNavController()?.navigate(R.id.homeFragment)
                    }
                }

                mainTimerMillis = START_TIME_IN_MILLIS_MAIN
                mainTimerRunning = false
            }
        }.start()

        pauseToPlayMain()

        mainTimerRunning = true
    }


    private fun updateNextText() {
        val minutes = (nextTimerMillis / 1000).toInt() / 60
        val seconds = (nextTimerMillis / 1000).toInt() % 60
    }

    private fun updateMainText() {
        val minutes = (mainTimerMillis / 1000).toInt() / 60
        val seconds = (mainTimerMillis / 1000).toInt() % 60
        val timeLeftFormatted: String = format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        binding.txtMain.text = timeLeftFormatted
    }

    private fun pauseNext() {
        if (nextCountTimer != null) {
            nextCountTimer!!.cancel()
            nextCountTimer = null
        }
        nextTimerRunning = false
        pauseToPlay()
    }

    private fun pauseMain() {
        if (mainCountTimer != null) {
            mainCountTimer!!.cancel()
            mainCountTimer = null
        }
        mainTimerRunning = false
        playToPauseMain()
    }

    private fun seekBarImageShow(resultValue: Int) {
        binding.seekBar.progress = resultValue + 1
        binding.seekBar.isEnabled = false
        binding.seekBar.max = 40

    }

    private fun seekBarCircle(valueProgress: Int) {
        val x = 5 - valueProgress
        binding.progressBar.progress = x
    }


    private fun playToPause() {
        binding.nextTimerBtn.setImageResource(R.drawable.play_to_pause)
        val avdPlayToPause = binding.nextTimerBtn.drawable as AnimatedVectorDrawable
        avdPlayToPause.start()
    }

    private fun pauseToPlay() {
        binding.nextTimerBtn.setImageResource(R.drawable.pause_to_play)
        val avdPauseToPlay = binding.nextTimerBtn.drawable as AnimatedVectorDrawable
        avdPauseToPlay.start()
    }

    private fun pauseToPlayMain() {
        binding.mainTimerBtn.setImageResource(R.drawable.play_to_pause)
        val avdPauseToPlay = binding.mainTimerBtn.drawable as AnimatedVectorDrawable
        avdPauseToPlay.start()

        binding.myAnimationView.resumeAnimation()


    }

    private fun playToPauseMain() {
        binding.mainTimerBtn.setImageResource(R.drawable.pause_to_play)
        val avdPauseToPlay = binding.mainTimerBtn.drawable as AnimatedVectorDrawable
        avdPauseToPlay.start()
        binding.myAnimationView.pauseAnimation()

    }


    override fun onPause() {
        super.onPause()
        pauseMain()
        pauseNext()
    }

    override fun onDestroy() {
        super.onDestroy()
        nextCountTimer?.cancel()
        mainCountTimer?.cancel()
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }


    private fun invisible() {
        binding.nextTimerBtn.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.INVISIBLE
        binding.mainTimerBtn.visibility = View.INVISIBLE
        binding.relativeLayout.visibility = View.INVISIBLE

        binding.txtMain.visibility = View.INVISIBLE
        binding.textView4.visibility = View.INVISIBLE
        binding.seekBar.visibility = View.INVISIBLE
        binding.toHome.visibility  =View.VISIBLE

        binding.textCongratulations.text = getString(R.string.eye_exercise_done)
        binding.textCongratulations.setTextColor(resources.getColor(R.color.white))
        binding.textCongratulations.visibility = View.VISIBLE
        val a1: Animation = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
        a1.duration = 1000
        binding.textCongratulations.startAnimation(a1)

        binding.txtTypeExercise.setText(R.string.txt_congratulations)
        binding.txtTypeExercise.setTextColor(resources.getColor(R.color.pale_color))
        binding.txtTypeExercise.visibility = View.VISIBLE
        val a2: Animation = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
        a2.duration = 1000
        binding.txtTypeExercise.startAnimation(a2)

        binding.myAnimationView.setAnimation(R.raw.cup_anim)
        binding.myAnimationView.visibility = View.VISIBLE
        binding.myAnimationView.playAnimation()
        binding.layout.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.main_color
            )
        )
        binding.myAnimationView.loop(false)
        binding.myAnimationView.playAnimation()
        binding.myAnimationView.speed = 0.5F

    }
    private fun visibilities(visBoolean: Boolean) {
        binding.myAnimationView.loop(false)
        binding.myAnimationView.playAnimation()
        binding.myAnimationView.speed = 0.2F
        if (visBoolean) {
            binding.nextTimerBtn.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
            binding.toHome.visibility  =View.INVISIBLE
            binding.txtTypeExercise.visibility = View.VISIBLE


            binding.mainTimerBtn.visibility = View.INVISIBLE
            binding.relativeLayout.visibility = View.INVISIBLE
            binding.txtMain.visibility = View.INVISIBLE
            binding.textView4.visibility = View.INVISIBLE
            binding.seekBar.visibility = View.INVISIBLE
            binding.myAnimationView.visibility = View.INVISIBLE
        } else {
            binding.nextTimerBtn.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.INVISIBLE
            binding.toHome.visibility  =View.INVISIBLE
            binding.txtTypeExercise.visibility = View.INVISIBLE


            binding.mainTimerBtn.visibility = View.VISIBLE
            binding.relativeLayout.visibility = View.VISIBLE
            binding.txtMain.visibility = View.VISIBLE
            binding.textView4.visibility = View.VISIBLE
            binding.seekBar.visibility = View.VISIBLE
            binding.myAnimationView.visibility = View.VISIBLE
        }
    }


}