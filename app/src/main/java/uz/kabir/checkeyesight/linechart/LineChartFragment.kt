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
import uz.kabir.checkeyesight.db.User
import uz.kabir.checkeyesight.db.UserDatabase


class LineChartFragment : Fragment() {

    private var viewBinding: FragmentLineChartBinding? = null
    private val binding get() = viewBinding!!

    private lateinit var list: MutableList<User>
    private lateinit var database: UserDatabase

    private lateinit var label: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = FragmentLineChartBinding.inflate(inflater, container, false)
        val view = binding.root

        database = UserDatabase.initDatabase(requireContext())

        list = database.userDao().getAllUsers() as MutableList<User>

        setLineChartData()

        return view
    }


    private fun setLineChartData() {
        val linevalues1 = ArrayList<Entry>()
        val linevalues2 = ArrayList<Entry>()

        for (index in 0 until list.size) {
            linevalues1.add(Entry(index.toFloat(), list[index].leftEye.toFloat()))
            linevalues2.add(Entry(index.toFloat(), list[index].rightEye.toFloat()))
        }

        val linedataset1 = LineDataSet(linevalues1, getString(R.string.left_eye))
        val linedataset2 = LineDataSet(linevalues2, getString(R.string.right_eye))

        linedataset1.lineWidth = 4f
        linedataset2.lineWidth = 4f

        //line design
        linedataset1.color = resources.getColor(R.color.line_chart_color_1)
        linedataset1.circleRadius = 6f
        linedataset1.setDrawFilled(false)
        linedataset1.valueTextSize = 18F
        linedataset1.fillColor = Color.WHITE
        linedataset1.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        linedataset1.formSize = 12f
        linedataset1.valueTextColor = Color.WHITE
        linedataset1.circleHoleColor = resources.getColor(R.color.line_chart_color_1)
        linedataset1.setCircleColors(resources.getColor(R.color.night_and_light))
        linedataset1.circleHoleRadius = 4f

        linedataset2.setDrawFilled(false)
        linedataset2.valueTextSize = 18F
        linedataset2.fillColor = Color.WHITE
        linedataset2.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        linedataset2.formSize = 12f
        linedataset2.color = resources.getColor(R.color.line_chart_color_2)
        linedataset2.circleRadius = 6f
        linedataset2.valueTextColor = Color.WHITE
        linedataset2.circleHoleColor = resources.getColor(R.color.line_chart_color_2)
        linedataset2.setCircleColors(resources.getColor(R.color.night_and_light))
        linedataset2.circleHoleRadius = 4f

        //Connect our data to the UI Screen
        val data1 = LineData(linedataset1, linedataset2)

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

//        xAxis.setAxisMinValue((list.size- 1).toFloat()-1)

    /* we can modify viewport, Scrolling the data from right to left allow
    30 values to be displayed at once on the x-axis not allow more value
    */
        binding.chartGraph.setVisibleXRangeMaximum(10f)
//        binding.chartGraph.setVisibleXRangeMinimum(10f)
//        binding.chartGraph.setVisibleYRangeMinimum(0.1f, linedataset1.axisDependency)
    /*set the left edge of the chart to x-index 20
    moveViewToX(...) also calls invalidate()
    */
        binding.chartGraph.moveViewToX(10f)

//        binding.chartGraph.scrollX = 10


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
//            buf.append("\n\n\n")
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
        viewBinding = null
        super.onDestroyView()
    }


}


fun String.showlog(tag: String = "TTT") {
    Log.d(tag, this)
}

