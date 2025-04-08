package uz.kabir.checkeyesight.home

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentHomeBinding
import uz.kabir.checkeyesight.tablayout.MyPagerAdapter


class HomeFragment : Fragment() {

    private var viewBinding: FragmentHomeBinding? = null
    private val binding get() = viewBinding!!

    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>


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

                dialogTitle.text = getString(R.string.close_app_question)

                val dialog = builder.create()
                dialog.show()

                cancel.setOnClickListener {
                    dialog.dismiss()
                }
                start.setOnClickListener {
                    activity?.finish()
                }
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*      In App Update       */
        appUpdateManager = AppUpdateManagerFactory.create(requireContext())

        // Initialize Activity Result Launcher
        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result: ActivityResult ->
            if (result.resultCode != RESULT_OK) {
                Toast.makeText(requireContext(), "Update failed!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Update successful!", Toast.LENGTH_SHORT).show()
            }
        }

        checkForUpdate()
    }

    private fun checkForUpdate() {
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request an immediate update
                requestUpdate(appUpdateInfo)
            }
        }
    }

    /* Start an update */
    private fun requestUpdate(appUpdateInfo: AppUpdateInfo) {
        appUpdateManager.startUpdateFlowForResult(
            // Pass the intent that is returned by 'getAppUpdateInfo()'.
            appUpdateInfo,
            // an activity result launcher registered via registerForActivityResult
            activityResultLauncher,
            AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE)
                .setAllowAssetPackDeletion(true)
                .build()
        )
    }


    // Checks that the update is not stalled during 'onResume()'
    override fun onResume() {
        super.onResume()
        // Resume update if it was interrupted
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    // If an in-app update is already running, resume the update.
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, activityResultLauncher, AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build())
                }
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initialize AppUpdateManager here (after binding is initialized)
        appUpdateManager = AppUpdateManagerFactory.create(requireContext())
        checkForUpdate()

        val adapter = activity?.supportFragmentManager?.let { MyPagerAdapter(it, lifecycle) }
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.tab_layout1)
                1 -> tab.text = getString(R.string.tab_layout2)
            }
            marginTabLayout()
        }.attach()

        return view
    }


    private fun marginTabLayout() {
        for (i in 0 until binding.tabs.tabCount) {
            val tab = (binding.tabs.getChildAt(0) as ViewGroup).getChildAt(i)
            val p = tab.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(0, 0, 0, 0)
        }
    }

    override fun onCreateOptionsMenu(menu1: Menu, inflater1: MenuInflater) {
        super.onCreateOptionsMenu(menu1, inflater1)
        inflater1.inflate(R.menu.action_menu, menu1)
        menu1.findItem(R.id.info_uz).isVisible = false
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }

}