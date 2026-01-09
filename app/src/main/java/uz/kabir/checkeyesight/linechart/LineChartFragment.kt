package uz.kabir.checkeyesight.linechart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import uz.kabir.checkeyesight.R
import uz.kabir.checkeyesight.databinding.FragmentLineChartBinding
import uz.kabir.checkeyesight.history.db.HistoryEntity
import uz.kabir.checkeyesight.history.db.UserDatabase


class LineChartFragment : Fragment() {

    private var _binding: FragmentLineChartBinding? = null
    private val binding get() = _binding!!
    private lateinit var list: MutableList<HistoryEntity>
    private lateinit var database: UserDatabase
    private lateinit var label: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLineChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = UserDatabase.initDatabase(requireContext())
        list = database.userDao().getAllUsers() as MutableList<HistoryEntity>
        setLineChartData()
    }


    private fun setLineChartData() {
        val lineValues1 = ArrayList<Entry>()
        val lineValues2 = ArrayList<Entry>()

        for (index in 0 until list.size) {
            lineValues1.add(Entry(index.toFloat(), list[index].leftEye.toFloat()))
            lineValues2.add(Entry(index.toFloat(), list[index].rightEye.toFloat()))
        }

        val lineDataSet1 = LineDataSet(lineValues1, getString(R.string.left_eye))
        val lineDataSet2 = LineDataSet(lineValues2, getString(R.string.right_eye))

        lineDataSet1.lineWidth = 4f
        lineDataSet2.lineWidth = 4f

        //line design
        lineDataSet1.color = resources.getColor(R.color.line_chart_color_1)
        lineDataSet1.circleRadius = 6f
        lineDataSet1.setDrawFilled(false)
        lineDataSet1.valueTextSize = 18F
        lineDataSet1.fillColor = Color.WHITE
        lineDataSet1.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        lineDataSet1.formSize = 12f
        lineDataSet1.valueTextColor = Color.WHITE
        lineDataSet1.circleHoleColor = resources.getColor(R.color.line_chart_color_1)
        lineDataSet1.setCircleColors(resources.getColor(R.color.night_and_light))
        lineDataSet1.circleHoleRadius = 4f

        lineDataSet2.setDrawFilled(false)
        lineDataSet2.valueTextSize = 18F
        lineDataSet2.fillColor = Color.WHITE
        lineDataSet2.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        lineDataSet2.formSize = 12f
        lineDataSet2.color = resources.getColor(R.color.line_chart_color_2)
        lineDataSet2.circleRadius = 6f
        lineDataSet2.valueTextColor = Color.WHITE
        lineDataSet2.circleHoleColor = resources.getColor(R.color.line_chart_color_2)
        lineDataSet2.setCircleColors(resources.getColor(R.color.night_and_light))
        lineDataSet2.circleHoleRadius = 4f

        //Connect our data to the UI Screen
        val data1 = LineData(lineDataSet1, lineDataSet2)

        //Animation line chart
        binding.chartGraph.data = data1
        binding.chartGraph.animateXY(1000, 1000, Easing.EaseInCubic)
        binding.chartGraph.invalidate()


        val xAxis: XAxis = binding.chartGraph.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val dates = getDate()
        Log.d("TTT", "dates = $dates")

        xAxis.valueFormatter = IndexAxisValueFormatter(dates)
        xAxis.labelRotationAngle = 90f

        xAxis.labelCount = 10
        xAxis.setAxisMaxValue((list.size).toFloat() - 1)

        binding.chartGraph.setVisibleXRangeMaximum(10f)
        binding.chartGraph.moveViewToX(10f)


        binding.chartGraph.setBackgroundColor(resources.getColor(R.color.dark_night))
        binding.chartGraph.setDrawGridBackground(false)

        binding.chartGraph.legend.isEnabled = true
        binding.chartGraph.legend.textColor = Color.WHITE
        binding.chartGraph.legend.textSize = 24f
        binding.chartGraph.legend.formToTextSpace = 2f


        binding.chartGraph.legend.form = Legend.LegendForm.LINE
        binding.chartGraph.legend.formSize = 24f
        binding.chartGraph.legend.formLineWidth = 6f

        binding.chartGraph.legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        binding.chartGraph.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        binding.chartGraph.legend.orientation = Legend.LegendOrientation.HORIZONTAL
        binding.chartGraph.legend.setDrawInside(false)
        binding.chartGraph.legend.isWordWrapEnabled = true

        binding.chartGraph.description.isEnabled = false

        binding.chartGraph.setTouchEnabled(true)
        binding.chartGraph.isDragEnabled = true


        //mashtab
        binding.chartGraph.setScaleEnabled(false)
        binding.chartGraph.setPinchZoom(false)
        binding.chartGraph.extraLeftOffset = 2f
        binding.chartGraph.extraRightOffset = 2f

        binding.chartGraph.isScaleXEnabled = false
        binding.chartGraph.isScaleYEnabled = false

        binding.chartGraph.isDoubleTapToZoomEnabled = false

        //to hide background lines
        binding.chartGraph.xAxis.setDrawGridLines(true)
        binding.chartGraph.axisLeft.setDrawGridLines(true)
        binding.chartGraph.axisRight.setDrawGridLines(true)

        //to hide right Y and top X border
        val rightYAxis: YAxis = binding.chartGraph.axisRight
        rightYAxis.isEnabled = false


        val leftYAxis: YAxis = binding.chartGraph.axisLeft
        leftYAxis.gridColor = Color.TRANSPARENT
        leftYAxis.textColor = Color.WHITE
        leftYAxis.isEnabled = true
        leftYAxis.textSize = 14f


        val topXAxis: XAxis = binding.chartGraph.xAxis
        topXAxis.gridColor = Color.WHITE
        topXAxis.textColor = Color.WHITE
        topXAxis.textSize = 14f
        topXAxis.isEnabled = true

    }


    private fun getDate(): ArrayList<String> {
        label = ArrayList()
        for (i in 0 until list.size)
        {

            val substringDay = list[i].dateFormat.substring(0,8)
            val substringHour = list[i].dateFormat.substring(9,14)

            val buf = StringBuilder()
            buf.append(substringDay)
            buf.append(System.getProperty("line.separator"))

            buf.append(substringHour)


            label.add(buf.toString())
            Log.i("manashude = >", label.toString())

        }

        return label
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
        super.onDestroyView()
        _binding = null
    }
}


fun String.showlog(tag: String = "TTT") {
    Log.d(tag, this)
}

