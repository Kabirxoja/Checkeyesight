package uz.kabir.checkeyesight.visiontest


import uz.kabir.checkeyesight.R
import java.util.ArrayList

object List2 {

    fun getQuestions2(): ArrayList<Question> {

        //create ArrayList of type question to store data
        val questionsList = ArrayList<Question>()

        //populate the questions
        val que1 = Question(
            1,
            R.drawable.digit_seven,
            listOf(
                R.drawable.digit_one,
                R.drawable.digit_seven,
                R.drawable.digit_four,
                R.drawable.digit_three
            )
        )

        questionsList.add(que1)

        val que2 = Question(
            2,
            R.drawable.digit_eight,
            listOf(
                R.drawable.digit_eight,
                R.drawable.digit_three,
                R.drawable.digit_six,
                R.drawable.digit_five
            )
        )

        questionsList.add(que2)

        val que3 = Question(
            3,
            R.drawable.digit_nine,
            listOf(
                R.drawable.digit_six,
                R.drawable.digit_eight,
                R.drawable.digit_nine,
                R.drawable.digit_three
            )
        )


        questionsList.add(que3)

        val que4 = Question(
            4,
            R.drawable.digit_zero,
            listOf(
                R.drawable.digit_eight,
                R.drawable.digit_zero,
                R.drawable.digit_two,
                R.drawable.digit_five
            )
        )


        questionsList.add(que4)

        val que5 = Question(
            5,
            R.drawable.digit_one,
            listOf(
                R.drawable.digit_seven,
                R.drawable.digit_two,
                R.drawable.digit_one,
                R.drawable.digit_four
            )
        )

        questionsList.add(que5)

        val que6 = Question(
            6,
            R.drawable.digit_six,
            listOf(
                R.drawable.digit_six,
                R.drawable.digit_seven,
                R.drawable.digit_eight,
                R.drawable.digit_five
            )
        )
        //

        questionsList.add(que6)

        val que7 = Question(
            7,
            R.drawable.digit_three,
            listOf(
                R.drawable.digit_four,
                R.drawable.digit_five,
                R.drawable.digit_nine,
                R.drawable.digit_three
            )
        )


        questionsList.add(que7)

        val que8 = Question(
            8,
            R.drawable.digit_five,
            listOf(
                R.drawable.digit_zero,
                R.drawable.digit_six,
                R.drawable.digit_five,
                R.drawable.digit_two
            )

        )

        questionsList.add(que8)


        val que9 = Question(
            9,
            R.drawable.digit_four,
            listOf(
                R.drawable.digit_one,
                R.drawable.digit_seven,
                R.drawable.digit_four,
                R.drawable.digit_eight
            )
        )


        questionsList.add(que9)

        val que10 = Question(
            10,
            R.drawable.digit_two,
            listOf(
                R.drawable.digit_five,
                R.drawable.digit_seven,
                R.drawable.digit_three,
                R.drawable.digit_two
            )
        )

        questionsList.add(que10)

        return  questionsList
    }

}