package uz.kabir.checkeyesight.bluetoothconnect

import uz.kabir.checkeyesight.visiontest.Question
import uz.kabir.checkeyesight.R
import java.util.ArrayList

object ListBlueTooth {


        fun getQuestions1(): ArrayList<Question> {

            //create ArrayList of type question to store data
            val questionsList = ArrayList<Question>()

            //populate the questions
            val que1 = Question(
                1,
                R.drawable.e_chart_left,
                listOf(
                    R.drawable.e_chart_down,
                    R.drawable.e_chart_up,
                    R.drawable.e_chart_right,
                    R.drawable.e_chart_left
                )
            )
            //que1.variants.shuffled()
            questionsList.add(que1)

            val que2 = Question(
                2,
                R.drawable.e_chart_right,
                listOf(
                    R.drawable.e_chart_down,
                    R.drawable.e_chart_up,
                    R.drawable.e_chart_right,
                    R.drawable.e_chart_left
                )
            )
            //que2.variants.shuffled()
            questionsList.add(que2)

            val que3 = Question(
                3,
                R.drawable.e_chart_left,
                listOf(
                    R.drawable.e_chart_down,
                    R.drawable.e_chart_up,
                    R.drawable.e_chart_right,
                    R.drawable.e_chart_left
                )
            )
            //que3.variants.shuffled()

            questionsList.add(que3)

            val que4 = Question(
                4,
                R.drawable.e_chart_up,
                listOf(
                    R.drawable.e_chart_down,
                    R.drawable.e_chart_up,
                    R.drawable.e_chart_right,
                    R.drawable.e_chart_left
                )
            )
            //que4.variants.shuffled()

            questionsList.add(que4)

            val que5 = Question(
                5,
                R.drawable.e_chart_left,
                listOf(
                    R.drawable.e_chart_down,
                    R.drawable.e_chart_up,
                    R.drawable.e_chart_right,
                    R.drawable.e_chart_left
                )
            )
            // que5.variants.shuffled()
            questionsList.add(que5)

            val que6 = Question(
                6,
                R.drawable.e_chart_down,
                listOf(
                    R.drawable.e_chart_down,
                    R.drawable.e_chart_up,
                    R.drawable.e_chart_right,
                    R.drawable.e_chart_left
                )
            )
            //  que6.variants.shuffled()

            questionsList.add(que6)

            val que7 = Question(
                7,
                R.drawable.e_chart_up,
                listOf(
                    R.drawable.e_chart_down,
                    R.drawable.e_chart_up,
                    R.drawable.e_chart_right,
                    R.drawable.e_chart_left
                )
            )
            //que7.variants.shuffled()

            questionsList.add(que7)

            val que8 = Question(
                8,
                R.drawable.e_chart_down,
                listOf(
                    R.drawable.e_chart_down,
                    R.drawable.e_chart_up,
                    R.drawable.e_chart_right,
                    R.drawable.e_chart_left
                )

            )
            //que8.variants.shuffled()
            questionsList.add(que8)

            val que9 = Question(
                9,
                R.drawable.e_chart_right,
                listOf(
                    R.drawable.e_chart_down,
                    R.drawable.e_chart_up,
                    R.drawable.e_chart_right,
                    R.drawable.e_chart_left
                )
            )
            //que9.variants.shuffled()

            questionsList.add(que9)

            val que10 = Question(
                10,
                R.drawable.e_chart_up,
                listOf(
                    R.drawable.e_chart_down,
                    R.drawable.e_chart_up,
                    R.drawable.e_chart_right,
                    R.drawable.e_chart_left
                )
            )
            questionsList.add(que10)

//
//            val que11 = Question(
//                11,
//                R.drawable.e_chart_right,
//                listOf(
//                    R.drawable.e_chart_down,
//                    R.drawable.e_chart_up,
//                    R.drawable.e_chart_right,
//                    R.drawable.e_chart_left
//                )
//            )
//            questionsList.add(que11)
//
//            val que12 = Question(
//                12,
//                R.drawable.e_chart_down,
//                listOf(
//                    R.drawable.e_chart_down,
//                    R.drawable.e_chart_up,
//                    R.drawable.e_chart_right,
//                    R.drawable.e_chart_left
//                )
//            )
//            //que10.variants.shuffled()
//
//            questionsList.add(que12)

//        questionsList.shuffle(Random(10))

            return  questionsList
        }

}